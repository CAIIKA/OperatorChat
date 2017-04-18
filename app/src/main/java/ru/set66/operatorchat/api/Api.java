package ru.set66.operatorchat.api;

import android.app.Application;


import retrofit2.Retrofit;

/**
 * Created by Alex on 16.04.2017.
 */

public class Api extends Application {
    private static InterfaceApi response;
    private Retrofit retrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        retrofit = new Retrofit.Builder()
                .baseUrl("http://79.172.35.24:7777/")

                .build();
        response = retrofit.create(InterfaceApi.class);
    }

    public static InterfaceApi getApi() {
        return response;
    }
}
