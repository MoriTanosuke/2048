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
        for (int x = 0; x < pf.getMaxX(); x++) {
            for (int y = 0; y < pf.getMaxY(); y++) {
                Tile t = pf.getTile(x, y);
                if (t != null) {
                    out.write(String.format("%4d", t.value));
                } else {
                    out.write("   X");
                }
            }
            out.write("\n");
        }
        out.write(control.getDescription());
        out.write("\n");
        out.write(calculator.getScoreDescription(pf));
        if (calculator.hasWon(pf)) {
            out.write("You have won the game. Feel free to continue.");
        }
        out.write("\n");
        out.flush();
    }
}
