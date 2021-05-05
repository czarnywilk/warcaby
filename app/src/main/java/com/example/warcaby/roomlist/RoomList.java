package com.example.warcaby.roomlist;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warcaby.GameManager;
import com.example.warcaby.lobby.Lobby;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.recyclerview.RoomAdapter;

import java.util.ArrayList;
import java.util.Random;

public class RoomList extends AppCompatActivity {

    private ArrayList<Game> roomList;
    private RoomAdapter roomAdapter;
    private static Handler handler;
    private static Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        GameManager.setContext(this); // uruchomic raz na activity

        Button createRoom = findViewById(R.id.createRoomButton);
        EditText gameName = findViewById(R.id.roomNameInput);


        //region recycler view setup
        RecyclerView recyclerView = findViewById(R.id.recyclerViewGames);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getBaseContext());
        roomList = new ArrayList<>();
        roomAdapter = new RoomAdapter(roomList);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(roomAdapter);

        DividerItemDecoration itemDecorator = new DividerItemDecoration(getBaseContext(), DividerItemDecoration.VERTICAL);
        itemDecorator.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecorator);
        //endregion

        //region refresh every x seconds
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

        createRoom.setOnClickListener(v -> {
            String Name = gameName.getText().toString();
            if(!Name.isEmpty()){

                Game game = new Game(Name);

                int random = new Random().nextInt(100);
                if(random > 50) {
                    game.setWhitePlayerId(GameManager.getUserPlayer().getId());
                    game.setCurrentPlayerId(GameManager.getUserPlayer().getId());
                }
                else
                    game.setBlackPlayerId(GameManager.getUserPlayer().getId());

                GameManager.createGame(game);
                GameManager.setServerCallbackListener(new GameManager.ServerCallbackListener() {
                    @Override
                    public void onServerResponse(Object obj) {
                        removeRefreshCallbacks();
                        startActivity(new Intent(getBaseContext(), Lobby.class));
                        finish();
                    }

                    @Override
                    public void onServerFailed() {
                        Toast.makeText(GameManager.getContext(),
                                "Connection failed!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void Refresh(){
        try {
            roomList.clear();
            GameManager.getGames();
            GameManager.setServerCallbackListener(new GameManager.ServerCallbackListener() {
                @Override
                public void onServerResponse(Object obj) {
                    if(obj!=null){
                        roomList.addAll((ArrayList)obj);
                        roomAdapter.notifyDataSetChanged();
                    }
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

    public static void removeRefreshCallbacks() {
        handler.removeCallbacks(runnable);
    }
}