package com.example.warcaby.multiplayer;

import android.util.Log;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceholderUtility {

    private static final String BaseUrl = "https://dangerous-jellyfish-82.loca.lt";

    private static boolean initialized = false;
    private static Retrofit retrofitInstance;

    private static JsonPlaceholderAPI placeholderInstance;

    public static void initialize() {
        if (initialized) return;

        retrofitInstance = createRetrofitInstance();
        placeholderInstance = retrofitInstance.create(JsonPlaceholderAPI.class);
        initialized = true;
    }

    // --------------- Retrofit --------------------
    private static Retrofit createRetrofitInstance() {
        if (!initialized) {
            return new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().serializeNulls().create()))
                    .build();
        }
        else return retrofitInstance;
    }
    public static Retrofit getRetrofitInstance() {
        return retrofitInstance;
    }

    // -------------- Placeholder -------------------
    public static JsonPlaceholderAPI getPlaceholderInstance() {
        if (placeholderInstance == null)System.out.println("PLACEHOLDER NULL");
        return placeholderInstance;
    }
}
