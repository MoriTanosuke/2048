package de.kopis.twothousand;

public class ScoreCalculator {
    public int calculateScore(Playfield playfield) {
        int score = 0;
        for (int x = 0; x < playfield.maxX; x++) {
            for (int y = 0; y < playfield.maxY; y++) {
                Tile t = playfield.getTile(x, y);
                if (t != null) {
                    score += t.value;
                }
            }
        }
        return score;
    }
}
