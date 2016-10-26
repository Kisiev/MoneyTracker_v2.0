package money.android.bignerdranch.com.moneytracker.UI;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.media.VolumeProviderCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.AccountPicker;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import money.android.bignerdranch.com.moneytracker.BuildConfig;
import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.utils.ConstantsManager;
import money.android.bignerdranch.com.moneytracker.UI.utils.MoneyTrackerAplication;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserLoginModel;
import money.android.bignerdranch.com.moneytracker.rest.RestService;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserRegistrationModel;

import static android.R.attr.data;

@EActivity(R.layout.registration_activity)
public class RegistratioActivity extends AppCompatActivity {

    final public static int REQUEST_CODE = 10;
    final public static String LOG_OUT = "my_log";
    @ViewById(R.id.login_et)
    EditText loginEt;
    @ViewById(R.id.password_et)
    EditText pass;
    @ViewById(R.id.confirm_password_et)
    EditText confirm_pass;
    @ViewById(R.id.signin_btn)
    Button signIn;
    @ViewById(R.id.checked_ch)
    CheckBox register_ch;
    @ViewById(R.id.reg_layout)
    LinearLayout linearLayout;
    @ViewById (R.id.login_google)
    SignInButton login_google_btn;




    @Background
    public void register(String login, String password) {
        RestService restService = new RestService();

        try {
            UserRegistrationModel registrationModel = restService.register(login, password);
            if (registrationModel.getStatus().equals(ConstantsManager.REGISTRATION_SUCCEED)) {
                navigateToReg();
                finish();
            } else {
                loginBusy();
            }
        } catch (IOException e) {
            showUnknownError();
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

    }

    @Background
    public void login(String login, String password) {

        RestService restService = new RestService();

        try {
            UserLoginModel userLoginModel = restService.login(login, password);
            if (userLoginModel.getStatus().equals(ConstantsManager.LOGIN_SUCCEED)){
                MoneyTrackerAplication.seveAuthToken(userLoginModel.getAuthToken());
                navigateToMain();
                finish();
            } else {
                wrongLogin();
            }
        } catch (IOException e) {
            showUnknownError();
            if (BuildConfig.DEBUG) {
                e.printStackTrace();
            }
        }

    }

    @UiThread
    void wrongLogin() {
        Snackbar.make(linearLayout, R.string.wrong_login, Snackbar.LENGTH_LONG).show();
    }

    @UiThread
    void navigateToMain() {
        startActivity(new Intent(RegistratioActivity.this, MainActivity.class));
    }

    @UiThread
    void navigateToReg() {
        register_ch.setChecked(false);
        loginEt.setText("");
        pass.setText("");
        Snackbar.make(linearLayout, R.string.success_reg, Snackbar.LENGTH_LONG).show();
    }

    @UiThread
    void loginBusy() {
        Snackbar.make(linearLayout, R.string.registerNON, Snackbar.LENGTH_LONG).show();
    }


    @UiThread
    void showUnknownError() {
        Snackbar.make(linearLayout, R.string.error, Snackbar.LENGTH_LONG).show();
    }


    @AfterViews
    public void main() {

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (register_ch.isChecked()) {
                    if (isNwConnected(RegistratioActivity.this)) {
                        String login = loginEt.getText().toString();
                        String password = pass.getText().toString();
                        String confirm = confirm_pass.getText().toString();
                        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                            if (login.length() >= 5 && password.length() >= 5 && login.length() <= 10) {
                                if (confirm.equals(password)) {
                                    register(login, password);
                                } else {
                                    Snackbar.make(linearLayout, R.string.confirmError, Snackbar.LENGTH_LONG).show();
                                }
                            } else {
                                Snackbar.make(linearLayout, R.string.login_lenght, Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            if (TextUtils.isEmpty(login))
                                Snackbar.make(linearLayout ,getString(R.string.empty_login), Snackbar.LENGTH_LONG).show(); //поле логин - пустое
                            if (TextUtils.isEmpty(password))
                                Snackbar.make(linearLayout ,getString(R.string.empty_pass), Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(linearLayout, R.string.errorNet, Snackbar.LENGTH_LONG).show(); //сообщаем, что интернета нет
                    }

                } else {
                    if (isNwConnected(RegistratioActivity.this)) {
                        String login = loginEt.getText().toString();
                        String password = pass.getText().toString();
                        if (!TextUtils.isEmpty(login) && !TextUtils.isEmpty(password)) {
                            login(login, password);
                        } else {
                            Snackbar.make(linearLayout, R.string.empty_log_pass, Snackbar.LENGTH_LONG).show();
                        }
                    } else {
                        Snackbar.make(linearLayout, R.string.errorNet, Snackbar.LENGTH_LONG).show();
                    }
                }

            }

        });

        register_ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
                @Override
                public void onCheckedChanged (CompoundButton compoundButton,boolean b){
                    if (register_ch.isChecked()) {
                        confirm_pass.setVisibility(View.VISIBLE);
                        signIn.setText(getString(R.string.registration_button_text));
                    } else {
                    confirm_pass.setVisibility(View.GONE);
                    signIn.setText(getString(R.string.logIn_button_text));
                    }

                }
        });

        login_google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AccountPicker.newChooseAccountIntent(null, null, new String[]{"com.google"}, false,
                        null, null, null, null);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });



    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK){
            logInWithGoogle(data);
        }
    }

    @Background
    void logInWithGoogle(Intent data){
        String token = null;
        final String accountName = data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
        final String accountType = data.getStringExtra(AccountManager.KEY_ACCOUNT_TYPE);

        Account account = new Account(accountName, accountType);
        try {
            token = GoogleAuthUtil.getToken(this,account, ConstantsManager.SCOPES);

        }
        catch (UserRecoverableAuthException userAuthEx){
            startActivityForResult(userAuthEx.getIntent(), REQUEST_CODE);
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (GoogleAuthException e) {
            Log.d(LOG_OUT, "Fatal Authorization Exception" + e.getLocalizedMessage());
            e.printStackTrace();
        }
        Log.d(LOG_OUT,"token"+ token);
        if (token != null){
            MoneyTrackerAplication.seveGoogleAuthToken(token);
            navigateToMain();
            finish();
        }
    }

    public static boolean isNwConnected(Context context) {
        if (context == null) {
            return true;
        }
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nwInfo = connectivityManager.getActiveNetworkInfo();
        if (nwInfo != null && nwInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }



}
