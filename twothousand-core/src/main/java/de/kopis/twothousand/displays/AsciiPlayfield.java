package de.kopis.twothousand.displays;

import de.kopis.twothousand.controls.PlayfieldControl;
import de.kopis.twothousand.models.Playfield;
import de.kopis.twothousand.models.Tile;
import de.kopis.twothousand.scores.ScoreCalculator;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class AsciiPlayfield implements DisplayPlayfield {
    private final ScoreCalculator calculator;

    public AsciiPlayfield(ScoreCalculator scoreCalculator) {
        this.calculator = scoreCalculator;
    }

    @Override
    public void print(final OutputStream output, final Playfield pf, final PlayfieldControl control) throws IOException {
        final PrintWriter out = new PrintWriter(output);
        writeTileColumnValues(pf, out);
        out.write(control.getDescription());
        out.write("\n");
        out.write(calculator.getScoreDescription(pf));
        if (calculator.hasWon(pf)) {
            out.write("You have won the game. Feel free to continue.");
        }
        out.write("\n");
        out.flush();
    }

    private void writeTileColumnValues(final Playfield pf, final PrintWriter out) {
        //TODO maybe rewrite using Stream API?
        /*
         * I'm thinking like this:
         *
         * pf.tiles.columns().stream().splitRows().stream().print(Tile.value)
         *
         * Problem is: how do I add a linebreak after a column? Streaming all tile values should be pretty straight forward
         */
        for (int x = 0; x < pf.getMaxX(); x++) {
            writeTileRowValues(pf, out, x);
        }
    }

    private void writeTileRowValues(final Playfield pf, final PrintWriter out, final int x) {
        for (int y = 0; y < pf.getMaxY(); y++) {
            writeTileValue(out, pf, x, y);
        }
        out.write("\n");
    }

    private void writeTileValue(final PrintWriter out, final Playfield pf, final int x, final int y) {
        final Tile t = pf.getTile(x, y);
        if (t != null) {
            out.write(String.format("%4d", t.value));
        } else {
            out.write("   X");
        }
    }
}
