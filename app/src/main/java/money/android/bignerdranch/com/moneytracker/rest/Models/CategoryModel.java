package money.android.bignerdranch.com.moneytracker.rest.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by User on 26.10.2016.
 */

public class CategoryModel {
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
