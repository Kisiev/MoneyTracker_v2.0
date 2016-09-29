package money.android.bignerdranch.com.moneytracker.UI.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.adapters.CategoryAdapter;
import money.android.bignerdranch.com.moneytracker.models.CategoryModel;
import money.android.bignerdranch.com.moneytracker.models.ExpenseModel;

/**
 * Created by User on 19.09.2016.
 */
public class CategoryFragment extends Fragment implements View.OnClickListener{

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.category_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_category);
       // categoryAdapter = new CategoryAdapter(getCategory());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryAdapter);
        onClick(rootView);
        return rootView;
    }

    private List<CategoryModel> getCategory()
    {
        List<CategoryModel> category = new ArrayList<>();
        category.add(new CategoryModel("Категория 1"));
        category.add(new CategoryModel("Категория 2"));
        category.add(new CategoryModel("Категория 3"));
        category.add(new CategoryModel("Категория 4"));
        category.add(new CategoryModel("Категория 5"));
        category.add(new CategoryModel("Категория 6"));
        return category;
    }

    @Override
    public void onClick(View view) {
        FloatingActionButton actionButton = (FloatingActionButton) view.findViewById(R.id.categoryActionButton);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.category), Snackbar.LENGTH_LONG).show();
            }
        });
    }
}
