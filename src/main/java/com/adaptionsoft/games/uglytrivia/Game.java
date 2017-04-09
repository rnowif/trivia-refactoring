package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.*;

public class Game {
    private static final int NB_CELLS = 12;
    private static final Category[] CATEGORIES = new Category[]{Category.POP, Category.SCIENCE, Category.SPORTS, Category.ROCK};

    private final PrintStream output;
    private final Map<Integer, Category> categoriesByPosition = new HashMap<>(NB_CELLS);
    private final Map<Category, Deque<String>> questionsByCategory = new HashMap<>();

    private final List<Player> players = new ArrayList<>();
    private Player currentPlayer = null;
    private int currentPlayerIndex = 0;

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

        for (int i = 0; i < NB_CELLS; i++) {
            categoriesByPosition.put(i, CATEGORIES[i % CATEGORIES.length]);
        }
    }

    @Deprecated
    public Game() {
        this(System.out);
    }

    public void add(String playerName) {
        players.add(new Player(playerName));
        print(playerName + " was added");
        print("There are " + players.size() + " players");
        currentPlayer = players.get(currentPlayerIndex);
    }

    private void print(String message) {
        output.println(message);
    }

    public void roll(int roll) {
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

        currentPlayer.moveTo(newPosition(currentPlayer.getPosition(), roll));

        Category currentCategory = categoryOf(currentPlayer.getPosition());

        print(currentPlayer + "'s new location is " + currentPlayer.getPosition());
        print("The category is " + currentCategory);
        print(nextQuestionAbout(currentCategory));
    }

    private int newPosition(int currentPosition, int roll) {
        return (currentPosition + roll) % NB_CELLS;
    }

    private String nextQuestionAbout(Category category) {
        return questionsByCategory.get(category).removeFirst();
    }

    private Category categoryOf(int position) {
        return categoriesByPosition.get(position);
    }

    private Player nextPlayer() {
        currentPlayerIndex = (currentPlayerIndex + 1) % players.size();
        return players.get(currentPlayerIndex);
    }

    /**
     * @return true if the game continues
     */
    public boolean wasCorrectlyAnswered() {
        if (currentPlayer.isInPenaltyBox()) {
            currentPlayer = nextPlayer();
            return true;
        }

        print("Answer was correct!!!!");
        currentPlayer.reward(1);
        print(currentPlayer + " now has " + currentPlayer.getGoldCoinsCount() + " Gold Coins.");

        boolean gameContinues = !currentPlayer.hasWon();
        currentPlayer = nextPlayer();

        return gameContinues;
    }

    /**
     * @return true if the game continues
     */
    public boolean wrongAnswer() {
        print("Question was incorrectly answered");
        currentPlayer.entersPenaltyBox();
        print(currentPlayer + " was sent to the penalty box");

        currentPlayer = nextPlayer();
        return true;
    }
}
