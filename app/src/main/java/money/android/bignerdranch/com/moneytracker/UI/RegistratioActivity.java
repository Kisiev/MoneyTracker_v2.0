package money.android.bignerdranch.com.moneytracker.UI;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.rest.RestService;
import money.android.bignerdranch.com.moneytracker.rest.registration.UserRegistrationModel;

@EActivity(R.layout.registration_activity)
public class RegistratioActivity extends AppCompatActivity{

    final public static String LOG_OUT = "my_log";
    @ViewById(R.id.login_et)
    EditText login;
    @ViewById(R.id.password_et)
    EditText pass;
    @ViewById (R.id.signin_btn)
    Button signIn;

    private UserRegistrationModel userRegistrationModel = null;
    @Background
    public void register(View view){

        RestService restService = new RestService();

        try {
            userRegistrationModel = restService.register(login.getText().toString(), pass.getText().toString());
            Log.d(LOG_OUT, "Status" + userRegistrationModel.getStatus());
        } catch (IOException e) {
            e.printStackTrace();
        }

        switch (userRegistrationModel.getStatus().toString()){
            case "success":
                finish();
                startActivity(new Intent(RegistratioActivity.this, MainActivity.class));
                break;
            case "Login busy already":
                Snackbar.make(view, getString(R.string.registerNON), Snackbar.LENGTH_LONG).show();
                break;
            default:
                return;
        }

    }

    @AfterViews
    public void main(){
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(login.getText().length() < 10 && login.getText().length() > 5 && pass.getText().length() > 0)
                    register(view);
                else {
                    if (pass.getText().length() < 1)
                        Snackbar.make(view, R.string.pass_lenght, Snackbar.LENGTH_LONG).show();
                    else
                        Snackbar.make(view, R.string.login_lenght, Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }
}
