package money.android.bignerdranch.com.moneytracker.rest;

import money.android.bignerdranch.com.moneytracker.rest.Models.UserLoginModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserRegistrationModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface LoftSchoolApi {
    @GET("/auth")
    Call<UserRegistrationModel> registerUser (@Query("login")String login,
                                              @Query("password") String password,
                                              @Query("register") String registerFlag);

    @GET("/auth")
        Call<UserLoginModel> login (@Query("login") String login,
                                    @Query("password") String password);
}
