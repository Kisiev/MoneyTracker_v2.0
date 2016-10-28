package money.android.bignerdranch.com.moneytracker.UI.fragments;

import android.app.Dialog;
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
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.api.BackgroundExecutor;

import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.adapters.CategoryAdapter;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ClickListener;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ExpensesAdapter;
import money.android.bignerdranch.com.moneytracker.UI.utils.AddCategoryActivity_;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;

@EFragment
public class CategoryFragment extends Fragment {

    private CategoryAdapter adapter;
    private ActionModeCallback actionModeCallback = new ActionModeCallback();
    private ActionMode actionMode;
    private RecyclerView recyclerView;
    FloatingActionButton actionButton;
    SearchView searchView;
    final public static int ID = 1;
    final String SEARCH_CATEGORY = "search_category";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.category_fragment, container, false);
        actionButton = (FloatingActionButton) rootView.findViewById(R.id.categoryActionButton);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_category);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDialog();
            }
        });

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
                categoryQuery(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                BackgroundExecutor.cancelAll(SEARCH_CATEGORY, true);
                categoryQuery(s);
                return false;
            }
        });
    }

    @Background(delay = 1000, id = SEARCH_CATEGORY)
    void categoryQuery(String query) {
        loadCategory(query);
    }

    private void loadCategory(final String query) {
        getLoaderManager().restartLoader(ID, null, new LoaderManager.LoaderCallbacks<List<CategoryEntity>>() {
            @Override
            public Loader<List<CategoryEntity>> onCreateLoader(int id, Bundle args) {
                final AsyncTaskLoader<List<CategoryEntity>> loader = new AsyncTaskLoader<List<CategoryEntity>>(getActivity()) {
                    @Override
                    public List<CategoryEntity> loadInBackground() {
                        return CategoryEntity.selectAll(query);
                    }
                };
                loader.forceLoad();
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<List<CategoryEntity>> loader, List<CategoryEntity> data) {
                adapter = new CategoryAdapter(data, new ClickListener() {
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
            public void onLoaderReset(Loader<List<CategoryEntity>> loader) {
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        categoryQuery("");

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

    private void ShowDialog() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.category_add_dialog);
        final EditText editText = (EditText) dialog.findViewById(R.id.name_categoryET);
        Button okButton = (Button) dialog.findViewById(R.id.button_OK);
        final Button cancelButton = (Button) dialog.findViewById(R.id.button_Cancel);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable text = editText.getText();
                if (!TextUtils.isEmpty(text)){
                    CategoryEntity categoryEntity = new CategoryEntity();
                    categoryEntity.setName(text.toString());
                    categoryEntity.save();
                    categoryQuery("");
                    dialog.dismiss();
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
