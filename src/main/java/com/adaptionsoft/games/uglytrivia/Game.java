package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.*;

import static java.util.Arrays.asList;

public class Game {
    private static final int NB_CELLS = 12;
    private static final int NB_QUESTIONS = 50;
    private static final List<Category> CATEGORIES = asList(Category.POP, Category.SCIENCE, Category.SPORTS, Category.ROCK);

    private final PrintStream output;
    private final PlayerList players = new PlayerList();
    private final Board board = new Board(NB_CELLS, CATEGORIES);
    private final QuestionDeck deck = new QuestionDeck(NB_QUESTIONS, CATEGORIES);

    public Game(PrintStream output) {
        this.output = output;
    }

    @Deprecated
    public Game() {
        this(System.out);
    }

    public void add(String playerName) {
        players.add(playerName);
        print(playerName + " was added");
        print("There are " + players.size() + " players");
    }

    private void print(String message) {
        output.println(message);
    }

    public void roll(int roll) {
        Player currentPlayer = players.currentPlayer();
        print(currentPlayer + " is the current player");
        print("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (roll % 2 != 0) {
                currentPlayer.exitsPenaltyBox();
                print(currentPlayer + " is getting out of the penalty box");
            } else {
                print(currentPlayer + " is not getting out of the penalty box");
                return;
            }

        }

        int newPosition = board.newPosition(currentPlayer.getPosition(), roll);

        currentPlayer.moveTo(newPosition);
        print(currentPlayer + "'s new location is " + newPosition);

        Category currentCategory = board.categoryOf(newPosition);
        print("The category is " + currentCategory);
        print(deck.nextQuestionAbout(currentCategory));
    }

    /**
     * @return true if the game continues
     */
    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.currentPlayer();
        players.nextPlayer();

        if (currentPlayer.isInPenaltyBox()) {
            return true;
        }

        print("Answer was correct!!!!");
        currentPlayer.reward(1);
        print(currentPlayer + " now has " + currentPlayer.getGoldCoinsCount() + " Gold Coins.");

        return !currentPlayer.hasWon();
    }

    /**
     * @return true if the game continues
     */
    public boolean wrongAnswer() {
        Player currentPlayer = players.currentPlayer();
        players.nextPlayer();

        print("Question was incorrectly answered");
        currentPlayer.entersPenaltyBox();
        print(currentPlayer + " was sent to the penalty box");

        return true;
    }
}
