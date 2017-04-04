package com.adaptionsoft.games.uglytrivia;

public enum Category {

    POP("Pop"), SCIENCE("Science"), SPORTS("Sports"), ROCK("Rock");

    private final String message;

    Category(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }
}
