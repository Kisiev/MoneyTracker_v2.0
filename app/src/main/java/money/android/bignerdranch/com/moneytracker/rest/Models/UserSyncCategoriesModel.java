package money.android.bignerdranch.com.moneytracker.rest.Models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserSyncCategoriesModel {

    @SerializedName("status")
    private String status;
    @SerializedName("data")
    private List<Datum> data = new ArrayList<Datum>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

}

 class Datum {

    @SerializedName("id")
    private int id;
    @SerializedName("title")
    private String title;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}