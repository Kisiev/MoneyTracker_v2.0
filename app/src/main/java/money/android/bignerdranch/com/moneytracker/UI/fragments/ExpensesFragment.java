package money.android.bignerdranch.com.moneytracker.UI.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ExpensesAdapter;
import money.android.bignerdranch.com.moneytracker.UI.utils.AddExpensesActivity_;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;


@EFragment
public class ExpensesFragment extends Fragment {

    RecyclerView recyclerView;
    FloatingActionButton actionButton;
    Toolbar toolbar;
    SearchView searchView;
    final public static int ID = 1;
    final public String SEARCH_QUERY = "search_query";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expenses_fragment, container, false);
        toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_expenses);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        actionButton = (FloatingActionButton)  rootView.findViewById(R.id.expensesActionButton);
        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main, menu);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.action_search));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {
                expensesQuery(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                BackgroundExecutor.cancelAll(SEARCH_QUERY, true);
                expensesQuery(s);
                return false;
            }
        });
    }

    @Background(id = SEARCH_QUERY, delay = 10000)
    void expensesQuery(String query){
        loadExpenses(query);
    }
    private void loadExpenses(final String quary){
        getLoaderManager().restartLoader(ID, null, new LoaderManager.LoaderCallbacks<List<ExpensesEntity>>() {
            @Override
            public Loader<List<ExpensesEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<ExpensesEntity>> loader = new AsyncTaskLoader<List<ExpensesEntity>>(getActivity()) {
                    @Override
                    public List<ExpensesEntity> loadInBackground() {
                        return ExpensesEntity.selectAll(quary);
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
        });
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
    public void onResume() {
        super.onResume();
        expensesQuery("");
    }
}
