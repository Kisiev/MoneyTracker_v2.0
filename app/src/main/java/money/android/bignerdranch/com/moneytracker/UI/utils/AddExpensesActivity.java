package money.android.bignerdranch.com.moneytracker.UI.utils;

import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;

@EActivity(R.layout.add_expenses_activity)
public class AddExpensesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

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
        List<String> categories = new ArrayList<String>();
        categories.add("Категория 1");
        categories.add("Категория 2");
        categories.add("Категория 3");
        categories.add("Категория 4");
        categories.add("Категория 5");
        categories.add("Категория 6");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter <String>(this, android.R.layout.simple_spinner_item, categories);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        listSpinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String item = adapterView.getItemAtPosition(i).toString();

        // Showing selected spinner item
        Toast.makeText(adapterView.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
