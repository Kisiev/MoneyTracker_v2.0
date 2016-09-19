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

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.UI.adapters.ExpensesAdapter;
import money.android.bignerdranch.com.moneytracker.models.ExpenseModel;

public class ExpensesFragment extends Fragment {

    RecyclerView recyclerView;
    ExpensesAdapter expensesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.expenses_fragment, container, false);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.list_of_expenses);
        expensesAdapter = new ExpensesAdapter(getExpenses());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(expensesAdapter);
        return rootView;
    }

    private List<ExpenseModel> getExpenses()
    {
        List<ExpenseModel> expense = new ArrayList<>();
        expense.add(new ExpenseModel("books", "1"));
        expense.add(new ExpenseModel("books", "2"));
        expense.add(new ExpenseModel("books", "3"));
        expense.add(new ExpenseModel("books", "4"));
        expense.add(new ExpenseModel("books", "5"));
        expense.add(new ExpenseModel("books", "6"));
        return expense;
    }
}
