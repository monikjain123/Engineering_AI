package com.monik.jain.engineeringai.API;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Monik
 * on 11/16/2017 3:16 PM.
 */

public class ServiceGenerator {
    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
    private static Gson gson = new GsonBuilder().serializeNulls().setLenient().create();
    private static Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://hn.algolia.com/api/v1/").addConverterFactory(GsonConverterFactory.create(gson));
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();

    private static Retrofit retrofit = builder.build();

    public static ApiInterface createService() {
        if (!httpClient.interceptors().contains(httpLoggingInterceptor)) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClient.addInterceptor(httpLoggingInterceptor);
        }
        httpClient.connectTimeout(90, TimeUnit.SECONDS);
        httpClient.readTimeout(90, TimeUnit.SECONDS);

        builder.client(httpClient.build());
        retrofit = builder.build();
        return retrofit.create(ApiInterface.class);
    }
}
