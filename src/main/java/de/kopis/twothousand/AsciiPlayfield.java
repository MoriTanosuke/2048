package de.kopis.twothousand;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;

public class AsciiPlayfield {
    /**
     * @see #print(OutputStream, Playfield)
     */
    public void print(final OutputStream out, final Playfield pf) throws IOException {
        print(new PrintWriter(out), pf);
    }

    /**
     * Prints the whole playfield using the given {@link Writer}.
     *
     * @param out {@link Writer} to use for printing. Is flushed after playfield is written.
     * @throws IOException
     */
    public void print(final Writer out, final Playfield pf) throws IOException {
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
        out.flush();
    }
}
