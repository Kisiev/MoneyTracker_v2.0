package money.android.bignerdranch.com.moneytracker.UI;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;

import org.androidannotations.annotations.EActivity;

import money.android.bignerdranch.com.moneytracker.R;

/**
 * Created by User on 25.09.2016.
 */

@EActivity (R.layout.splash)
public class SplashActivity extends Activity {

   boolean f = new Handler().postDelayed(new Runnable() {
       @Override
       public void run() {
           Intent intent = new Intent(SplashActivity.this, RegistratioActivity_.class);
           startActivity(intent);
           finish();
       }
   }, 2000);

}
