package money.android.bignerdranch.com.moneytracker.UI.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ClickListener;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ExpensesAdapter;
import money.android.bignerdranch.com.moneytracker.UI.adapters.SelectableAdapter;
import money.android.bignerdranch.com.moneytracker.UI.utils.AddExpensesActivity_;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;


@EFragment
public class ExpensesFragment extends Fragment {
    private ExpensesAdapter adapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
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
        actionButton = (FloatingActionButton) rootView.findViewById(R.id.expensesActionButton);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_add:
                if (CategoryEntity.selectAll("").size() > 0) {
                    addExpenses("300", "item", "28.10.16", CategoryEntity.selectAll("").get(0));
                    addExpenses("300", "item2", "28.10.16", CategoryEntity.selectAll("").get(0));
                    addExpenses("300", "item3", "28.10.16", CategoryEntity.selectAll("").get(0));
                    addExpenses("300", "item4", "28.10.16", CategoryEntity.selectAll("").get(0));
                    addExpenses("300", "item5", "28.10.16", CategoryEntity.selectAll("").get(0));
                    addExpenses("300", "item6", "28.10.16", CategoryEntity.selectAll("").get(0));
                    addExpenses("300", "item7", "28.10.16", CategoryEntity.selectAll("").get(0));
                    loadExpenses("");
                } else {
                    Toast.makeText(getActivity(), R.string.add_category_begin, Toast.LENGTH_LONG).show();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addExpenses(String sum, String name, String data, CategoryEntity categoryEntity) {
        ExpensesEntity expensesEntity = new ExpensesEntity();

        expensesEntity.setSum(sum);
        expensesEntity.setName(name);
        expensesEntity.setDate(data);
        expensesEntity.setCategory(categoryEntity);
        expensesEntity.save();

    }

    @Background(id = SEARCH_QUERY, delay = 10000)
    void expensesQuery(String query) {
        loadExpenses(query);
    }

    private void loadExpenses(final String quary) {
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
                adapter = new ExpensesAdapter(data, new ClickListener() {
                    @Override
                    public void onItemSelected(int position) {
                        if (actionMode != null) {
                            toggleSelection(position);
                        }
                    }

                    @Override
                    public boolean onItemLongSelected(int position) {
                        if (actionMode == null) {
                            AppCompatActivity activity = (AppCompatActivity) getActivity();
                            actionMode = activity.startSupportActionMode(actionModeCallback);
                        }
                        toggleSelection(position);
                        return true;
                    }
                });
                recyclerView.setAdapter(adapter);
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
                AddExpensesActivity_.intent(getActivity()).start()
                        .withAnimation(R.anim.enter_pull_in, R.anim.exit_fade_out);
                /*Snackbar.make(view, getString(R.string.spends), Snackbar.LENGTH_LONG).show();*/
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        expensesQuery("");
    }


    private void toggleSelection(int position) {
        adapter.toggleSelection(position);
        int count = adapter.getSelectedItemCount();
        if (count == 0) {
            actionMode.finish();
        } else {
            actionMode.setTitle(String.valueOf(count));
            actionMode.invalidate();
        }
    }

    private class ActionModeCallback implements ActionMode.Callback {

        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.contextual_action_bar, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()) {
                case R.id.menu_remove:
                    adapter.removeItems(adapter.getSelectedItems());
                    mode.finish();
                    return true;
                case R.id.menu_selected_all:
                    adapter.clearSelection();
                    for (int i = 0; i < adapter.getItemCount(); i ++) {
                        adapter.toggleSelection(i);
                    }
                    return true;
                default:
                    return false;
            }

        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            adapter.clearSelection();
            actionMode = null;

        }
    }
}
