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
    private int id;

    /*public Game() {
        // board = Board.getEmptyBoard();
        // zawsze białe na dole
    }*/
    public Game(String board) {
        this.board = board;
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

        int random = new Random().nextInt(100);
        this.currentPlayerId = (random > 50) ? whitePlayerId : blackPlayerId;
    }
    // ------------------------ METHODS ------------------------
    public void startGame() {

    }
    public void endGame() {

    }
}
