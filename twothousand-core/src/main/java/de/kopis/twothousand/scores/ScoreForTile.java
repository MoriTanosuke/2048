package de.kopis.twothousand.scores;

import de.kopis.twothousand.models.Playfield;

interface ScoreForTile {
    int scoreForTile(Playfield pf, int x, int y);
}