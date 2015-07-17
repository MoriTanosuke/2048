package de.kopis.twothousand;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class AsciiPlayfieldTest {


    @Test
    public void canPrintAPlayfield() throws IOException {
        final Playfield p = new Playfield(4);
        p.addTile(new Tile(1, 2, 8));

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AsciiPlayfield ascii = new AsciiPlayfield(new ScoreCalculator());
        ascii.print(out, p, new KeyboardPlayfieldControl());
        assertTrue(out.toString().startsWith("   X   X   X   X\n   X   X   8   X\n   X   X   X   X\n   X   X   X   X\n"));
    }
}