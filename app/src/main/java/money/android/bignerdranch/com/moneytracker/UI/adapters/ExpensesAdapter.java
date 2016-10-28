package money.android.bignerdranch.com.moneytracker.UI.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;


public class ExpensesAdapter extends SelectableAdapter<ExpensesAdapter.ExpensesHolder> {


    private List<ExpensesEntity> expensesList;
    private ClickListener clickListener;

    public ExpensesAdapter(List<ExpensesEntity> expensesList, ClickListener clickListener) {
        this.expensesList = expensesList;
        this.clickListener = clickListener;
    }

    @Override
    public ExpensesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expenses_item, parent, false);
        return new ExpensesHolder(itemView, clickListener);
    }

    @Override
    public void onBindViewHolder(ExpensesHolder holder, int position) {
        ExpensesEntity expenseModel = expensesList.get(position);
        holder.name.setText(expenseModel.getName());
        holder.price.setText(expenseModel.getSum());
        holder.selectedItem.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    class ExpensesHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ClickListener clickListener;
        TextView name;
        TextView price;
        View selectedItem;

        public ExpensesHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            name = (TextView) itemView.findViewById(R.id.name_expense_item);
            price = (TextView) itemView.findViewById(R.id.price_expense_item);
            selectedItem = itemView.findViewById(R.id.selected_overlay);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(clickListener != null) clickListener.onItemSelected(getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View view) {
            return clickListener != null && clickListener.onItemLongSelected(getAdapterPosition());
        }
    }


    public void removeItems(List<Integer> positions) {
        Collections.sort(positions, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return rhs - lhs;
            }
        });
        while (!positions.isEmpty()) {
            if (positions.size() == 1) {
                removeItem(positions.get(0));
                positions.remove(0);
            } else {
                for (int i = 0; i < positions.size();i ++){
                    removeItem(positions.get(0));
                    positions.remove(0);
                }

               /* int count = 1;
                while (positions.size() > count) {
                    count++;
                }
                removeRange(count - 1, count);
                for (int i = 0; i < count; i++) {
                    positions.remove(0);
                }*/
            }
        }
    }


    private void removeItem(int position) {
        removeExpenses(position);
        notifyItemRemoved(position);
    }

    private void removeExpenses(int position) {
        if (expensesList.get(position) != null) {
            expensesList.get(position).delete();
            expensesList.remove(position);
        }
    }
}
