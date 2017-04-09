package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class BoardTest {
    
    @Test
    public void should_move_forward_when_move() {
        Board board = new Board(12, emptyList());

        assertThat(board.newPosition(0, 4), is(4));
    }

    @Test
    public void should_return_to_start_when_move_goes_beyond_number_of_cells() {
        Board board = new Board(12, emptyList());

        assertThat(board.newPosition(11, 1), is(0));
    }
    
    @Test
    public void should_return_categories_in_the_right_order() {
        Board board = new Board(12, asList(Category.POP, Category.SCIENCE));

        assertThat(board.categoryOf(0), is(Category.POP));
        assertThat(board.categoryOf(1), is(Category.SCIENCE));
        assertThat(board.categoryOf(2), is(Category.POP));
    }

}