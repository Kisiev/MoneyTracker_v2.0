package money.android.bignerdranch.com.moneytracker.rest.Models;

import com.google.gson.annotations.SerializedName;


public class CategoryModel {
        @SerializedName("id")
        private String id;
        @SerializedName("title")
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
}
