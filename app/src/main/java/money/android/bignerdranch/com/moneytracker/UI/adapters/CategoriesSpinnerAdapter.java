package money.android.bignerdranch.com.moneytracker.UI.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import org.junit.experimental.categories.Categories;

import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;



public class CategoriesSpinnerAdapter extends ArrayAdapter<CategoryEntity> implements SpinnerAdapter{

    List<CategoryEntity> categories;
    public CategoriesSpinnerAdapter(Context context, List<CategoryEntity> categories) {
        super(context, 0, categories);
        this.categories = categories;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        CategoryEntity category = (CategoryEntity) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categories_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_category_item);
        name.setText(category.name);

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {

        CategoryEntity category = (CategoryEntity) getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.categories_item, parent, false);
        }

        TextView name = (TextView) convertView.findViewById(R.id.name_category_item);
        name.setText(category.name);

        return convertView;
    }
}

