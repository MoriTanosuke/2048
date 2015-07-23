package de.kopis.twothousand.scores;

import de.kopis.twothousand.models.Playfield;
import de.kopis.twothousand.models.Tile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class SimpleScoreCalculatorTest {
    @Test
    public void canCalculateScoreOnEmptyPlayfield() {
        final Playfield playfield = new Playfield(4);
        final SimpleScoreCalculator calculator = new SimpleScoreCalculator();
        int score = calculator.calculateScore(playfield);
        assertEquals(0, score);
    }

    @Test
    public void canCalculateScoreOnPlayfieldWithAllFieldsFilled() {
        final int lowValue = 2;
        final int highValue = 32;

        final Playfield playfield = new Playfield(4);
        // add all 2s to playfield
        for (int x = 0; x < playfield.getMaxX(); x++) {
            for (int y = 0; y < playfield.getMaxY(); y++) {
                playfield.addTile(new Tile(x, y, lowValue));
            }
        }
        // replace one field with a higher value
        playfield.addTile(new Tile(0, 0, highValue));

        final SimpleScoreCalculator calculator = new SimpleScoreCalculator();
        int score = calculator.calculateScore(playfield);
        assertEquals(15 * lowValue + highValue, score);
    }

    @Test
    public void recognizesAWinningGame() {
        final Playfield playfield = new Playfield(4);
        playfield.addTile(new Tile(1, 1, 2048));
        final SimpleScoreCalculator calculator = new SimpleScoreCalculator();
        assertTrue(calculator.hasWon(playfield));
    }
}
