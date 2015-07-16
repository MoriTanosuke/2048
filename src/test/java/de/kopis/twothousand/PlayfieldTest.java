package de.kopis.twothousand;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

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
    public void playfieldCanMoveATileThroughEmptySpaces() throws IOException {
        final Playfield grid = new Playfield(4);
        final Tile originalTile = new Tile(3, 3, 2);
        grid.addTile(originalTile);

        final String s1 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\nXXX2\n", s1);

        grid.moveTile(3, 3, Playfield.Direction.UP);
        final String s2 = dump(grid);
        assertEquals("XXX2\nXXXX\nXXXX\nXXXX\n", s2);
        final Tile t1 = grid.getTile(0, 3);
        assertEquals(originalTile.value, t1.value);
        assertTrue(grid.getTile(3, 3) == null);

        grid.moveTile(0, 3, Playfield.Direction.DOWN);
        final String s3 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\nXXX2\n", s3);
        final Tile t2 = grid.getTile(3, 3);
        assertEquals(originalTile.value, t2.value);
        assertTrue(grid.getTile(2, 3) == null);

        grid.moveTile(3, 3, Playfield.Direction.LEFT);
        final String s4 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\n2XXX\n", s4);
        final Tile t3 = grid.getTile(3, 0);
        assertEquals(originalTile.value, t3.value);
        assertTrue(grid.getTile(3, 3) == null);

        grid.moveTile(3, 0, Playfield.Direction.RIGHT);
        final String s5 = dump(grid);
        assertEquals("XXXX\nXXXX\nXXXX\nXXX2\n", s5);
        final Tile t4 = grid.getTile(3, 3);
        assertEquals(originalTile.value, t4.value);
        assertTrue(grid.getTile(3, 0) == null);
    }

    @Test
    public void playfieldCanMoveATileUntilBlockedByAnotherTile() throws IOException {
        final Playfield grid = new Playfield(4);
        final Tile originalTile = new Tile(3, 3, 2);
        grid.addTile(originalTile);
        final Tile blockingTile = new Tile(0, 3, 4);
        grid.addTile(blockingTile);

        final String s1 = dump(grid);
        assertEquals("XXX4\nXXXX\nXXXX\nXXX2\n", s1);

        grid.moveTile(3, 3, Playfield.Direction.UP);
        final String s2 = dump(grid);
        assertEquals("XXX4\nXXX2\nXXXX\nXXXX\n", s2);
        final Tile t1 = grid.getTile(0, 3);
        assertEquals(blockingTile.value, t1.value);
        final Tile t2 = grid.getTile(1, 3);
        assertEquals(originalTile.value, t2.value);
    }

    @Test
    public void playfieldCanMoveAllTiles() throws IOException {
        final Playfield grid = new Playfield(4);
        final Tile originalTile = new Tile(3, 3, 2);
        grid.addTile(originalTile);
        final Tile blockingTile = new Tile(0, 3, 4);
        grid.addTile(blockingTile);

        final String s1 = dump(grid);
        assertEquals("XXX4\nXXXX\nXXXX\nXXX2\n", s1);

        // move the whole playfield
        grid.move(Playfield.Direction.UP);

        final String s2 = dump(grid);
        assertEquals("XXX4\nXXX2\nXXXX\nXXXX\n", s2);
        final Tile t1 = grid.getTile(0, 3);
        assertEquals(blockingTile.value, t1.value);
        final Tile t2 = grid.getTile(1, 3);
        assertEquals(originalTile.value, t2.value);
    }

    @Test
    public void playfieldCanNotMoveATileOutsideOfBounds() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(0, 0, 2));
        grid.addTile(new Tile(3, 3, 2));

        // try to move the tile outside of the playfield
        grid.moveTile(0, 0, Playfield.Direction.UP);
        assertEquals(2, grid.getTile(0, 0).value);
        grid.moveTile(0, 0, Playfield.Direction.LEFT);
        assertEquals(2, grid.getTile(0, 0).value);

        grid.moveTile(3, 3, Playfield.Direction.DOWN);
        assertEquals(2, grid.getTile(3, 3).value);
        grid.moveTile(3, 3, Playfield.Direction.RIGHT);
        assertEquals(2, grid.getTile(3, 3).value);
    }

    @Test
    public void playfieldCanMoveATileOnAnotherTileWithSameValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(0, 3, 2));
        grid.addTile(new Tile(3, 3, 2));
        grid.moveTile(3, 3, Playfield.Direction.UP);

        final Tile t = grid.getTile(0, 3);
        assertEquals(4, t.value);
        assertTrue(grid.getTile(3, 3) == null);
    }

    @Test
    public void playfieldCanNotMoveATileOnAnotherTileWithDifferentValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(2, 3, 4));
        grid.addTile(new Tile(3, 3, 2));
        grid.moveTile(3, 3, Playfield.Direction.UP);

        final Tile t1 = grid.getTile(2, 3);
        assertEquals(4, t1.value);

        final Tile t2 = grid.getTile(3, 3);
        assertEquals(2, t2.value);
    }

    @Test
    public void canPrintAPlayfield() throws IOException {
        final Playfield p = new Playfield(4);
        p.addTile(new Tile(1, 2, 8));
        final String s = dump(p);
        assertEquals("XXXX\nXX8X\nXXXX\nXXXX\n", s);
    }

    private String dump(Playfield grid) throws IOException {
        final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        grid.print(new PrintWriter(bos1));
        return bos1.toString();
    }
}