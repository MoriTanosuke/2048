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
        final AsciiPlayfield ascii = new AsciiPlayfield();
        ascii.print(out, p);
        assertEquals("   X   X   X   X\n   X   X   8   X\n   X   X   X   X\n   X   X   X   X\n", out.toString());
    }
}