package money.android.bignerdranch.com.moneytracker.UI.utils;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;

@EActivity (R.layout.add_category_activity)
public class AddCategoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    @ViewById(R.id.category_et)
    EditText categoryEditText;
    @ViewById(R.id.button_add_category)
    Button addCategoryButton;
    @ViewById(R.id.button_cancel_category)
    Button cancelCategoryButton;

    private void addCategory(){
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setName(categoryEditText.getText().toString());
        categoryEntity.save();
    }

    @AfterViews
    public void main (){

        addCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categoryEditText.getText().equals("")){
                    Toast.makeText(AddCategoryActivity.this, getString(R.string.category_toast), Toast.LENGTH_LONG).show();
                } else
                addCategory();
            }
        });
        cancelCategoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
