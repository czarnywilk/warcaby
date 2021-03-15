package com.example.warcaby.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.example.warcaby.GameManager;

public class QuitAppService extends Service {

        @Override
        public IBinder onBind(Intent intent) {
            return null;
        }

        @Override
        public int onStartCommand(Intent intent, int flags, int startId) {
            Log.d("test", "Service Started");
            return START_NOT_STICKY;
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d("test", "Service Destroyed");
        }

        @Override
        public void onTaskRemoved(Intent rootIntent) {
            Log.e("test", "END");
            if(GameManager.getUserPlayer().getGameId()!=null)
                GameManager.deleteGame(GameManager.getUserPlayer().getGameId());
            GameManager.deletePlayer(GameManager.getUserPlayer().getId());
            stopSelf();
        }
}
