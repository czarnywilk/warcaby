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
        public void onDestroy() { // doesn't guarantee to be invoked (should be onPause / onStop)

            // not deleting player when no second player present
            GameManager.quitGame(true);

            super.onDestroy();
        }

        @Override
        public void onTaskRemoved(Intent rootIntent) {

            stopSelf();
        }
}
