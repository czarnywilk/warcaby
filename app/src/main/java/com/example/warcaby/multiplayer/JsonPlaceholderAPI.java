package com.example.warcaby.multiplayer;

import retrofit2.Call;
import retrofit2.http.*;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;

public interface JsonPlaceholderAPI {

    @GET("games/{id}")
    Call<Game> getGame(@Path("id") String gameId);



    @POST("games")
    Call<Game> createGame(@Body Game game);
    @POST("players")
    Call<Game> createPlayer(@Body Player player);



    @DELETE("games/{id}")
    Call<Game> deleteGame (@Path("id") String gameId);



    @PUT("games/{id}")
    Call<Game> editGame (@Path("id") String gameId, @Body Game game);


}
