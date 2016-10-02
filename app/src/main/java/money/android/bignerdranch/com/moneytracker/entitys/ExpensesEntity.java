package money.android.bignerdranch.com.moneytracker.entitys;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;
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

    public String getSum() {
        return sum;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }
    public CategoryEntity getCategory() {
        return category;
    }

    public static List<ExpensesEntity> selectAll(){
        return new Select().from(ExpensesEntity.class).execute();

    }


    public static List<ExpensesEntity> deleteAll(){
        return new Delete().from(ExpensesEntity.class).execute();
    }

}
