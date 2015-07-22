package de.kopis.twothousand.displays;

import de.kopis.twothousand.controls.KeyboardPlayfieldControl;
import de.kopis.twothousand.models.Playfield;
import de.kopis.twothousand.models.Tile;
import de.kopis.twothousand.scores.SimpleScoreCalculator;
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
        final AsciiPlayfield ascii = new AsciiPlayfield(new SimpleScoreCalculator());
        ascii.print(out, p, new KeyboardPlayfieldControl());
        assertTrue(out.toString().startsWith("   X   X   X   X\n   X   X   8   X\n   X   X   X   X\n   X   X   X   X\n"));
    }
}