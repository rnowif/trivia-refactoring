package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    
    @Test
    public void should_have_won_when_6_gold_coins() {
        Player player = new Player("Foo");
        player.reward(6);

        assertTrue(player.hasWon());
    }

    @Test
    public void should_not_have_won_when_less_than_6_gold_coins() {
        Player player = new Player("Foo");
        player.reward(5);

        assertFalse(player.hasWon());
    }

}