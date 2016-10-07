package money.android.bignerdranch.com.moneytracker.rest;


import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {

    public static final String BASE_NAME = "http://lmt.loftblog.tmweb.ru/";

    private LoftSchoolApi loftSchoolApi;

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

        loftSchoolApi = retrofit.create(LoftSchoolApi.class);
    }

    public LoftSchoolApi getLoftSchoolApi() {
        return loftSchoolApi;
    }
}
