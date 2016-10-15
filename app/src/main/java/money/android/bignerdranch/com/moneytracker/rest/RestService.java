package money.android.bignerdranch.com.moneytracker.rest;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

import java.io.EOFException;
import java.io.IOException;

import money.android.bignerdranch.com.moneytracker.rest.registration.UserRegistrationModel;

/**
 * Created by User on 05.10.2016.
 */

public final class RestService {
    private final static String REGISTER_FLAG = "1";
    private RestClient restClient;

    public RestService(){
        restClient = new RestClient();
    }

    public UserRegistrationModel register (@NonNull String login,
                                           @NonNull String password) throws Exception {

        return restClient.getRegisterUserApi()
                .registerUser(login, password, REGISTER_FLAG)
                .execute().body();

    }


}
