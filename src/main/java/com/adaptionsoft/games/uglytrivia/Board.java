package com.adaptionsoft.games.uglytrivia;

import java.util.List;

public class Board {

    private final int cellsCount;
    private final List<Category> categories;

    public Board(int cellsCount, List<Category> categories) {
        this.cellsCount = cellsCount;
        this.categories = categories;
    }

    int newPosition(int currentPosition, int offset) {
        return (currentPosition + offset) % cellsCount;
    }

    Category categoryOf(int position) {
        return categories.get(position % categories.size());
    }
}
