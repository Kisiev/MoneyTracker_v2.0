package money.android.bignerdranch.com.moneytracker.models;

/**
 * Created by User on 18.09.2016.
 */
public class ExpenseModel {
    private String name;
    private String price;

    public ExpenseModel (String name, String price){
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
