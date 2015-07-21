package de.kopis.twothousand;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScoreCalculatorTest {
    @Test
    public void canCalculateScoreOnEmptyPlayfield() {
        final Playfield playfield = new Playfield(4);
        final ScoreCalculator calculator = new ScoreCalculator();
        int score = calculator.calculateScore(playfield);
        // empty playfield can only have a score of 2 or 4
        assertTrue(score == 2 || score == 4);
    }

    @Test
    public void canCalculateScoreOnPlayfieldWithAllFieldsFilled() {
        final int lowValue = 2;
        final int highValue = 32;

        final Playfield playfield = new Playfield(4);
        // add all 2s to playfield
        for (int x = 0; x < playfield.maxX; x++) {
            for (int y = 0; y < playfield.maxY; y++) {
                playfield.addTile(new Tile(x, y, lowValue));
            }
        }
        // replace one field with a higher value
        playfield.addTile(new Tile(0, 0, highValue));

        final ScoreCalculator calculator = new ScoreCalculator();
        int score = calculator.calculateScore(playfield);
        assertEquals(15 * lowValue + highValue, score);
    }
}
