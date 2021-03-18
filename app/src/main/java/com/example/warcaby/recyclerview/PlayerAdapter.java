package com.example.warcaby.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warcaby.R;
import com.example.warcaby.multiplayer.serialized.Player;

import java.util.ArrayList;

public class PlayerAdapter extends RecyclerView.Adapter<PlayerAdapter.PlayerViewHolder> {

    private ArrayList<Player> playersList;

    public PlayerAdapter(ArrayList<Player> players) {
        playersList = players;
    }

    @NonNull
    @Override
    public PlayerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.player_item, parent, false);
        return new PlayerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PlayerViewHolder holder, int position) {
        Player player = playersList.get(position);

        if (player != null) {
            holder.playerName.setText(player.getPlayerName());
        }
    }

    @Override
    public int getItemCount() {
        return playersList.size();
    }

    class PlayerViewHolder extends RecyclerView.ViewHolder {

        public TextView playerName;

        public PlayerViewHolder(@NonNull View itemView) {
            super(itemView);

            playerName = itemView.findViewById(R.id.playerNameText);
        }
    }
}

