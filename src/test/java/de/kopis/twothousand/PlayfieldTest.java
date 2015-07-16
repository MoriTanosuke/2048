package de.kopis.twothousand;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayfieldTest {
    @Test
    public void emptyGridHasAvailableTilesUntilFull() {
        final int size = 4;

        final Playfield grid = new Playfield(size);
        assertTrue(grid.hasAvailableSlots());

        // fill the whole grid with random tiles
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                grid.addTile(new Tile(x, y, 2));
                assertTrue(grid.hasAvailableSlots());
            }
        }
        grid.addRandomTile();
        assertFalse(grid.hasAvailableSlots());
    }

    @Test
    public void playfieldCanMoveATileToEmptySpaces() {
        final Playfield grid = new Playfield(4);
        Tile originalTile = new Tile(4, 4, 2);
        grid.addTile(originalTile);
        grid.moveTile(4, 4, Playfield.Direction.UP);
        Tile t1 = grid.getTile(4, 3);
        assertEquals(originalTile.value, t1.value);
        assertTrue(grid.getTile(4, 4) == null);

        grid.moveTile(4, 3, Playfield.Direction.DOWN);
        Tile t2 = grid.getTile(4, 4);
        assertEquals(originalTile.value, t2.value);
        assertTrue(grid.getTile(4, 3) == null);
    }

    @Test
    public void playfieldCanMoveATileOnAnotherTileWithSameValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(4, 3, 2));
        grid.addTile(new Tile(4, 4, 2));
        grid.moveTile(4, 4, Playfield.Direction.UP);

        Tile t = grid.getTile(4, 3);
        assertEquals(4, t.value);
        assertTrue(grid.getTile(4, 4) == null);
    }

    @Test
    public void playfieldCanNotMoveATileOnAnotherTileWithDifferentValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(4, 3, 4));
        grid.addTile(new Tile(4, 4, 2));
        grid.moveTile(4, 4, Playfield.Direction.UP);

        Tile t1 = grid.getTile(4, 3);
        assertEquals(4, t1.value);

        Tile t2 = grid.getTile(4, 4);
        assertEquals(2, t2.value);
    }

    @Test
    public void canPrintAPlayfield() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        final Writer out = new PrintWriter(bos);
        final Playfield p = new Playfield(4);
        p.addTile(new Tile(1, 2, 8));
        p.print(out);
        assertEquals("XXXX\nXX8X\nXXXX\nXXXX\n", bos.toString());
    }
}