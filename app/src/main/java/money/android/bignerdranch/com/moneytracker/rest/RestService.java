package money.android.bignerdranch.com.moneytracker.rest;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import money.android.bignerdranch.com.moneytracker.entitys.CategoryEntity;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserGetDataModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserLoginModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserRegistrationModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserSyncCategoriesModel;
import money.android.bignerdranch.com.moneytracker.rest.Models.UserValidTokenModel;


public final class RestService {
    private final static String REGISTER_FLAG = "1";
    private RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    public UserRegistrationModel register (@NonNull String login,
                                           @NonNull String password) throws IOException {

        return restClient.getLoftSchoolApi()
                .registerUser(login, password, REGISTER_FLAG)
                .execute().body();

    }
    public UserLoginModel login (@NonNull String login,
                                 @NonNull String password) throws IOException{
        return restClient.getLoftSchoolApi()
                .login(login, password)
                .execute().body();
    }

    public UserValidTokenModel validToken (@NonNull String token) throws IOException{
        return restClient.getLoftSchoolApi()
                .userValidToken(token)
                .execute().body();
    }

    public UserGetDataModel getData(@NonNull String token) throws IOException {
        return restClient.getLoftSchoolApi()
                .userData(token)
                .execute()
                .body();
    }

    public UserSyncCategoriesModel userSyncCategoriesModel (@NonNull String id, String title, @NonNull String token) throws IOException {
        return restClient.getLoftSchoolApi()
                .sendCategories(id, title, token)
                .execute().
                body();
    }

}
