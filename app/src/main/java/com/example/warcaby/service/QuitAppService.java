package com.example.warcaby.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.warcaby.GameManager;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;

import java.util.ArrayList;
import java.util.List;

public class QuitAppService extends Service {

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            return START_NOT_STICKY;
        }

        @Override
        public void onDestroy() {

            Game game = GameManager.getUserGame();
            Integer playerId = GameManager.getUserPlayer().getId();

            System.out.println("game name: " + game.getGameName());
            if (game != null) {
                System.out.println("GAME ID: " + game.getId());
                GameManager.deleteGame(game.getId()); // <--- this mf line is deleting game AND players (WHY?!?!)
                System.out.println("2");

                //Player secondPlayer = GameManager.getSecondPlayer();
                //System.out.println("second player: " + secondPlayer.getPlayerName());
                //secondPlayer.setGameId(null);
                //GameManager.updatePlayer(secondPlayer);

                System.out.println("3");
                //GameManager.deletePlayer(playerId);
                System.out.println("4");
            }

            super.onDestroy();
        }

        @Override
        public void onTaskRemoved(Intent rootIntent) {



            stopSelf();
        }
}
