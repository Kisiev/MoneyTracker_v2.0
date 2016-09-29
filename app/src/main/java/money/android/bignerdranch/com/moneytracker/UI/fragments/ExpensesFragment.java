package money.android.bignerdranch.com.moneytracker.UI.fragments;


import android.app.Activity;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.MainActivity;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ExpensesAdapter;
import money.android.bignerdranch.com.moneytracker.UI.utils.AddExpensesActivity_;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;
import money.android.bignerdranch.com.moneytracker.models.ExpenseModel;

public class ExpensesFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ExpensesEntity>>{

    RecyclerView recyclerView;
    ExpensesAdapter expensesAdapter;
    FloatingActionButton actionButton;
    Toolbar toolbar;
    final public static int ID = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expenses_fragment, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_expenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        actionButton = (FloatingActionButton)  rootView.findViewById(R.id.expensesActionButton);
        getLoaderManager().restartLoader(ID, null, this);
        return rootView;
    }




    @Override
    public void onStart() {
        super.onStart();

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddExpensesActivity_.class);
                startActivity(intent);
                /*Snackbar.make(view, getString(R.string.spends), Snackbar.LENGTH_LONG).show();*/
            }
        });
    }


    @Override
    public Loader<List<ExpensesEntity>> onCreateLoader(int id, Bundle args) {
        final AsyncTaskLoader<List<ExpensesEntity>> loader = new AsyncTaskLoader<List<ExpensesEntity>>(getActivity()) {
            @Override
            public List<ExpensesEntity> loadInBackground() {
                return ExpensesEntity.selectAll();
            }
        };
        loader.forceLoad();
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<List<ExpensesEntity>> loader, List<ExpensesEntity> data) {
        recyclerView.setAdapter(new ExpensesAdapter(data));
    }

    @Override
    public void onLoaderReset(Loader<List<ExpensesEntity>> loader) {

    }
}
