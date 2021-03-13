package com.example.warcaby.lobby;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.warcaby.GameManager;
import com.example.warcaby.R;

public class Lobby extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lobby);

        GameManager.setContext(this);
    }
}