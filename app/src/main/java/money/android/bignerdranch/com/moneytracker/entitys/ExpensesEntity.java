package money.android.bignerdranch.com.moneytracker.entitys;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Sqlable;

import java.util.List;


@Table(name = "expenses")
public class ExpensesEntity extends Model{

    @Column(name = "sum")
    public String sum;
    @Column(name = "name")
    public String name;
    @Column(name = "date")
    public String date;
    @Column(name = "category", onUpdate = Column.ForeignKeyAction.CASCADE, onDelete = Column.ForeignKeyAction.CASCADE)
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

    public static List<ExpensesEntity> selectAll(String quary){
        return new Select().from(ExpensesEntity.class)
                .where("name LIKE?", new String[] {"%" + quary + "%"})
                .execute();

    }

    public static void deleteItem(String id){
        new Delete().from(ExpensesEntity.class)
                .where("name = ?", new String[]{"%"+ id +"%"})
                .execute();
    }

    public static  List<ExpensesEntity> selectSortCategory(long category){
        return new Select().from(ExpensesEntity.class)
                .where("category = ?", category)
                .execute();
    }

    public static  List<ExpensesEntity> SelectSortExpensesOnDate(){
        return new Select().from(ExpensesEntity.class)
                .orderBy("date")
                .execute();
    }


}
