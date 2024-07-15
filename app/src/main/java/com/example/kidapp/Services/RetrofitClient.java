package com.example.kidapp.Services;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    //private static final String BASE_URL = "http://172.20.10.4:8084";
    //private static final String BASE_URL = "http://10.0.2.2:8084";
    //private static final String BASE_URL = "http://192.168.1.140:8084";//device at roeiHome
    //private static final String BASE_URL = "http://192.168.63.230:8084";  //device at nati
    //private static final String BASE_URL = "http://172.20.24.92:8084";//device at roeiPhone
    private static final String BASE_URL = "http://172.20.10.3:8084";  //device at noaAfeka
    private static RetrofitClient instance;
    private Retrofit retrofit;

    private RetrofitClient() {
        if (instance == null) {
            this.retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
    }

    public static RetrofitClient getInstance() {
        if (instance == null) {
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Retrofit getClient() {
        return this.retrofit;
    }
}

