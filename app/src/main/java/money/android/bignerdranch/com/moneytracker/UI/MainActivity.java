package money.android.bignerdranch.com.moneytracker.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.LocaleList;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Choreographer;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import money.android.bignerdranch.com.moneytracker.UI.fragments.CategoryFragment;
import money.android.bignerdranch.com.moneytracker.UI.fragments.ExpensesFragment;
import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.fragments.SettingFragment;
import money.android.bignerdranch.com.moneytracker.UI.fragments.StatisticFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    protected Toolbar toolbar;
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    TextView toolTitel;
    Bundle saveInst;
    public static final String TAG = "myLog";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        saveInst = savedInstanceState;
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        Log.d(TAG, "onCreate");

        if (savedInstanceState == null)
        {
            replaceFragment(new ExpensesFragment());
        }


    }
    private void replaceFragment(Fragment fragment) {
          String backStackName = fragment.getClass().getName();


          FragmentManager manager = getSupportFragmentManager();
          boolean fragmentPopped = manager.popBackStackImmediate(backStackName, 0);


          if (! fragmentPopped && manager.findFragmentByTag(backStackName) == null) {
                  FragmentTransaction ft = manager.beginTransaction();
                  ft.replace(R.id.main_container, fragment, backStackName);
                   ft.addToBackStack(backStackName);
                   ft.commit();
          }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        toolTitel = (TextView) findViewById(R.id.header_drawer);
        switch (id)
        {
            case R.id.spendItem:
                ExpensesFragment ef = new ExpensesFragment();
                replaceFragment(ef);
                toolTitel.setText("Траты");
                break;
            case R.id.categoryItem:
                CategoryFragment cf = new CategoryFragment();
                replaceFragment(cf);
                toolTitel.setText("Категории");
                break;
            case R.id.statItem:
                StatisticFragment sf = new StatisticFragment();
                replaceFragment(sf);
                toolTitel.setText("Статистики");
                break;
            case R.id.settingItem:
                SettingFragment setf = new SettingFragment();
                replaceFragment(setf);
                toolTitel.setText("Настройки");
                break;
            case R.id.exitItem:
                break;
            default:
                return false;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

}
