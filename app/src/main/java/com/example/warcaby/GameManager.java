package com.example.warcaby;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.warcaby.lobby.Lobby;
import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.roomlist.RoomList;

import java.util.List;

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
    private static Player secondPlayer;
    private static Game userGame;

    private static Context mContext; // memory leak :(
    public static void setContext(Context context) {
        mContext = context;
        listener = new ServerCallbackListener() {
            @Override
            public void onServerResponse(Object obj) { }

            @Override
            public void onServerFailed() { }
        };
    }

    // ------------------- GETTERS -----------------------
    public static void getGame (Game game) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().getGame(game.getId());

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    listener.onServerFailed();
                    return;
                }

                listener.onServerResponse(response.body());
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                Toast.makeText(mContext,"Connection failed!", Toast.LENGTH_SHORT).show();
                listener.onServerFailed();
            }
        });
    }
    public static Game getGame_sync (Integer gameId) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().getGame(gameId);

        final Game[] _game = new Game[1];

        try {
            Runnable runnable = () -> {
                try {
                    Response<Game> response = call.execute();

                    _game[0] = response.body();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start(); // spawn thread
            thread.join(); // wait for thread to finish
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        return _game[0];
    }
    public static void getGames () {
        Call<List<Game>> call = PlaceholderUtility.getPlaceholderInstance().getListOfRooms();

        call.enqueue(new Callback<List<Game>>() {
            @Override
            public void onResponse(Call<List<Game>> call, Response<List<Game>> response) {
                if (!response.isSuccessful()) {
                    listener.onServerFailed();
                    return;
                }

                //List<Game> gamesList = response.body();
                //games.addAll(gamesList);
                listener.onServerResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Game>> call, Throwable t) {
                listener.onServerFailed();
            }
        });
    }
    public static void getPlayersFromGame (Game game) {
        Call<List<Player>> call = PlaceholderUtility.getPlaceholderInstance().getPlayersFromGame(game.getId());
        call.enqueue(new Callback<List<Player>>() {
            @Override
            public void onResponse(Call<List<Player>> call, Response<List<Player>> response) {
                if (!response.isSuccessful()) {
                    listener.onServerFailed();
                    return;
                }
                listener.onServerResponse(response.body());
            }

            @Override
            public void onFailure(Call<List<Player>> call, Throwable t) {
                listener.onServerFailed();
            }
        });
    }

    public static Player getUserPlayer() {
        return userPlayer;
    }
    public static Player getSecondPlayer() {
        return secondPlayer;
    }
    public static Game getUserGame() {
        return userGame;
    }
    public static Context getContext() {
        return mContext;
    }

    // ------------------- SETTERS -----------------------
    public static void setUserPlayer(Player userPlayer) {
        GameManager.userPlayer = userPlayer;
    }
    public static void setUserGame(Game userGame) {
        GameManager.userGame = userGame;
    }
    public static void setSecondPlayer(Player secondPlayer) {
        GameManager.secondPlayer = secondPlayer;
    }

    // -------------------- EDIT -------------------------
    public static void updateGame (Game game) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().
                editGame(game.getId(), game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                listener.onServerFailed();
            }
        });
    }
    public static Game updateGame_sync (Game game) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().
                editGame(game.getId(), game);

        final Game[] games = {null};

        try {
            Runnable runnable = () -> {
                try {
                    Response<Game> response = call.execute();
                    games[0] = response.body();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start(); // spawn thread
            thread.join(); // wait for thread to finish
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }

        return games[0];
    }
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

                Game gameResponse = response.body();
                assert gameResponse != null;

                setUserGame(gameResponse);
                userPlayer.setGameId(gameResponse.getId());
                updatePlayer_sync(userPlayer);

                RoomList.removeRefreshCallbacks();
                mContext.startActivity(new Intent(mContext, Lobby.class));

                //listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                //listener.onServerFailed();
            }
        });
    }
    public static void updatePlayer (Player updatedPlayer, boolean setResponse) {
        Call<Player> call = PlaceholderUtility.getPlaceholderInstance().
                editPlayer(updatedPlayer.getId(), updatedPlayer);

        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {
                if (!response.isSuccessful()) {
                    return;
                }
                if (setResponse)
                    listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                //listener.onServerFailed();
            }
        });
    }
    public static void updatePlayer_sync (Player updatedPlayer) {
        Call<Player> call = PlaceholderUtility.getPlaceholderInstance().
                editPlayer(updatedPlayer.getId(), updatedPlayer);

        try {
            Runnable runnable = () -> {
                try {
                    call.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start(); // spawn thread
            thread.join(); // wait for thread to finish
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    // ------------------- CREATE ------------------------
    public static void createGame (Game game) {
        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().createGame(game);

        call.enqueue(new Callback<Game>() {
            @Override
            public void onResponse(Call<Game> call, Response<Game> response) {
                if (!response.isSuccessful()) {
                    listener.onServerFailed();
                    return;
                }
                Toast.makeText(mContext,"Game created!", Toast.LENGTH_SHORT).show();

                Game gameResponse = response.body();
                assert gameResponse != null;

                game.setId(gameResponse.getId());
                game.setWhitePlayerId(gameResponse.getWhitePlayerId());
                game.setBlackPlayerId(gameResponse.getBlackPlayerId());

                GameManager.setUserGame(gameResponse);
                GameManager.userPlayer.setGameId(gameResponse.getId());
                GameManager.updatePlayer(userPlayer, false);

                listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Game> call, Throwable t) {
                listener.onServerFailed();
            }
        });
    }
    public static void createPlayer(Player player) {
        Call<Player> call = PlaceholderUtility.getPlaceholderInstance().createPlayer(player);
        call.enqueue(new Callback<Player>() {
            @Override
            public void onResponse(Call<Player> call, Response<Player> response) {

                if (!response.isSuccessful()) {
                    listener.onServerFailed();
                    return;
                }
                Toast.makeText(mContext,"Player created!", Toast.LENGTH_SHORT).show();
                player.setId(response.body().getId());
                listener.onServerResponse(null);
            }

            @Override
            public void onFailure(Call<Player> call, Throwable t) {
                listener.onServerFailed();
            }
        });
    }

    //-------------------- DELETE ------------------------
    public static void deletePlayer_sync (Integer deletePlayerId) {

        Call<Player> call = PlaceholderUtility.getPlaceholderInstance().
                deletePlayer(deletePlayerId);

        try {
            Runnable runnable = () -> {
                try {
                    call.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start(); // spawn thread
            thread.join(); // wait for thread to finish
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }
    public static void deleteGame_sync (Integer gameId) {

        Call<Game> call = PlaceholderUtility.getPlaceholderInstance().deleteGame(gameId);

        try {
            Runnable runnable = () -> {
                try {
                    call.execute();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            };
            Thread thread = new Thread(runnable);
            thread.start(); // spawn thread
            thread.join(); // wait for thread to finish
        }
        catch (InterruptedException ie) {
            ie.printStackTrace();
        }
    }

    // -------------------- QUIT -------------------------
    public static void quitGame(boolean deletePlayerOnQuit) {
        try {
            Game game = getGame_sync(getUserGame().getId());
            Integer playerId = getUserPlayer().getId();

            if (game != null) {
                if (getSecondPlayer() == null) {
                    if (deletePlayerOnQuit)
                        deletePlayer_sync(playerId);
                    deleteGame_sync(game.getId());
                } else {
                    if (deletePlayerOnQuit)
                        deletePlayer_sync(playerId);
                    else {
                        getUserPlayer().setGameId(null);
                        updatePlayer_sync(getUserPlayer());
                    }

                    if (game.getWhitePlayerId() != null &&
                            game.getWhitePlayerId().equals(playerId)) {
                        game.setWhitePlayerId(null);
                    } else if (game.getBlackPlayerId() != null &&
                            game.getBlackPlayerId().equals(playerId)) {
                        game.setBlackPlayerId(null);
                    }

                    if (game.getCurrentPlayerId() != null &&
                            game.getCurrentPlayerId().equals(playerId)) {
                        game.setCurrentPlayerId(null);
                    }

                    game.setGameStarted(false);
                    setSecondPlayer(null);
                    game.setBoard(MultiActivity.cleanBoard);
                    updateGame_sync(game);
                }
            } else if (deletePlayerOnQuit) {
                deletePlayer_sync(playerId);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

