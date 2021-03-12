package com.example.warcaby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.warcaby.multiplayer.PlaceholderUtility;
import com.example.warcaby.multiplayer.serialized.Game;
import com.example.warcaby.multiplayer.serialized.Player;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        GameManager.setContext(this); // uruchomic raz na activity
        PlaceholderUtility.initialize(); // raz na cala aplikacje

        Button cGameBtn = findViewById(R.id.createGame);
        Button cPlayerBtn = findViewById(R.id.createPlayer);

        Player p1 = new Player("p1", null);
        Player p2 = new Player("p2", null);
        cPlayerBtn.setOnClickListener(v -> {
            GameManager.createPlayer(p1);
            GameManager.createPlayer(p2);
        });

        Game game = new Game("11111111111110000000000000000000000000000000000000000222222222222222222");
        cGameBtn.setOnClickListener(v -> GameManager.createGame(game));

    }
}