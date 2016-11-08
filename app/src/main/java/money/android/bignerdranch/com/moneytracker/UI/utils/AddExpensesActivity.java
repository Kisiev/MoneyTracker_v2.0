package money.android.bignerdranch.com.moneytracker.UI.utils;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.adapters.CategoriesSpinnerAdapter;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ExpensesAdapter;
import money.android.bignerdranch.com.moneytracker.UI.adapters.CategoryAdapter;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;

@EActivity(R.layout.add_expenses_activity)
public class AddExpensesActivity extends AppCompatActivity {

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
    @ViewById (R.id.expenses_data_et)
    EditText date_et;
    @ViewById (R.id.toolbar)
    Toolbar toolbar;
    @ViewById (R.id.expenses_data_et)
    EditText dateEdit;

    private void addExpenses(){
        ExpensesEntity expensesEntity = new ExpensesEntity();
        expensesEntity.setSum(sumEdit.getText().toString());
        expensesEntity.setName(descEdit.getText().toString());
        expensesEntity.setDate(date_et.getText().toString());
        CategoryEntity category = (CategoryEntity) listSpinner.getSelectedItem();
        expensesEntity.setCategory(category);
        expensesEntity.save();
    }

    @AfterViews
    protected void main (){

        long date = System.currentTimeMillis();
        dateEdit.setText(new SimpleDateFormat("dd.MM.yyyy").format(date));


        List addCategoryList = new ArrayList<>();
        addCategoryList.addAll(CategoryEntity.selectAll(""));


        CategoriesSpinnerAdapter categories = new CategoriesSpinnerAdapter(this, addCategoryList);

        categories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categories.notifyDataSetChanged();
        listSpinner.setAdapter(categories);


        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sumEdit.getText().toString().equals("")
                        || descEdit.getText().toString().equals("")
                        || date_et.getText().toString().equals("")){
                    Toast.makeText(AddExpensesActivity.this, "Не заполнены поля", Toast.LENGTH_LONG).show();
                } else {
                    addExpenses();
                    sumEdit.setText("");
                    descEdit.setText("");
                    listSpinner.setSelection(0);
                }

            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();

            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.enter_fade_in, R.anim.exit_push_out);
    }



}
