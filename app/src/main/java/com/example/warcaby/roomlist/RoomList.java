package com.example.warcaby.roomlist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warcaby.GameManager;
import com.example.warcaby.Lobby.Lobby;
import com.example.warcaby.MainMenu;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;

import java.util.Random;

public class RoomList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_list);

        GameManager.setContext(this); // uruchomic raz na activity


        Button createRoom = findViewById(R.id.createRoomButton);
        EditText gameName = findViewById(R.id.roomNameInput);

        createRoom.setOnClickListener(v -> {
            String Name = gameName.getText().toString();
            if(!Name.isEmpty()){

               // GameManager.setUserPlayer(new Player(Name,null));
                Game game = new Game(Name);
                int random = new Random().nextInt(100);
                if(random>50){
                    game.setWhiteAndBlackPlayerId(GameManager.getUserPlayer().getId(),null);
                }
                else game.setWhiteAndBlackPlayerId(null, GameManager.getUserPlayer().getId());
                GameManager.createGame(game);
                GameManager.setServerCallbackListerer(new GameManager.ServerCallbackListener() {
                    @Override
                    public void onServerResponse() {
                        startActivity(new Intent(RoomList.this, Lobby.class));
                    }

                    @Override
                    public void onServerFailed() {
                        Toast.makeText(GameManager.getmContext(),"Brak odpowiedzi z servera!", Toast.LENGTH_SHORT).show();
                    }
                });
                //startActivity(new Intent(MainMenu.this, RoomList.class));
            }


        });
    }
}