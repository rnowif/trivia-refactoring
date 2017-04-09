package com.adaptionsoft.games.uglytrivia;

import java.util.*;

class QuestionDeck {
    private final Map<Category, Deque<String>> questionsByCategory = new HashMap<>();

    QuestionDeck(int questionCount, List<Category> categories) {
        for (Category category : categories) {
            questionsByCategory.put(category, new LinkedList<>());
        }

        for (int i = 0; i < questionCount; i++) {
            for (Category category : categories) {
                questionsByCategory.get(category).addLast(category.toString() + " Question " + i);
            }
        }
    }

    String nextQuestionAbout(Category category) {
        return questionsByCategory.get(category).removeFirst();
    }
}
