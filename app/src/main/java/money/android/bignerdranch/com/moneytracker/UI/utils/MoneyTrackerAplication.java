package money.android.bignerdranch.com.moneytracker.UI.utils;

import android.app.Application;
import android.content.SharedPreferences;

import com.activeandroid.ActiveAndroid;

public class MoneyTrackerAplication extends Application {
    private static SharedPreferences sharedPreferences;

    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
        sharedPreferences = getSharedPreferences(ConstantsManager.SHARED_PREF, MODE_PRIVATE);
    }

    public static void saveAuthToken (String token){
        sharedPreferences.edit().putString(ConstantsManager.TOKEN_KEY, token).apply();
    }

    public static String getAuthToken(){
        return sharedPreferences.getString(ConstantsManager.TOKEN_KEY, "");
    }

    public static void saveGoogleAuthToken (String token){
        sharedPreferences.edit().putString(ConstantsManager.GOOGLE_TOKEN_KEY, token).apply();
    }

    public static String getGoogleAuthToken(){
        return sharedPreferences.getString(ConstantsManager.GOOGLE_TOKEN_KEY, "");
    }

    public static String getGoogleAvatar(){
        return sharedPreferences.getString(ConstantsManager.AVATAR, "");
    }

    public static void saveGoogleAvatar (String url){
        sharedPreferences.edit().putString(ConstantsManager.AVATAR, url).apply();
    }

    public static String getUserName(){
        return sharedPreferences.getString(ConstantsManager.USER_NAME, "");
    }

    public static void saveGoogleUserName(String name){
        sharedPreferences.edit().putString(ConstantsManager.USER_NAME, name).apply();
    }

    public static String getUserEmile(){
        return sharedPreferences.getString(ConstantsManager.USER_EMILE, "");
    }

    public static void saveGoogleUserEmail(String emile){
        sharedPreferences.edit().putString(ConstantsManager.USER_EMILE, emile).apply();
    }


}
