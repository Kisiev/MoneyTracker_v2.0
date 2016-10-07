package money.android.bignerdranch.com.moneytracker.rest;


import money.android.bignerdranch.com.moneytracker.rest.Models.RegisterUserApi;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String BASE_NAME = "http://lmt.loftblog.tmweb.ru/";

    private LoftScoolApi loftScoolApi;

    public RestClient (){


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addNetworkInterceptor(logging)
                .build();


        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_NAME)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

        loftScoolApi = retrofit.create(LoftScoolApi.class);
    }

    public LoftScoolApi getLoftScoolApi() {
        return loftScoolApi;
    }
}
