package com.example.warcaby.lobby;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.warcaby.GameManager;
import com.example.warcaby.MultiActivity;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.recyclerview.PlayerAdapter;
import com.example.warcaby.recyclerview.RoomAdapter;
import com.example.warcaby.roomlist.RoomList;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class Lobby extends AppCompatActivity {

    ArrayList<Player> playersList;
    PlayerAdapter playerAdapter;
    private static Handler handler;
    private static Runnable runnable;

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

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        //endregion

        //region refresh
        handler =  new Handler();
        runnable = new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void run() {
                if (PlaceholderUtility.hasInternetAccess())
                    Refresh();
                handler.postDelayed(this, 10000);// 3 sec
            }
        };
        handler.postDelayed(runnable, 0);
        //endregion

        //region start game
        Button startGameBtn = findViewById(R.id.startGameButton);
        startGameBtn.setOnClickListener(v -> {
            if (playersList.size() > 1) {
                try {
                    GameManager.getUserGame().setGameStarted(true);
                    GameManager.updateGame(GameManager.getUserGame());
                    removeRefreshCallbacks();
                    startActivity(new Intent(getBaseContext(), MultiActivity.class));
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println(e.getMessage());
                }
            }
            else {
                Toast.makeText(this, "Not enough players!", Toast.LENGTH_SHORT).show();
            }
        });
        //endregion

        TextView roomName = findViewById(R.id.roomNameLabel);
        roomName.setText(GameManager.getUserGame().getGameName());
    }
    void Refresh(){

        try {
            playersList.clear();

            final boolean[] gameStarted = {false};
            Call<Game> call = PlaceholderUtility.getPlaceholderInstance()
                    .getGame(GameManager.getUserGame().getId());
            try {
                Runnable runnable = () -> {
                    try {
                        gameStarted[0] = call.execute().body().isGameStarted();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                };
                Thread thread = new Thread(runnable);
                thread.start(); // spawn thread
                thread.join(); // wait for thread to finish
            }
            catch (InterruptedException ie) {
                ie.printStackTrace();
            }

            GameManager.getPlayersFromGame(GameManager.getUserGame());
            GameManager.setServerCallbackListener(new GameManager.ServerCallbackListener() {
                @Override
                public void onServerResponse(Object obj) {
                    List<Player> players = (ArrayList)obj;

                    //region set second player
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
                    //endregion

                    if (gameStarted[0]) {
                        removeRefreshCallbacks();
                        startActivity(new Intent(getBaseContext(), MultiActivity.class));
                        finish();
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

    public static void removeRefreshCallbacks() {
        handler.removeCallbacks(runnable);
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
            removeRefreshCallbacks();
            startActivity(new Intent(getApplicationContext(), RoomList.class));
            finish();
        });
    }
}