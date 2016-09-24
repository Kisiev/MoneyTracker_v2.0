package money.android.bignerdranch.com.moneytracker.UI.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class CategoryFragment extends Fragment {

    RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.category_fragment, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_category);
        categoryAdapter = new CategoryAdapter(getCategory());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(categoryAdapter);

        return rootView;
    }

    private List<CategoryModel> getCategory()
    {
        List<CategoryModel> category = new ArrayList<>();
        category.add(new CategoryModel("Категоря 1"));
        category.add(new CategoryModel("Категоря 2"));
        category.add(new CategoryModel("Категоря 3"));
        category.add(new CategoryModel("Категоря 4"));
        category.add(new CategoryModel("Категоря 5"));
        category.add(new CategoryModel("Категоря 6"));
        return category;
    }
}
