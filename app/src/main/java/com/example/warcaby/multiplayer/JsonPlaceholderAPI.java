package com.example.warcaby.multiplayer;

import retrofit2.Call;
import retrofit2.http.*;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;

import java.util.List;

public interface JsonPlaceholderAPI {

    @GET("games/{id}")
    Call<Game> getGame(@Path("id") Integer gameId);
    @GET("games")
    Call<List<Game>> getListOfRooms();



    @POST("games")
    Call<Game> createGame(@Body Game game);
    @POST("players")
    Call<Game> createPlayer(@Body Player player);



    @DELETE("games/{id}")
    Call<Game> deleteGame (@Path("id") Integer gameId);



    @PUT("games/{id}")
    Call<Game> editGame (@Path("id") Integer gameId, @Body Game game);

}
