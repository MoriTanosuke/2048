package de.kopis.twothousand;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class AsciiPlayfieldTest {


    @Test
    public void canPrintAPlayfield() throws IOException {
        final Playfield p = new Playfield(4);
        p.addTile(new Tile(1, 2, 8));

        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        final AsciiPlayfield ascii = new AsciiPlayfield(new ScoreCalculator());
        ascii.print(out, p, new KeyboardPlayfieldControl());
        final String rawPlayfield = out.toString();

        // extract tile value
        final String value = rawPlayfield.substring(25, 29);
        assertEquals("   8", value);
    }
}