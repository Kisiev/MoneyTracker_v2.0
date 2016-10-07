package money.android.bignerdranch.com.moneytracker.UI.utils;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

import com.activeandroid.ActiveAndroid;

public class MoneyTrackerAplication extends Application {
    private static SharedPreferences sharedPreferences;



    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        sharedPreferences = getSharedPreferences(ConstantsManager.SHARED_PREF, MODE_PRIVATE);
    }

    public static void seveAuthToken (String token){
        sharedPreferences.edit().putString(ConstantsManager.TOKEN_KEY, token).apply();
    }

    public static String getAuthToken(){
        return sharedPreferences.getString(ConstantsManager.TOKEN_KEY, "");
    }
}
