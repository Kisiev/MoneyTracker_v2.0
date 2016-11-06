package money.android.bignerdranch.com.moneytracker.entitys;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

import java.util.List;

import money.android.bignerdranch.com.moneytracker.services.ServiceSample;

/**
 * Created by User on 06.11.2016.
 */



@Table(name = "sample_service")
public class SampleServiceEntity extends Model {

    @Column(name = "sample")
    public ServiceSample serviceSample;

    public ServiceSample getServiceSample() {
        return serviceSample;
    }

    public void setServiceSample(ServiceSample serviceSample) {
        this.serviceSample = serviceSample;
    }

    public SampleServiceEntity(){
        super();
    }

    public static List<ServiceSample> selectSampleSevice (){
        return new Select().from(SampleServiceEntity.class)
                .executeSingle();
    }

    public static void Delete(){
        new Delete().from(SampleServiceEntity.class).execute();
    }

}
