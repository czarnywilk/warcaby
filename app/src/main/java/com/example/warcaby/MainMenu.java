package com.example.warcaby;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.roomlist.RoomList;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        GameManager.setContext(this); // uruchomic raz na activity
        PlaceholderUtility.initialize(); // raz na cala aplikacje

        Button enterGame = findViewById(R.id.startGame);
        EditText playerName = findViewById(R.id.editPlayerName);

        enterGame.setOnClickListener(v -> {
            String Name = playerName.getText().toString();
            if(!Name.isEmpty()){

                GameManager.setUserPlayer(new Player(Name,null));
                GameManager.createPlayer(GameManager.getUserPlayer());

                GameManager.setServerCallbackListerer(new GameManager.ServerCallbackListener() {
                    @Override
                    public void onServerResponse() {
                        startActivity(new Intent(MainMenu.this, RoomList.class));
                    }

                    @Override
                    public void onServerFailed() {
                        Toast.makeText(GameManager.getmContext(),"Brak odpowiedzi z servera!", Toast.LENGTH_SHORT).show();
                    }
                });
            }


        });

        //Game game = new Game("11111111111110000000000000000000000000000000000000000222222222222222222");
        //cGameBtn.setOnClickListener(v -> GameManager.createGame(game));

    }
}