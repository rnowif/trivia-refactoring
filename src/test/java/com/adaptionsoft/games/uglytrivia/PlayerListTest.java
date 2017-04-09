package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class PlayerListTest {
    
    @Test
    public void should_select_first_player_of_the_list_when_first_round() {
        PlayerList players = new PlayerList(asList("Sue", "Martin"));

        assertThat(players.currentPlayer().getName(), is("Sue"));
    }
    
    @Test
    public void should_pass_to_the_next_player_when_next_round() {
        PlayerList players = new PlayerList(asList("Sue", "Martin"));
        assertThat(players.nextPlayer().getName(), is("Martin"));
    }

    @Test
    public void should_pass_to_the_first_player_when_next_round_after_the_last_player() {
        PlayerList players = new PlayerList(asList("Sue", "Martin"));
        players.nextPlayer();

        assertThat(players.nextPlayer().getName(), is("Sue"));
    }

}