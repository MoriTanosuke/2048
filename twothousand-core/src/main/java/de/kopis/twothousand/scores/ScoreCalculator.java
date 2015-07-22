package de.kopis.twothousand.scores;

import de.kopis.twothousand.models.Playfield;

public interface ScoreCalculator {
    /**
     * Calculates the score for the given {@link Playfield}.
     *
     * @param playfield
     * @return the score
     */
    int calculateScore(Playfield playfield);

    /**
     * Checks if the given {@link Playfield} is already won.
     *
     * @param playfield
     * @return <code>true</code> if game has reached winning state, else <code>false</code>
     */
    boolean hasWon(Playfield playfield);

    /**
     * Returns a String representation of the score of the given {@link Playfield}
     *
     * @param playfield
     * @return a score with a description, for example "Your score: 1234"
     */
    String getScoreDescription(Playfield playfield);
}
