package money.android.bignerdranch.com.moneytracker.UI.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

import money.android.bignerdranch.com.moneytracker.R;
import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.models.CategoryModel;
import money.android.bignerdranch.com.moneytracker.models.ExpenseModel;

/**
 * Created by User on 19.09.2016.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>{

    private List<CategoryEntity> categoryList;

    public CategoryAdapter (List<CategoryEntity> categoryList){
        this.categoryList = categoryList;
    }

    @Override
    public CategoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_item, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(CategoryHolder holder, int position) {
        CategoryEntity categoryModel = categoryList.get(position);
        holder.categoryName.setText(categoryModel.getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    class CategoryHolder extends RecyclerView.ViewHolder{

        TextView categoryName;
        public CategoryHolder(View itemView) {
            super(itemView);
            categoryName = (TextView)itemView.findViewById(R.id.name_category_item);
        }
    }
}
