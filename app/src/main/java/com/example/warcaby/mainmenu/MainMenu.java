package com.example.warcaby.mainmenu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.warcaby.GameManager;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Player;
import com.example.warcaby.roomlist.RoomList;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        GameManager.setContext(this); // uruchomic raz na activity
        PlaceholderUtility.initialize(); // raz na cala aplikacje

        Button enterGame = findViewById(R.id.startGameButton);
        EditText playerName = findViewById(R.id.playerNameInput);

        enterGame.setOnClickListener(v -> {
            String Name = playerName.getText().toString();
            if(!Name.isEmpty()){

                GameManager.setUserPlayer(new Player(Name,null));
                GameManager.createPlayer(GameManager.getUserPlayer());

                GameManager.setServerCallbackListener(new GameManager.ServerCallbackListener() {
                    @Override
                    public void onServerResponse(Object obj) {
                        startActivity(new Intent(MainMenu.this, RoomList.class));
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
}