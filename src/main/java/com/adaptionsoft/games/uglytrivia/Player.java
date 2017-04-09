package com.adaptionsoft.games.uglytrivia;

class Player {
    private final String name;

    private int position = 0;
    private int goldCoinsCount = 0;
    private boolean inPenaltyBox = false;

    Player(String name) {
        this.name = name;
    }

    int getPosition() {
        return position;
    }

    void moveTo(int newPosition) {
        this.position = newPosition;
    }

    int getGoldCoinsCount() {
        return goldCoinsCount;
    }

    void reward(int goldCoinsCount) {
        this.goldCoinsCount += goldCoinsCount;
    }

    boolean hasWon() {
        return goldCoinsCount >= 6;
    }

    void entersPenaltyBox() {
        this.inPenaltyBox = true;
    }

    void exitsPenaltyBox() {
        this.inPenaltyBox = false;
    }

    boolean isInPenaltyBox() {
        return inPenaltyBox;
    }

    @Override
    public String toString() {
        return name;
    }
}
