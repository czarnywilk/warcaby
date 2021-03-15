package com.example.warcaby.lobby;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.warcaby.GameManager;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.recyclerview.PlayerAdapter;
import com.example.warcaby.recyclerview.RoomAdapter;

import java.util.ArrayList;

import static com.example.warcaby.GameManager.hasInternetAccess;

public class Lobby extends AppCompatActivity {


    ArrayList<Player> playersList;
    PlayerAdapter playerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        GameManager.setContext(this);

        //region recycler view setup
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlayers);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getBaseContext());
        playersList = new ArrayList<>();
        playerAdapter = new PlayerAdapter(playersList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(playerAdapter);
        //endregion

        //refresh every x seconds
        Handler handler =  new Handler();
        Runnable runnable = new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                if (GameManager.hasInternetAccess())
                    Refresh();
                handler.postDelayed(this, 3000);// 3 sec
            }
        };
        handler.postDelayed(runnable, 3000);

    }
    void Refresh(){

        try {
            playersList.clear();
            GameManager.getPlayersFromGame(GameManager.getUserPlayer().getGameId());
            GameManager.setServerCallbackListener(new GameManager.ServerCallbackListener() {
                @Override
                public void onServerResponse(Object obj) {
                    playersList.addAll((ArrayList)obj);
                    playerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onServerFailed() {

                }
            });

        }
        catch (Exception e) {
            Log.d("test", e.getMessage());
        }
    }
}