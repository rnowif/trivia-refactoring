package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.*;

public class Game {
    private static final int NB_CELLS = 12;
    private static final Category[] CATEGORIES = new Category[]{Category.POP, Category.SCIENCE, Category.SPORTS, Category.ROCK};

    private final PrintStream output;
    private final Map<Integer, Category> categoriesByPosition = new HashMap<>(NB_CELLS);
    private final Map<Category, Deque<String>> questionsByCategory = new HashMap<>();

    private final List<String> players = new ArrayList<>();
    private final int[] places = new int[6];
    private final int[] purses = new int[6];
    private final boolean[] inPenaltyBox = new boolean[6];
    private int currentPlayer = 0;

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
        players.add(playerName);
        places[players.size()] = 0;
        purses[players.size()] = 0;
        inPenaltyBox[players.size()] = false;

        print(playerName + " was added");
        print("There are " + players.size() + " players");
    }

    private void print(String message) {
        output.println(message);
    }

    public void roll(int roll) {
        print(players.get(currentPlayer) + " is the current player");
        print("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                inPenaltyBox[currentPlayer] = false;
                print(players.get(currentPlayer) + " is getting out of the penalty box");
            } else {
                print(players.get(currentPlayer) + " is not getting out of the penalty box");
                return;
            }

        }

        move(roll);

        print(players.get(currentPlayer) + "'s new location is " + currentPosition());
        print("The category is " + currentCategory());
        askQuestion();
    }

    private void askQuestion() {
        print(questionsByCategory.get(currentCategory()).removeFirst());
    }

    private Category currentCategory() {
        return categoriesByPosition.get(currentPosition());
    }


    private void move(int offset) {
        places[currentPlayer] = (currentPosition() + offset) % NB_CELLS;
    }

    private int currentPosition() {
        return places[currentPlayer];
    }

    private int nextPlayer() {
        return (currentPlayer + 1) % players.size();
    }

    private boolean didPlayerWin() {
        return purses[currentPlayer] == 6;
    }

    /**
     *
     * @return true if the game continues
     */
    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            currentPlayer = nextPlayer();
            return true;
        } else {

            print("Answer was correct!!!!");
            purses[currentPlayer]++;
            print(players.get(currentPlayer) + " now has " + purses[currentPlayer] + " Gold Coins.");

            boolean gameContinues = !didPlayerWin();
            currentPlayer = nextPlayer();

            return gameContinues;
        }
    }

    /**
     *
     * @return true if the game continues
     */
    public boolean wrongAnswer() {
        print("Question was incorrectly answered");
        print(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer = nextPlayer();
        return true;
    }
}
