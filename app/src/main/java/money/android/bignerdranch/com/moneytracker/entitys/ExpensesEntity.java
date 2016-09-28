package money.android.bignerdranch.com.moneytracker.entitys;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.jar.Attributes;

/**
 * Created by User on 28.09.2016.
 */
@Table(name = "expenses")
public class ExpensesEntity extends Model{


    @Column(name = "sum")
    public String sum;
    @Column(name = "name")
    public String name;
    @Column(name = "date")
    public String date;
    @Column(name = "category")
    public CategoryEntity category;

    public ExpensesEntity(){
        super();
    }
    public ExpensesEntity(String sum, String name, String date, CategoryEntity category){
        super();
        this.sum = sum;
        this.name = name;
        this.date = date;
        this.category = category;
    }


    public String getSum() {
        return sum;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public CategoryEntity getCategory() {
        return category;
    }

}
