package com.example.warcaby;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;

import com.example.warcaby.lobby.Lobby;
import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.roomlist.RoomList;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GameManager {

    // -------------------- SERVER LISTENER ----------------
    public interface ServerCallbackListener {
        void onServerResponse(Object obj);
        void onServerFailed();
    }
    public static ServerCallbackListener listener;
    public static void setServerCallbackListener(ServerCallbackListener listener){
        GameManager.listener = listener;
    }
    // -----------------------------------------------------

    private static Player userPlayer;
    private static Context mContext; // memory leak :(
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

    // -------------------- JOIN -------------------------
    public static void joinGame (Game game) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().
                editGame(game.getId(), game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Toast.makeText(mContext,"Joined Game!", Toast.LENGTH_SHORT).show();
                mContext.startActivity(new Intent(mContext, Lobby.class));
                //listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                //listener.onServerFailed();
            }
        });
    }

    // ------------------- CREATE ------------------------
    public static void createGame (Game game) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().createGame(game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                Toast.makeText(mContext,"Game created!", Toast.LENGTH_SHORT).show();

                Game gameResponse = response.body();
                assert gameResponse != null;

                game.setId(gameResponse.getId());
                game.setWhitePlayerId(gameResponse.getWhitePlayerId());
                game.setBlackPlayerId(gameResponse.getBlackPlayerId());

                listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                listener.onServerFailed();
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
                Toast.makeText(mContext,"Player created!", Toast.LENGTH_SHORT).show();
                player.setId(response.body().getId());
                listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                listener.onServerFailed();
            }
        });
    }

    // ------------------- GETTERS -----------------------
    public static void getGame (Integer gameId) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().getGame(gameId);
        //final Game[] game = {null};

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext,"Connection unsuccessful!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //game[0] = response.body();
                listener.onServerResponse(response.body());
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Toast.makeText(mContext,"Connection failed!", Toast.LENGTH_SHORT).show();
                listener.onServerFailed();
            }
        });
    }
    public static void getGames () {
        Call<List<Game>> call = PlaceholderUtility.getPlaceholderInstance().getListOfRooms();
        //final ArrayList<Game> games = new ArrayList<>();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(mContext,"Connection unsuccessful!", Toast.LENGTH_SHORT).show();
                    return;
                }

                //List<Game> gamesList = response.body();
                //games.addAll(gamesList);
                listener.onServerResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                Toast.makeText(mContext,"Connection failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public static Player getUserPlayer() {
        return userPlayer;
    }
    public static Context getContext() {
        return mContext;
    }

    // ------------------- SETTERS -----------------------
    public static void setUserPlayer(Player userPlayer) {
        GameManager.userPlayer = userPlayer;
    }
}
