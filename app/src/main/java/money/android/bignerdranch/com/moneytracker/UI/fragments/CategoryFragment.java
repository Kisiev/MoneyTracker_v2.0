package money.android.bignerdranch.com.moneytracker.UI.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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
import money.android.bignerdranch.com.moneytracker.UI.utils.AddCategoryActivity;
import money.android.bignerdranch.com.moneytracker.UI.utils.AddCategoryActivity_;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;
import money.android.bignerdranch.com.moneytracker.models.CategoryModel;
import money.android.bignerdranch.com.moneytracker.models.ExpenseModel;

/**
 * Created by User on 19.09.2016.
 */
public class CategoryFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<CategoryEntity>>{

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    private RecyclerView recyclerView;
    CategoryAdapter categoryAdapter;
    FloatingActionButton actionButton;

    final public static int ID = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.category_fragment, container, false);
        actionButton = (FloatingActionButton) rootView.findViewById(R.id.categoryActionButton);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }




    @Override
    public void onStart() {
        super.onStart();
        getLoaderManager().restartLoader(ID, null, this);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), AddCategoryActivity_.class));
            }
        });
    }

    @Override
    public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
        final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
            @Override
            public List<CategoryEntity> loadInBackground() {
                return CategoryEntity.selectAll();
            }
        };
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<CategoryEntity>> loader, List<CategoryEntity> data) {
        recyclerView.setAdapter(new CategoryAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<List<CategoryEntity>> loader) {

    }
}
