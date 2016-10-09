package money.android.bignerdranch.com.moneytracker.UI;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.utils.ConstantsManager;
import money.android.bignerdranch.com.moneytracker.UI.utils.MoneyTrackerAplication;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserLoginModel;
import money.android.bignerdranch.com.moneytracker.rest.RestService;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserRegistrationModel;

@EActivity(R.layout.registration_activity)
public class RegistratioActivity extends AppCompatActivity{

    final public static String LOG_OUT = "my_log";
    @ViewById(R.id.login_et)
    EditText login;
    @ViewById(R.id.password_et)
    EditText pass;
    @ViewById(R.id.confirm_password_et)
    EditText confirm_pass;
    @ViewById (R.id.signin_btn)
    Button signIn;
    @ViewById(R.id.checked_ch)
    CheckBox register_ch;


    private UserRegistrationModel userRegistrationModel;
    private UserLoginModel userLoginModel;
    @Background
    public void register(View view){

        RestService restService = new RestService();
        boolean exep = false;

        try {
            userRegistrationModel = restService.register(login.getText().toString(), pass.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(view, R.string.errorNet, Snackbar.LENGTH_LONG).show();
            exep = true;
        }
       // Log.d(LOG_OUT, "Status" + userRegistrationModel.getStatus());


    if (exep == false)
        switch (userRegistrationModel.getStatus().toString()){
            case ConstantsManager.REGISTRATION_SUCCEED:
                Snackbar.make(view, "Регистрация усшешно завершена!", Snackbar.LENGTH_LONG).show();
                checkOut();
                break;
            case ConstantsManager.LOGIN_BUSY:
                Snackbar.make(view, getString(R.string.registerNON), Snackbar.LENGTH_LONG).show();
                break;
            case "null":
                Snackbar.make(view, R.string.errorNet, Snackbar.LENGTH_LONG).show();
            default:
                return;

        }

    }

    @Background
    public void login(View view){

        RestService restService = new RestService();
        boolean exep = false;

        try {
             userLoginModel = restService.login(login.getText().toString(), pass.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
            Snackbar.make(view, R.string.errorNet, Snackbar.LENGTH_LONG).show();
            exep = true;
        }
        // Log.d(LOG_OUT, "Status" + userRegistrationModel.getStatus());


        if (exep == false)
            switch (userLoginModel.getStatus().toString()){
                case ConstantsManager.LOGIN_SUCCEED:
                    MoneyTrackerAplication.seveAuthToken(userLoginModel.getAuthToken());
                    navigateToMain();
                    break;
                case ConstantsManager.WRONG_LOGIN:
                    Snackbar.make(view, R.string.wrong_login, Snackbar.LENGTH_LONG).show();
                    break;
                case "null":
                    Snackbar.make(view, R.string.errorNet, Snackbar.LENGTH_LONG).show();
                default:
                    return;

            }

    }

    @UiThread
    void navigateToMain(){
        startActivity(new Intent(RegistratioActivity.this, MainActivity.class));
    }
    @UiThread
    void checkOut(){
        register_ch.setChecked(false);
        login.setText("");
        pass.setText("");
    }


    @AfterViews
    public void main(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(register_ch.isChecked()) {

                    if ((login.getText().length() < 10 && login.getText().length() > 5) && (pass.getText().length() > 0) && (confirm_pass.getText().toString().equals(pass.getText().toString())))
                        register(view);
                    else {
                        if (pass.getText().length() == 0)
                            Snackbar.make(view, R.string.pass_lenght, Snackbar.LENGTH_LONG).show();
                        else if (login.getText().length() > 10 || login.getText().length() < 5)
                            Snackbar.make(view, R.string.login_lenght, Snackbar.LENGTH_LONG).show();
                        else if (!confirm_pass.getText().toString().equals(pass.getText().toString()))
                            Snackbar.make(view, R.string.confirmError, Snackbar.LENGTH_LONG).show();
                    }

                } else {
                        login(view);
                }

            }
        });

        register_ch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (register_ch.isChecked()) {
                    confirm_pass.setVisibility(View.VISIBLE);
                    signIn.setText(getString(R.string.registration_button_text));
                } else {
                    confirm_pass.setVisibility(View.GONE);
                    signIn.setText(getString(R.string.logIn_button_text));
                }

            }
        });

    }
}
