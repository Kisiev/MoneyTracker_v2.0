package money.android.bignerdranch.com.moneytracker.sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.utils.MoneyTrackerAplication;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;
import money.android.bignerdranch.com.moneytracker.rest.Models.CategoryModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.ExpensesModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserGetDataModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserSyncCategoriesModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserSyncExpensesModel;
import money.android.bignerdranch.com.moneytracker.rest.RestService;


public class TrackerSyncAdapter extends AbstractThreadedSyncAdapter {


    public TrackerSyncAdapter(Context context, boolean autoInitialize) {
        super(context, autoInitialize);
    }

    @Override
    public void onPerformSync(Account account, Bundle bundle, String s, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        try {
            syncCategories();
            syncExpenses();
        } catch (Exception e){

        }
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
        final int SYNC_INTERVAL = 60*60*24;
        final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;
        TrackerSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.content_authority), true);
        ContentResolver.addPeriodicSync(newAccount, context.getString(R.string.content_authority), Bundle.EMPTY, SYNC_INTERVAL);
        syncImmediately(context);
    }

    private void syncCategories (){

        RestService restService = new RestService();
        List<CategoryEntity> categoryEntityList = CategoryEntity.selectAll("");
        List<CategoryModel> categoryModels = new ArrayList<>();
        for (int i = 0; i < categoryEntityList.size(); i ++) {
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

    private void syncExpenses(){
        RestService restService = new RestService();
        List<ExpensesEntity> expensesEntityList = ExpensesEntity.selectAll("");
        List<ExpensesModel> expensesModels = new ArrayList<>();
        for (int i = 0; i < expensesEntityList.size(); i ++){
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

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }
}
