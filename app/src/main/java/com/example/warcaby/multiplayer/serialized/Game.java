package com.example.warcaby.multiplayer.serialized;

import com.example.warcaby.MainActivity;
import com.google.gson.annotations.SerializedName;

import java.util.Random;

public class Game {

    @SerializedName("board")
    private String board;
    @SerializedName("currentPlayerId")
    private Integer currentPlayerId = null;
    @SerializedName("whitePlayerId")
    private Integer whitePlayerId = null;
    @SerializedName("blackPlayerId")
    private Integer blackPlayerId = null;
    @SerializedName("gameName")
    private String gameName;
    private int id;


    public Game(String gameName) {
        board = MainActivity.cleanBoard;
        this.gameName = gameName;
    }

    // ------------------------ GETTERS ------------------------
    public String getBoard() {
        return board;
    }
    public Integer getCurrentPlayerId() {
        return currentPlayerId;
    }
    public Integer getWhitePlayerId() {
        return whitePlayerId;
    }
    public Integer getBlackPlayerId() {
        return blackPlayerId;
    }
    public Integer getId() {
        return id;
    }
    public String getGameName() {
        return gameName;
    }

    public Integer getPlayersCount() {
        if (whitePlayerId == null && blackPlayerId == null) {
            return 0;
        }
        else if ((whitePlayerId != null && blackPlayerId == null) ||
                (whitePlayerId == null && blackPlayerId != null)) {
            return 1;
        }
        else
            return 2;
    }

    // ------------------------ SETTERS ------------------------
    public void setId(int id) {
        this.id = id;
    }

    public void setWhitePlayerId(Integer whitePlayerId) {
        this.whitePlayerId = whitePlayerId;
        this.currentPlayerId = whitePlayerId;
    }

    public void setBlackPlayerId(Integer blackPlayerId) {
        this.blackPlayerId = blackPlayerId;
    }
}
