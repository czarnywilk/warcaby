package com.example.warcaby.multiplayer.serialized;

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

    /*public Game() {
        // board = Board.getEmptyBoard();
        // zawsze białe na dole
    }*/
    public Game(String gameName) {
        // board = Board.getEmptyBoard();
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

    // ------------------------- SETTERS -----------------------
    public void setId(int id) {
        this.id = id;
    }
    public void setWhiteAndBlackPlayerId(Integer whitePlayerId, Integer blackPlayerId) {
        this.whitePlayerId = whitePlayerId;
        this.blackPlayerId = blackPlayerId;
        this.currentPlayerId = whitePlayerId;
    }
    // ------------------------ METHODS ------------------------
    public void startGame() {

    }
    public void endGame() {

    }
}
