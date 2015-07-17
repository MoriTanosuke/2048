package de.kopis.twothousand;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScoreCalculatorTest {
    @Test
    public void canCalculateScoreOnEmptyPlayfield() {
        final Playfield playfield = new Playfield(4);
        final ScoreCalculator calculator = new ScoreCalculator();
        int score = calculator.calculateScore(playfield);
        assertEquals(0, score);
    }
}
