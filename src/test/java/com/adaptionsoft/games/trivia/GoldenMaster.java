package com.adaptionsoft.games.trivia;

import com.adaptionsoft.games.uglytrivia.*;
import com.github.approval.Approvals;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;

import static java.util.Arrays.asList;

public class GoldenMaster {

    private static final int NB_CELLS = 12;
    private static final int NB_QUESTIONS = 50;
    private static final List<Category> CATEGORIES = asList(Category.POP, Category.SCIENCE, Category.SPORTS, Category.ROCK);
    private static final List<String> PLAYERS = asList("Chet", "Pat", "Sue");

    @Test
    public void should_record_and_verify_golden_master() {
        String result = playGame(1L);

        Approvals.verify(result, Paths.get("src", "main", "resources", "approval", "result.txt"));
    }

    private String playGame(long seed) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        boolean notAWinner;
        Game aGame = new Game(
                new PrintStream(outputStream),
                new Board(NB_CELLS, CATEGORIES),
                new QuestionDeck(NB_QUESTIONS, CATEGORIES),
                new PlayerList(PLAYERS)
        );

        Random rand = new Random(seed);

        do {

            aGame.roll(rand.nextInt(5) + 1);

            if (rand.nextInt(9) == 7) {
                notAWinner = aGame.wrongAnswer();
            } else {
                notAWinner = aGame.wasCorrectlyAnswered();
            }

        } while (notAWinner);

        return new String(outputStream.toByteArray());
    }
}
