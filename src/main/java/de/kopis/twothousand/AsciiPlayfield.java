package de.kopis.twothousand;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class AsciiPlayfield {
    private final ScoreCalculator calculator;

    public AsciiPlayfield(ScoreCalculator scoreCalculator) {
        this.calculator = scoreCalculator;
    }

    /**
     * @see #print(Writer, Playfield, PlayfieldControl)
     */
    public void print(final OutputStream out, final Playfield pf, final PlayfieldControl control) throws IOException {
        print(new PrintWriter(out), pf, control);
    }

    /**
     * Prints the whole playfield using the given {@link Writer}.
     *
     * @param out     {@link Writer} to use for printing. Is flushed after playfield is written
     * @param pf      {@link Playfield} to display
     * @param control {@link PlayfieldControl} to display to player
     * @throws IOException
     */
    public void print(final Writer out, final Playfield pf, final PlayfieldControl control) throws IOException {
        for (int x = 0; x < pf.maxX; x++) {
            for (int y = 0; y < pf.maxY; y++) {
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
        //out.write(String.format("Your score: %d", calculator.calculateScore(pf)));
        //out.write("\n");
        out.flush();
    }
}
