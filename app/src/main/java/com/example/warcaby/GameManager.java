package com.example.warcaby;

import androidx.annotation.RequiresApi;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import javax.xml.XMLConstants;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameManager {

    private static Context mContext;
    public static void setContext(Context context) {
        mContext = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean hasInternetAccess() {

        ConnectivityManager connectivityManager =
                (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        return Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)).getState() ==
                NetworkInfo.State.CONNECTED ||
        Objects.requireNonNull(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)).getState() ==
                NetworkInfo.State.CONNECTED;
    }

    public static void createGame (Game game) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().createGame(game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Toast.makeText(mContext,"Gra została stworzona!", Toast.LENGTH_SHORT).show();

                Game gameResponse = response.body();
                assert gameResponse != null;

                game.setId(gameResponse.getId());
                game.setWhiteAndBlackPlayerId(gameResponse.getWhitePlayerId(),
                                              gameResponse.getBlackPlayerId());
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
            }
        });
    }
    public static void getGame (String gameId) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().getGame(gameId);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    return;
                }


            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
            }
        });
    }

    public static void createPlayer(Player player) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().createPlayer(player);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Toast.makeText(mContext,"Gracz został stworzony!", Toast.LENGTH_SHORT).show();
                player.setId(response.body().getId());
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
            }
        });
    }
}
