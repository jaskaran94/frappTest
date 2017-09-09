package com.example.frapptest.api;

import android.content.Context;

import com.example.frapptest.ConnectionOfflineException;
import com.example.frapptest.utilities.Utils;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
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
            initApiService(context);
        }
        return apiService;
    }

    private static void initApiService(final Context context){
        final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                if (Utils.isConnectionOnline(context)) {
                    int maxAge = 60; // read from cache for 1 minute
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .build();
                } else {
                    int maxStale = 60 * 60 * 24 * 28;
                    return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .build();
                }
            }
        };

        File httpCacheDirectory = new File(context.getCacheDir(), "responses");
        int cacheSize = 3 * 1024 * 1024; // 3 mb
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .cache(cache)
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .build();
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
        apiService = restAdapter.create(ApiService.class);
    }


}
