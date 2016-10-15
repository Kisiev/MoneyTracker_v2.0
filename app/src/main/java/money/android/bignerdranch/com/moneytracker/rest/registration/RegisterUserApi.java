package money.android.bignerdranch.com.moneytracker.rest.registration;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;



public interface RegisterUserApi {
    @GET("/auth")
    Call<UserRegistrationModel> registerUser (@Query("login")String login,
                                              @Query("password") String password,
                                              @Query("register") String registerFlag);
}
