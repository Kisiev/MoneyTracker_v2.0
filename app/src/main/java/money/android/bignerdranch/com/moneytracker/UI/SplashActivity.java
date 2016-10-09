package money.android.bignerdranch.com.moneytracker.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.utils.MoneyTrackerAplication;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserLoginModel;


@EActivity (R.layout.splash)
public class SplashActivity extends Activity {
    @AfterViews
    void main() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (MoneyTrackerAplication.getAuthToken().equals("")) {
                    startActivity(new Intent(SplashActivity.this, RegistratioActivity_.class));
                    finish();
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }

            }
        }, 2000);
    }
}
