package money.android.bignerdranch.com.moneytracker.UI.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import money.android.bignerdranch.com.moneytracker.R;

@EActivity(R.layout.add_expenses_activity)
public class AddExpensesActivity extends AppCompatActivity{

    @ViewById (R.id.expenses_sum_et)
    EditText sumEdit;
    @ViewById (R.id.expenses_description_et)
    EditText descEdit;
    @ViewById (R.id.expenses_category_spinner)
    Spinner listSpinner;
    @ViewById (R.id.expenses_cancel_button)
    Button cancelButton;
    @ViewById (R.id.expenses_ok_button)
    Button okButton;

    @AfterViews
    protected void main (){

    }
}
