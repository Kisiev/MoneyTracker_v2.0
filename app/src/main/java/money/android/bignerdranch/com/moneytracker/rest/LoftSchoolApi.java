package money.android.bignerdranch.com.moneytracker.rest;

import java.util.List;

import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserGetDataModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserLoginModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserRegistrationModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserSyncCategoriesModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserValidTokenModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface LoftSchoolApi {
    @GET("/auth")
    Call<UserRegistrationModel> registerUser (@Query("login")String login,
                                              @Query("password") String password,
                                              @Query("register") String registerFlag);

    @GET("/auth")
        Call<UserLoginModel> login (@Query("login") String login,
                                    @Query("password") String password);

    @GET("/gcheck")
    Call<UserValidTokenModel> userValidToken (@Query("google_token") String googleToken);

    @GET("/gjson")
    Call<UserGetDataModel> userData (@Query("google_token") String googleToken);

    @GET("/categories/synch")
    Call<UserSyncCategoriesModel> sendCategories (@Query("data[]") String id, String name,
                                                    @Query("token") String token);

}
