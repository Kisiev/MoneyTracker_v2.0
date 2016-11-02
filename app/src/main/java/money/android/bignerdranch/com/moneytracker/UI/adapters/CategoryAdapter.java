package money.android.bignerdranch.com.moneytracker.UI.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.entitys.ExpensesEntity;


public class CategoryAdapter extends SelectableAdapter<CategoryAdapter.CategoryHolder>{
    private Context context;

    private List<CategoryEntity> categoryList;
    private ClickListener clickListener;
    public CategoryAdapter (List<CategoryEntity> categoryList, ClickListener clickListener, Context context){
        this.categoryList = categoryList;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        CategoryEntity categoryModel = categoryList.get(position);
        holder.categoryName.setText(categoryModel.getName());
        holder.selectedItem.setVisibility(isSelected(position) ? View.VISIBLE : View.INVISIBLE);
    }



    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        private ClickListener clickListener;
        TextView categoryName;
        View selectedItem;
        CardView cardView;
        public CategoryHolder(View itemView, ClickListener clickListener) {
            super(itemView);
            categoryName = (TextView)itemView.findViewById(R.id.name_category_item);
            selectedItem = itemView.findViewById(R.id.selected_overlay);
            cardView = (CardView) itemView.findViewById(R.id.category_card_root);
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

        removeCategory(position);
        notifyItemRemoved(position);
    }

    private void removeCategory(int position) {
        if (categoryList.get(position) != null) {
                 categoryList.get(position).delete();
                 categoryList.remove(position);
        }
    }
}
