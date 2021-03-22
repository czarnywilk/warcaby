package com.example.warcaby.lobby;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.warcaby.GameManager;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.recyclerview.PlayerAdapter;
import com.example.warcaby.recyclerview.RoomAdapter;
import com.example.warcaby.roomlist.RoomList;

import java.util.ArrayList;
import java.util.List;

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
                handler.postDelayed(this, 10000);// 3 sec
            }
        };
        handler.postDelayed(runnable, 0);

        // REMEMBER TO REMOVE CALLBACKS FROM HANDLER (its running on another thread)

    }
    void Refresh(){

        try {
            playersList.clear();
            GameManager.getPlayersFromGame(GameManager.getUserPlayer().getGameId());
            GameManager.setServerCallbackListener(new GameManager.ServerCallbackListener() {
                @Override
                public void onServerResponse(Object obj) {
                    List<Player> players = (ArrayList)obj;

                    if (GameManager.getSecondPlayer() == null) {
                        Game game = GameManager.getUserGame();
                        for (Player p : players) {
                            if (!p.getId().equals(GameManager.getUserPlayer().getId())) {
                                GameManager.setSecondPlayer(p);
                                if (game.getWhitePlayerId() == null) {
                                    game.setWhitePlayerId(p.getId());
                                    game.setCurrentPlayerId(p.getId());
                                }
                                else if (game.getBlackPlayerId() == null) {
                                    game.setBlackPlayerId(p.getId());
                                }
                                break;
                            }
                        }
                    }

                    playersList.addAll(players);
                    playerAdapter.notifyDataSetChanged();
                }

                @Override
                public void onServerFailed() {

                }
            });

        }
        catch (Exception e) {
            Log.e("test", e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        //moveTaskToBack(true);
        View window = findViewById(R.id.quitWindow);

        window.setVisibility(View.VISIBLE);
        window.setClickable(true);

        Button noBtn = findViewById(R.id.quitNo);
        noBtn.setOnClickListener(v -> {
            window.setVisibility(View.INVISIBLE);
            window.setClickable(false);
        });

        Button yesBtn = findViewById(R.id.quitYes);
        yesBtn.setOnClickListener(v -> {
            window.setVisibility(View.INVISIBLE);
            window.setClickable(false);
            GameManager.quitGame(false);

            startActivity(new Intent(getApplicationContext(), RoomList.class));
        });
    }
}