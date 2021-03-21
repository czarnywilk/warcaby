package com.example.warcaby.recyclerview;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.warcaby.GameManager;
import com.example.warcaby.R;
import com.example.warcaby.multiplayer.serialized.Game;
import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.RoomViewHolder> {

    private ArrayList<Game> gamesList;

    public RoomAdapter(ArrayList<Game> games) {
        gamesList = games;
    }

    @NonNull
    @Override
    public RoomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_item, parent, false);
        return new RoomViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomViewHolder holder, int position) {
        Game game = gamesList.get(position);

        if (game != null) {
            holder.roomName.setText(game.getGameName());
            String players = "Players: " + game.getPlayersCount() + " / 2";
            holder.playersCount.setText(players);

            holder.joinButton.setOnClickListener(v -> {
                if (game.getPlayersCount() < 2) {
                    GameManager.getGame(game.getId());
                    GameManager.setServerCallbackListener(new GameManager.ServerCallbackListener() {
                        @Override
                        public void onServerResponse(Object obj) {
                            Game _game = (Game) obj;
                            if (_game.getCurrentPlayerId() == null) {
                                _game.setWhitePlayerId(GameManager.getUserPlayer().getId());
                                _game.setCurrentPlayerId(GameManager.getUserPlayer().getId());
                            }
                            else {
                                _game.setBlackPlayerId(GameManager.getUserPlayer().getId());
                            }
                            GameManager.joinGame(_game);
                        }

                        @Override
                        public void onServerFailed() {

                        }
                    });
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

     class RoomViewHolder extends RecyclerView.ViewHolder {

        public TextView roomName;
        public TextView playersCount;
        public Button joinButton;

        public RoomViewHolder(@NonNull View itemView) {
            super(itemView);

            roomName = itemView.findViewById(R.id.roomNameText);
            playersCount = itemView.findViewById(R.id.playersCountText);
            joinButton = itemView.findViewById(R.id.joinGameButton);
        }
    }
}
