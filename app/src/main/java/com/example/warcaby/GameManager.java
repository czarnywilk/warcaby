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

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameManager {

    public interface ServerCallbackListener {
        public void onServerResponse();
        public void onServerFailed();
    }

    public static ServerCallbackListener listener;
    public static void setServerCallbackListerer(ServerCallbackListener listener){
        GameManager.listener = listener;
    }

    private static Player userPlayer;
    private static Context mContext;
    public static void setContext(Context context) {
        mContext = context;
        listener = null;
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
// ------------------CREATE-----------------------
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
                listener.onServerResponse();
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                listener.onServerFailed();
            }
        });
    }

    // -------------------- GETTERS --------------------------
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

    public static Player getUserPlayer() {
        return userPlayer;
    }

    public static Context getmContext() {
        return mContext;
    }
    // -------------------SETTERS----------------------

    public static void setUserPlayer(Player userPlayer) {
        GameManager.userPlayer = userPlayer;
    }
}
