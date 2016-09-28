package money.android.bignerdranch.com.moneytracker.entitys;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

import java.util.List;

/**
 * Created by User on 28.09.2016.
 */

@Table(name = "categories")
public class CategoryEntity extends Model{
    @Column(name = "name")
    public String name;

    public CategoryEntity(){
        super();
    }
    public CategoryEntity(String name){
        super();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<ExpensesEntity> expenses(){
        return getMany(ExpensesEntity.class, "category");
    }
}
