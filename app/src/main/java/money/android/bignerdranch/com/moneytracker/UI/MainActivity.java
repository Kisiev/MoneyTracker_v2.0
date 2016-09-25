package money.android.bignerdranch.com.moneytracker.UI;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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
            setTitle(getString(R.string.expenses_header_nav));
        }

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Fragment f = getSupportFragmentManager().findFragmentById(R.id.main_container);
                if (f != null)
                {
                    updateToolbarTitle(f);
                } else finish();
            }
        });



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


    private void updateToolbarTitle (Fragment fragment)
    {
        String fragmentClassName = fragment.getClass().getName();
        if (fragmentClassName.equals(ExpensesFragment.class.getName()))
        {
            setTitle(getString(R.string.expenses_header_nav));
            navigationView.setCheckedItem(R.id.spendItem);
        }else
        if (fragmentClassName.equals(CategoryFragment.class.getName()))
        {
            setTitle(getString(R.string.category_header_nav));
            navigationView.setCheckedItem(R.id.categoryItem);
        }else
        if (fragmentClassName.equals(StatisticFragment.class.getName()))
        {
            setTitle(getString(R.string.statistic_header_nav));
            navigationView.setCheckedItem(R.id.statItem);
        }else
        if (fragmentClassName.equals(SettingFragment.class.getName()))
        {
            setTitle(getString(R.string.setting_header_nav));
            navigationView.setCheckedItem(R.id.settingItem);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        if (drawer != null) {
            drawer.closeDrawer(GravityCompat.START);
        }
        int id = item.getItemId();

        switch (id)
        {
            case R.id.spendItem:
                ExpensesFragment ef = new ExpensesFragment();
                replaceFragment(ef);
                break;
            case R.id.categoryItem:
                CategoryFragment cf = new CategoryFragment();
                replaceFragment(cf);
                break;
            case R.id.statItem:
                StatisticFragment sf = new StatisticFragment();
                replaceFragment(sf);
                break;
            case R.id.settingItem:
                SettingFragment setf = new SettingFragment();
                replaceFragment(setf);
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
