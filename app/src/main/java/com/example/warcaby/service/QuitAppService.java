package com.example.warcaby.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.warcaby.GameManager;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;

import java.util.ArrayList;
import java.util.List;

public class QuitAppService extends IntentService {

    public QuitAppService() {
        super(null);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        System.out.println("onHandleIntent : " + intent);
        if (intent == null) {
            System.out.println("onTaskRemoved OTHER THREAD CALLED");
            GameManager.quitGame(true);
            System.out.println("onTaskRemoved CALL ENDED");
        }
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }
    @Override
    public void onDestroy() {
        System.out.println("TASK REMOVED");
        super.onDestroy();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {

        System.out.println("onTaskRemoved CALLED");

        Runnable runnable = () -> {
            System.out.println("onTaskRemoved OTHER THREAD CALLED");
            GameManager.quitGame(true);
            System.out.println("onTaskRemoved CALL ENDED");
            stopSelf();
        };
        Thread quitThread = new Thread(runnable);
        quitThread.start();

        //stopSelf();
    }
}
