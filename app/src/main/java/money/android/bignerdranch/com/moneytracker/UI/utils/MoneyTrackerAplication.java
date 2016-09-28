package money.android.bignerdranch.com.moneytracker.UI.utils;

import android.app.Application;

import com.activeandroid.ActiveAndroid;

public class MoneyTrackerAplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ActiveAndroid.initialize(this);
    }
}
