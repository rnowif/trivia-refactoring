package com.adaptionsoft.games.uglytrivia;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class QuestionDeckTest {

    @Test
    public void should_ask_next_question_for_a_category() {
        QuestionDeck deck = new QuestionDeck(5, asList(Category.ROCK, Category.SPORTS));

        assertThat(deck.nextQuestionAbout(Category.ROCK), is("Rock Question 0"));
        assertThat(deck.nextQuestionAbout(Category.SPORTS), is("Sports Question 0"));
        assertThat(deck.nextQuestionAbout(Category.SPORTS), is("Sports Question 1"));
    }

}