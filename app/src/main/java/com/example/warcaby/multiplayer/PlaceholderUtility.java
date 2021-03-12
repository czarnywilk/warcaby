package com.example.warcaby.multiplayer;

import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceholderUtility {

    private static final String BaseUrl = "https://mean-bat-45.loca.lt";

    private static boolean instanceCreated = false;
    private static Retrofit retrofitInstance;

    private static JsonPlaceholderAPI placeholderInstance;

    public static void initialize() {
        retrofitInstance = createRetrofitInstance();
        placeholderInstance = retrofitInstance.create(JsonPlaceholderAPI.class);
        instanceCreated = true;
    }

    // --------------- Retrofit --------------------
    private static Retrofit createRetrofitInstance() {
        if (!instanceCreated) {
            return new Retrofit.Builder()
                    .baseUrl(BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create(
                            new GsonBuilder().serializeNulls().create()))
                    .build();
        }
        else return retrofitInstance;
        //JsonPlaceholderAPI placeholder = retrofit.create(JsonPlaceholderAPI.class);
        //Call<Post> call = placeholder.getPost(cityName, MainActivity.AppId);
    }
    public static Retrofit getRetrofitInstance() {
        return retrofitInstance;
    }

    // -------------- Placeholder -------------------
    public static JsonPlaceholderAPI getPlaceholderInstance() {
        return placeholderInstance;
    }
}
