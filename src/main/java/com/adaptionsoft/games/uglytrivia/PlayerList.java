package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class PlayerList {

    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    public PlayerList(List<String> names) {
        names.forEach(name -> players.add(new Player(name)));
    }

    Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    Player nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(currentPlayerIndex);
    }
}
