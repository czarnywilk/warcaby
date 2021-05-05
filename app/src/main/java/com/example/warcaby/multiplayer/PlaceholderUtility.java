package com.example.warcaby.multiplayer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.warcaby.GameManager;
import com.google.gson.GsonBuilder;

import java.util.Objects;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlaceholderUtility {

    private static final String BaseUrl = "https://ordinary-fly-78.loca.lt/";

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
        return placeholderInstance;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean hasInternetAccess() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager) GameManager.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() ==
                NetworkInfo.State.CONNECTED ||
                Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() ==
                        NetworkInfo.State.CONNECTED;
    }
}
