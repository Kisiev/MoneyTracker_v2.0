package money.android.bignerdranch.com.moneytracker.rest.Models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ExpensesModel {

    @SerializedName("id")
    private int id;
    @SerializedName("category_id")
    private int categoryId;
    @SerializedName("comment")
    private String comment;
    @SerializedName("sum")
    private double sum;
    @SerializedName("tr_date")
    private String trDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    public String getTrDate() {
        return trDate;
    }

    public void setTrDate(String trDate) {
        this.trDate = trDate;
    }

}