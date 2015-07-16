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
    public void playfieldCanMoveATileToEmptySpaces() throws IOException {
        final Playfield grid = new Playfield(4);
        final Tile originalTile = new Tile(3, 3, 2);
        grid.addTile(originalTile);

        final String s1 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\nXXX2\n", s1);

        // UP - x=2,y=3
        grid.moveTile(3, 3, Playfield.Direction.UP);
        final String s2 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXX2\nXXXX\n", s2);
        Tile t1 = grid.getTile(2, 3);
        assertEquals(originalTile.value, t1.value);
        assertTrue(grid.getTile(3, 3) == null);

        // DOWN, x=3,y=3 again
        grid.moveTile(2, 3, Playfield.Direction.DOWN);
        final String s3 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\nXXX2\n", s3);
        Tile t2 = grid.getTile(3, 3);
        assertEquals(originalTile.value, t2.value);
        assertTrue(grid.getTile(2, 3) == null);

        // LEFT, x=3,y=2
        grid.moveTile(3, 3, Playfield.Direction.LEFT);
        final String s4 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\nXX2X\n", s4);
        Tile t3 = grid.getTile(3, 2);
        assertEquals(originalTile.value, t3.value);
        assertTrue(grid.getTile(3, 3) == null);

        //RIGHT, x=3,y=3 again
        grid.moveTile(3, 2, Playfield.Direction.RIGHT);
        final String s5 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\nXXX2\n", s5);
        Tile t4 = grid.getTile(3, 3);
        assertEquals(originalTile.value, t4.value);
        assertTrue(grid.getTile(3, 2) == null);
    }

    private String dump(Playfield grid) throws IOException {
        final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        grid.print(new PrintWriter(bos1));
        return bos1.toString();
    }

    @Test
    public void playfieldCanMoveATileOnAnotherTileWithSameValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(2, 3, 2));
        grid.addTile(new Tile(3, 3, 2));
        grid.moveTile(3, 3, Playfield.Direction.UP);

        Tile t = grid.getTile(2, 3);
        assertEquals(4, t.value);
        assertTrue(grid.getTile(3, 3) == null);
    }

    @Test
    public void playfieldCanNotMoveATileOnAnotherTileWithDifferentValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(2, 3, 4));
        grid.addTile(new Tile(3, 3, 2));
        grid.moveTile(3, 3, Playfield.Direction.UP);

        Tile t1 = grid.getTile(2, 3);
        assertEquals(4, t1.value);

        Tile t2 = grid.getTile(3, 3);
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