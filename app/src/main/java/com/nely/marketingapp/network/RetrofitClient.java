package com.nely.marketingapp.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class RetrofitClient {

    private static final String BASE_URL = "http://192.168.100.228:8000/";

    private static Retrofit retrofit;

    public static ApiService getApiService() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(
                            GsonConverterFactory.create()
                    )
                    .build();
        }

        return retrofit.create(ApiService.class);
    }
}
