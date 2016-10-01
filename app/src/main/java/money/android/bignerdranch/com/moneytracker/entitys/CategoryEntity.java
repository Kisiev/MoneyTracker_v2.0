package money.android.bignerdranch.com.moneytracker.entitys;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by User on 28.09.2016.
 */

@Table(name = "categories")
public class CategoryEntity extends Model{
    @Column(name = "name")
    public  String name;

    public CategoryEntity(){
        super();
    }

    public CategoryEntity(String name) {
        super();
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ExpensesEntity> expenses(){
        return getMany(ExpensesEntity.class, "category");
    }

    public static List<CategoryEntity> selectAll(){
        return  new Select().from(CategoryEntity.class).execute();
    }

    public static List<CategoryEntity> selectAllInner(){
        ExpensesEntity f = new ExpensesEntity();
        CategoryEntity f1 = new CategoryEntity();
        return  new Select().from(ExpensesEntity.class).execute();
    }

    public static List<CategoryEntity> deleteAll(){
        return new Delete().from(CategoryEntity.class).execute();
    }
}
