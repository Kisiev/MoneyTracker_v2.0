package money.android.bignerdranch.com.moneytracker.UI;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import money.android.bignerdranch.com.moneytracker.R;
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
    @ViewById(R.id.liner)
    LinearLayout liner;

    private UserRegistrationModel userRegistrationModel = null;
    private Animation animation;
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
            case "success":
                finish();
                startActivity(new Intent(RegistratioActivity.this, MainActivity.class));
                break;
            case "Login busy already":
                Snackbar.make(view, getString(R.string.registerNON), Snackbar.LENGTH_LONG).show();
                break;
            case "null":
                Snackbar.make(view, R.string.errorNet, Snackbar.LENGTH_LONG).show();
            default:
                return;

        }

    }



    @AfterViews
    public void main(){
        animation = AnimationUtils.loadAnimation(this, R.anim.trans);
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if (login.getText().length() < 10 && login.getText().length() > 5 && pass.getText().length() > 0)
                        register(view);
                    else {
                        if (pass.getText().length() < 1)
                            Snackbar.make(view, R.string.pass_lenght, Snackbar.LENGTH_LONG).show();
                        else
                            Snackbar.make(view, R.string.login_lenght, Snackbar.LENGTH_LONG).show();
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
