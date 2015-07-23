package de.kopis.twothousand.scores;

import de.kopis.twothousand.models.Playfield;
import de.kopis.twothousand.models.Tile;

public class SimpleScoreCalculator implements ScoreCalculator {
    public int calculateScore(Playfield playfield) {
        int score = 0;
        for (int x = 0; x < playfield.getMaxX(); x++) {
            for (int y = 0; y < playfield.getMaxY(); y++) {
                Tile t = playfield.getTile(x, y);
                if (t != null) {
                    score += t.value;
                }
            }
        }
        return score;
    }

    public boolean hasWon(Playfield playfield) {
        boolean won = false;
        for (int x = 0; x < playfield.getMaxX(); x++) {
            for (int y = 0; y < playfield.getMaxY(); y++) {
                Tile t = playfield.getTile(x, y);
                if (t != null && t.value == 2048) {
                    won = true;
                    break;
                }
            }
            if (won) {
                break;
            }
        }
        return won;
    }

    @Override
    public String getScoreDescription(Playfield playfield) {
        return String.format("Your score: %d", calculateScore(playfield));
    }
}