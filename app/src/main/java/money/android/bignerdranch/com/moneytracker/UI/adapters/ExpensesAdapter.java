package money.android.bignerdranch.com.moneytracker.UI.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.models.ExpenseModel;

/**
 * Created by User on 18.09.2016.
 */
public class ExpensesAdapter extends RecyclerView.Adapter<ExpensesAdapter.ExpensesHolder>{


    private List<ExpenseModel> expensesList;

    public ExpensesAdapter(List<ExpenseModel> expensesList){
        this.expensesList = expensesList;
    }

    @Override
    public ExpensesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_item, parent, false);
        return new ExpensesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ExpensesHolder holder, int position) {
        ExpenseModel expenseModel = expensesList.get(position);
        Log.d("myf", String.valueOf(expenseModel.getName()));
        holder.name.setText(expenseModel.getName());
        holder.price.setText(expenseModel.getPrice());
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    class ExpensesHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView price;

        public ExpensesHolder(View itemView) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_expense_item);
            price = (TextView) itemView.findViewById(R.id.price_expense_item);
        }
    }
}
