package money.android.bignerdranch.com.moneytracker.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.preference.PreferenceManager;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.MainActivity;
import money.android.bignerdranch.com.moneytracker.UI.utils.MoneyTrackerAplication;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;
import money.android.bignerdranch.com.moneytracker.rest.Models.CategoryModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.ExpensesModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserSyncCategoriesModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserSyncExpensesModel;
import money.android.bignerdranch.com.moneytracker.rest.RestService;

public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final long[] VIBRATE_PATTERN = new long[]{0, 100, 0, 200};
    private static final int LED_LIGHTS_TIME_ON = 200;
    private static final int LED_LIGHTS_TIME_OFF = 1500;
    private static final int NOTIFICATION_ID = 4004;
    private NotificationManager mNotificationManager;
    private boolean isNotificationsEnabled;
    private boolean isVibrateEnabled;
    private boolean isSoundEnabled;
    private boolean isLedEnabled;

    private SharedPreferences mSharedPreferences;
    private String globalNotificationsKey;
    private String vibrateNotificationsKey;
    private String soundNotificationsKey;
    private String ledNotificationsKey;
    private static final boolean DEFAULT_VALUE = true;

    public TrackerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        try {
            syncCategories();
            syncExpenses();
            sendNotification();
        } catch (Exception e) {

        }
    }

    private void init() {
        SharedPreferences mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        globalNotificationsKey = getContext().getString(R.string.pref_enable_notifications_key);
        vibrateNotificationsKey = getContext().getString(R.string.pref_enable_vibrate_notifications_key);
        soundNotificationsKey = getContext().getString(R.string.pref_enable_sound_notifications_key);
        ledNotificationsKey = getContext().getString(R.string.pref_enable_led_notifications_key);


        isNotificationsEnabled = mSharedPreferences.getBoolean(globalNotificationsKey, DEFAULT_VALUE);
        isVibrateEnabled = mSharedPreferences.getBoolean(vibrateNotificationsKey, DEFAULT_VALUE);
        isSoundEnabled = mSharedPreferences.getBoolean(soundNotificationsKey, DEFAULT_VALUE);
        isLedEnabled = mSharedPreferences.getBoolean(ledNotificationsKey, DEFAULT_VALUE);
    }

    private static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.content_authority), bundle);
    }

    private static Account getSyncAccount(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));
        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            onAccountCreated(newAccount, context);
        }

        return newAccount;
    }


    private static void onAccountCreated(Account newAccount, Context context) {
        final int SYNC_INTERVAL = 60 * 60 * 24;
        final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
        TrackerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount, context.getString(R.string.content_authority), Bundle.EMPTY, SYNC_INTERVAL);
        syncImmediately(context);
    }

    private void syncCategories() {

        RestService restService = new RestService();
        List<CategoryEntity> categoryEntityList = CategoryEntity.selectAll("");
        List<CategoryModel> categoryModels = new ArrayList<>();
        for (int i = 0; i < categoryEntityList.size(); i++) {
            CategoryModel categoryModel = new CategoryModel();
            categoryModel.setId(categoryEntityList.get(i).getId().toString());
            categoryModel.setTitle(categoryEntityList.get(i).getName());
            categoryModels.add(categoryModel);
        }
        String gson = new Gson().toJson(categoryModels);


        try {
            UserSyncCategoriesModel userSyncCategoriesModel = restService.userSyncCategoriesModel(gson, MoneyTrackerAplication.getAuthToken(), MoneyTrackerAplication.getGoogleAuthToken());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void syncExpenses() {
        RestService restService = new RestService();
        List<ExpensesEntity> expensesEntityList = ExpensesEntity.selectAll("");
        List<ExpensesModel> expensesModels = new ArrayList<>();
        for (int i = 0; i < expensesEntityList.size(); i++) {
            ExpensesModel expensesModel = new ExpensesModel();
            expensesModel.setId(Integer.parseInt(expensesEntityList.get(i).getId().toString()));
            expensesModel.setSum(Double.parseDouble(expensesEntityList.get(i).getSum()));
            expensesModel.setComment(expensesEntityList.get(i).getName());
            expensesModel.setTrDate(expensesEntityList.get(i).getDate());
            CategoryEntity categoryEntity = (expensesEntityList.get(i).getCategory());
            expensesModel.setCategoryId(Integer.parseInt(categoryEntity.getId().toString()));
            expensesModels.add(expensesModel);
        }
        String gson = new Gson().toJson(expensesModels);
        try {
            UserSyncExpensesModel userSyncExpensesModel = restService.userSyncExpensesModel(gson, MoneyTrackerAplication.getAuthToken(), MoneyTrackerAplication.getGoogleAuthToken());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { // we can enable inexact timers in our periodic sync
            SyncRequest request = new SyncRequest.Builder().syncPeriodic(syncInterval, flexTime).setSyncAdapter(account, authority).setExtras(new Bundle()).build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, new Bundle(), syncInterval);
        }
    }

    public void sendNotification() {
        init();
        if (!isNotificationsEnabled) {
            return;
        }

        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0 ,intent, 0);

        mNotificationManager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.mipmap.logo_log)
                .setLargeIcon(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.logo_log))
                .setContentTitle(getContext().getString(R.string.app_name))
                .setContentText(getContext().getString(R.string.notification_message))
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        if (isLedEnabled) {
            builder.setLights(Color.BLUE, LED_LIGHTS_TIME_ON, LED_LIGHTS_TIME_OFF);
        }

        if (isSoundEnabled) {
            builder.setSound(Settings.System.DEFAULT_NOTIFICATION_URI);
        }

        if (isVibrateEnabled) {
            builder.setVibrate(VIBRATE_PATTERN);
        }

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
