package money.android.bignerdranch.com.moneytracker.models;

/**
 * Created by User on 19.09.2016.
 */
public class CategoryModel {
    private String categoryName;

    public CategoryModel (String categoryName)
    {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }
}
