package com.adaptionsoft.games.uglytrivia;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class Game {
    public static final int NB_CELLS = 12;
    private final PrintStream output;
    private List<String> players = new ArrayList<>();
    private int[] places = new int[6];
    private int[] purses = new int[6];
    private boolean[] inPenaltyBox = new boolean[6];

    private Deque<String> popQuestions = new LinkedList<>();
    private Deque<String> scienceQuestions = new LinkedList<>();
    private Deque<String> sportsQuestions = new LinkedList<>();
    private Deque<String> rockQuestions = new LinkedList<>();

    private int currentPlayer = 0;
    private boolean isGettingOutOfPenaltyBox;

    public Game(PrintStream output) {
        this.output = output;
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast("Science Question " + i);
            sportsQuestions.addLast("Sports Question " + i);
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    @Deprecated
    public Game() {
        this(System.out);
    }

    public boolean add(String playerName) {
        players.add(playerName);
        places[players.size()] = 0;
        purses[players.size()] = 0;
        inPenaltyBox[players.size()] = false;

        print(playerName + " was added");
        print("They are player number " + players.size());
        return true;
    }

    private void print(String message) {
        output.println(message);
    }

    public void roll(int roll) {
        print(players.get(currentPlayer) + " is the current player");
        print("They have rolled a " + roll);

        if (inPenaltyBox[currentPlayer]) {
            if (roll % 2 != 0) {
                isGettingOutOfPenaltyBox = true;

                print(players.get(currentPlayer) + " is getting out of the penalty box");
                move(roll);

                print(players.get(currentPlayer)
                        + "'s new location is "
                        + currentPosition());
                print("The category is " + currentCategory());
                askQuestion();
            } else {
                print(players.get(currentPlayer) + " is not getting out of the penalty box");
                isGettingOutOfPenaltyBox = false;
            }

        } else {

            move(roll);

            print(players.get(currentPlayer)
                    + "'s new location is "
                    + currentPosition());
            print("The category is " + currentCategory());
            askQuestion();
        }

    }

    private void move(int offset) {
        places[currentPlayer] = (currentPosition() + offset) % NB_CELLS;
    }

    private void askQuestion() {
        if (currentCategory().equals(Category.POP))
            print(popQuestions.removeFirst());
        if (currentCategory().equals(Category.SCIENCE))
            print(scienceQuestions.removeFirst());
        if (currentCategory().equals(Category.SPORTS))
            print(sportsQuestions.removeFirst());
        if (currentCategory().equals(Category.ROCK))
            print(rockQuestions.removeFirst());
    }


    private Category currentCategory() {
        if (currentPosition() == 0) return Category.POP;
        if (currentPosition() == 4) return Category.POP;
        if (currentPosition() == 8) return Category.POP;
        if (currentPosition() == 1) return Category.SCIENCE;
        if (currentPosition() == 5) return Category.SCIENCE;
        if (currentPosition() == 9) return Category.SCIENCE;
        if (currentPosition() == 2) return Category.SPORTS;
        if (currentPosition() == 6) return Category.SPORTS;
        if (currentPosition() == 10) return Category.SPORTS;
        return Category.ROCK;
    }

    private int currentPosition() {
        return places[currentPlayer];
    }

    private int nextPlayer() {
        return (currentPlayer + 1) % players.size();
    }

    public boolean wasCorrectlyAnswered() {
        if (inPenaltyBox[currentPlayer]) {
            if (isGettingOutOfPenaltyBox) {
                print("Answer was correct!!!!");
                purses[currentPlayer]++;
                print(players.get(currentPlayer)
                        + " now has "
                        + purses[currentPlayer]
                        + " Gold Coins.");

                boolean winner = didPlayerWin();
                currentPlayer = nextPlayer();

                return winner;
            } else {
                currentPlayer = nextPlayer();
                return true;
            }


        } else {

            print("Answer was corrent!!!!");
            purses[currentPlayer]++;
            print(players.get(currentPlayer)
                    + " now has "
                    + purses[currentPlayer]
                    + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer = nextPlayer();

            return winner;
        }
    }

    public boolean wrongAnswer() {
        print("Question was incorrectly answered");
        print(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox[currentPlayer] = true;

        currentPlayer = nextPlayer();
        return true;
    }


    private boolean didPlayerWin() {
        return !(purses[currentPlayer] == 6);
    }
}
