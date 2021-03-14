package com.example.warcaby.lobby;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.warcaby.GameManager;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.recyclerview.PlayerAdapter;
import com.example.warcaby.recyclerview.RoomAdapter;

import java.util.ArrayList;

public class Lobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        GameManager.setContext(this);

        //region recycler view setup
        RecyclerView recyclerView = findViewById(R.id.recyclerViewPlayers);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getBaseContext());
        ArrayList<Player> playersList = new ArrayList<>();
        PlayerAdapter playerAdapter = new PlayerAdapter(playersList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(playerAdapter);
        //endregion

        //refresh every x seconds
        try {
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