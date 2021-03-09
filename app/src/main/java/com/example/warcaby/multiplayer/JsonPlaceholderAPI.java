package com.example.warcaby.multiplayer;

import retrofit2.Call;
import retrofit2.http.*;
import com.example.warcaby.multiplayer.serialized.Game;

public interface JsonPlaceholderAPI {

    @GET("games/{id}")
    Call<Game> getGame(@Path("id") String gameId);
    @GET("games/{id}")
    Call<Game> getLastMove(@Path("id") String gameId);



    @POST("games")
    Call<Game> createGame(@Body Game game);



    @DELETE("games/{id}")
    Call<Game> deleteMessage (@Path("id") String gameId);



    @PUT("games/{id}")
    Call<Game> editMessage (@Path("id") String gameId, @Body Game game);


}
