package money.android.bignerdranch.com.moneytracker.UI;

import android.content.ClipData;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.google.gson.Gson;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.UI.fragments.CategoryFragment;
import money.android.bignerdranch.com.moneytracker.UI.fragments.ExpensesFragment;
import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.fragments.SettingFragment;
import money.android.bignerdranch.com.moneytracker.UI.fragments.StatisticFragment;
import money.android.bignerdranch.com.moneytracker.UI.utils.CircleTransform;
import money.android.bignerdranch.com.moneytracker.UI.utils.MoneyTrackerAplication;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.rest.Models.CategoryModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserGetDataModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserSyncCategoriesModel;
import money.android.bignerdranch.com.moneytracker.rest.RestService;
import money.android.bignerdranch.com.moneytracker.sync.TrackerSyncAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    protected Toolbar toolbar;
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    protected TextView nameText;
    protected TextView emileText;
    UserGetDataModel userGetDataModel;
    UserSyncCategoriesModel userSyncCategoriesModel;
    RestService restService = new RestService();
    Bundle saveInst;
    ImageView avatar;
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


        View headerView = navigationView.getHeaderView(0);
        avatar = (ImageView) headerView.findViewById(R.id.imageView);
        nameText = (TextView) headerView.findViewById(R.id.name_text);
        emileText = (TextView) headerView.findViewById(R.id.email_text);


                getParam();


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




        if (CategoryEntity.selectAll("").size() == 0){
            addCategory("Продукты");
            addCategory("Техника");
            addCategory("Одежда");
            addCategory("Прочее");
        }

        TrackerSyncAdapter.initializeSyncAdapter(this);

    }


    public void getParam(){

        if (!MoneyTrackerAplication.getGoogleAuthToken().equals("")) {
            saveGlideParam(MoneyTrackerAplication.getGoogleAvatar());
            nameText.setText(MoneyTrackerAplication.getUserName());
            emileText.setText(MoneyTrackerAplication.getUserEmile());
        }
    }

    private void saveGlideParam(String picture){

        Glide.with(this)
                .load(picture)
                .bitmapTransform(new CircleTransform(this))
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(new BitmapImageViewTarget(avatar).getView());
    }

    private void addCategory(String name){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(name);
        categoryEntity.save();

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
                MoneyTrackerAplication.saveAuthToken("");
                MoneyTrackerAplication.saveGoogleAuthToken("");
                Intent intent = new Intent(this, RegistratioActivity_.class);
                startActivity(intent);
                finish();
                break;
            default:
                return false;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
