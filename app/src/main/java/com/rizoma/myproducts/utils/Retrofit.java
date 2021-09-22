package com.rizoma.myproducts.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    //replace it with your computer or server ip address
    public static String baseurl = "http://192.168.1.12:8000/";

    static Gson gson = new GsonBuilder()
            .setLenient()
            .create();
    private static retrofit2.Retrofit retrofit;
    public static retrofit2.Retrofit getRetrofit(){
        if (retrofit==null){
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(baseurl)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;

    }


}
