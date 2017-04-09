package com.adaptionsoft.games.uglytrivia;

import java.util.ArrayList;
import java.util.List;

public class PlayerList {

    private final List<Player> players = new ArrayList<>();
    private int currentPlayerIndex = 0;

    /**
     * @deprecated Use the constructor with a predefined list of players
     */
    @Deprecated
    public PlayerList() {
        this(new ArrayList<>());
    }

    public PlayerList(List<String> names) {
        names.forEach(this::add);
    }

    /**
     * @deprecated Use the constructor with a predefined list of players
     * @param playerName Name of the player to add
     */
    @Deprecated
    void add(String playerName) {
        players.add(new Player(playerName));
    }

    Player currentPlayer() {
        return players.get(currentPlayerIndex);
    }

    int size() {
        return players.size();
    }

    Player nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(currentPlayerIndex);
    }
}
