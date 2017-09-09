package com.example.frapptest.api;

import android.content.Context;

import com.example.frapptest.ConnectionOfflineException;
import com.example.frapptest.utilities.Utils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jaskaranhome on 09/09/17.
 */

public class ApiAdapter {
    private static volatile ApiService apiService;
    public static final String BASE_URL = "http://54.254.198.83:8080";

    private ApiAdapter(){

    }

    public static synchronized ApiService getApiService(Context context) throws ConnectionOfflineException {
        if (!Utils.isConnectionOnline(context)){
            throw new ConnectionOfflineException();
        }
        if (apiService == null){
            initApiService();
        }
        return apiService;
    }

    private static void initApiService(){
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiService = restAdapter.create(ApiService.class);
    }
}
