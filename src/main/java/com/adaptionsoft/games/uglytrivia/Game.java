package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.List;

import static java.util.Arrays.asList;

public class Game {

    private final PrintStream output;
    private final Board board;
    private final PlayerList players;
    private final QuestionDeck deck;

    public Game(PrintStream output, Board board, QuestionDeck deck, PlayerList players) {
        this.output = output;
        this.board = board;
        this.deck = deck;
        this.players = players;
    }

    private void print(String message) {
        output.println(message);
    }

    public void roll(int roll) {
        Player currentPlayer = players.currentPlayer();
        print(currentPlayer + " is the current player");
        print("They have rolled a " + roll);

        if (currentPlayer.isInPenaltyBox()) {
            if (shouldReleaseFromPenaltyBox(roll)) {
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

    private boolean shouldReleaseFromPenaltyBox(int roll) {
        return roll % 2 != 0;
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
