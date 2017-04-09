package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.*;

import static java.util.Arrays.asList;

public class Game {
    private static final int NB_CELLS = 12;
    private static final List<Category> CATEGORIES = asList(Category.POP, Category.SCIENCE, Category.SPORTS, Category.ROCK);

    private final PrintStream output;
    private final Map<Category, Deque<String>> questionsByCategory = new HashMap<>();

    private final PlayerList players = new PlayerList();
    private final Board board = new Board(NB_CELLS, CATEGORIES);

    public Game(PrintStream output) {
        this.output = output;

        for (Category category : CATEGORIES) {
            questionsByCategory.put(category, new LinkedList<>());
        }

        for (int i = 0; i < 50; i++) {
            for (Category category : CATEGORIES) {
                questionsByCategory.get(category).addLast(category.toString() + " Question " + i);
            }
        }
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
        print(nextQuestionAbout(currentCategory));
    }

    private String nextQuestionAbout(Category category) {
        return questionsByCategory.get(category).removeFirst();
    }

    /**
     * @return true if the game continues
     */
    public boolean wasCorrectlyAnswered() {
        Player currentPlayer = players.currentPlayer();
        if (currentPlayer.isInPenaltyBox()) {
            players.nextPlayer();
            return true;
        }

        print("Answer was correct!!!!");
        currentPlayer.reward(1);
        print(currentPlayer + " now has " + currentPlayer.getGoldCoinsCount() + " Gold Coins.");

        boolean gameContinues = !currentPlayer.hasWon();
        players.nextPlayer();

        return gameContinues;
    }

    /**
     * @return true if the game continues
     */
    public boolean wrongAnswer() {
        Player currentPlayer = players.currentPlayer();
        print("Question was incorrectly answered");
        currentPlayer.entersPenaltyBox();
        print(currentPlayer + " was sent to the penalty box");

        players.nextPlayer();
        return true;
    }
}
