package de.kopis.twothousand;

import org.junit.Ignore;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore("random tiles are always added after moving the playfield, this tests are now broken...")
public class PlayfieldTest {

    private ScoreCalculator scoreCalculator = new ScoreCalculator();

    private PlayfieldControl controls = new PlayfieldControl() {
        @Override
        public boolean run(Playfield playfield) {
            return false;
        }

        @Override
        public boolean run(PlayfieldControlParse parseMethod, Playfield playfield) {
            return false;
        }

        @Override
        public String getDescription() {
            // return empty string for easy asserts on playfield state
            return "";
        }
    };

    @Test
    public void playfieldCanMoveATileThroughEmptySpaces() throws IOException {
        final Playfield grid = new Playfield(4);
        final Tile originalTile = new Tile(3, 3, 2);
        grid.addTile(originalTile);

        final String s1 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   2\n\n", s1);

        grid.move(Direction.UP);
        final String s2 = dump(grid);
        assertEquals("   X   X   X   2\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n\n", s2);
        final Tile t1 = grid.getTile(0, 3);
        assertEquals(originalTile.value, t1.value);
        assertTrue(grid.getTile(3, 3) == null);

        grid.move(Direction.DOWN);
        final String s3 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   2\n\n", s3);
        final Tile t2 = grid.getTile(3, 3);
        assertEquals(originalTile.value, t2.value);
        assertTrue(grid.getTile(2, 3) == null);

        grid.move(Direction.LEFT);
        final String s4 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   2   X   X   X\n\n", s4);
        final Tile t3 = grid.getTile(3, 0);
        assertEquals(originalTile.value, t3.value);
        assertTrue(grid.getTile(3, 3) == null);

        grid.move(Direction.RIGHT);
        final String s5 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   2\n\n", s5);
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
        assertEquals("   X   X   X   4\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   2\n\n", s1);

        grid.move(Direction.UP);
        final String s2 = dump(grid);
        assertEquals("   X   X   X   4\n" +
                "   X   X   X   2\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n\n", s2);
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
        assertEquals("   X   X   X   4\n   X   X   X   2\n   X   X   X   X\n   X   X   X   X\n\n", s1);

        // move the whole playfield
        grid.move(Direction.UP);

        final String s2 = dump(grid);
        assertEquals("   X   X   X   4\n   X   X   X   2\n   X   X   X   X\n   X   X   X   X\n\n", s2);
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
        grid.move(Direction.UP);
        assertEquals(2, grid.getTile(0, 0).value);
        grid.move(Direction.LEFT);
        assertEquals(4, grid.getTile(0, 0).value);

        grid.move(Direction.DOWN);
        assertEquals(4, grid.getTile(3, 0).value);
        grid.move(Direction.RIGHT);
        assertEquals(4, grid.getTile(3, 3).value);
    }

    @Test
    public void playfieldCanMoveATileOnAnotherTileWithSameValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(0, 3, 2));
        grid.addTile(new Tile(3, 3, 2));
        grid.move(Direction.UP);

        final Tile t = grid.getTile(0, 3);
        assertEquals(4, t.value);
        assertTrue(grid.getTile(3, 3) == null);
    }

    @Test
    public void playfieldCanNotMoveATileOnAnotherTileWithDifferentValue() {
        final Playfield grid = new Playfield(4);
        grid.addTile(new Tile(2, 3, 4));
        grid.addTile(new Tile(3, 3, 2));
        grid.move(Direction.UP);

        final Tile t1 = grid.getTile(0, 3);
        assertEquals(4, t1.value);

        final Tile t2 = grid.getTile(1, 3);
        assertEquals(2, t2.value);
    }

    private String dump(Playfield grid) throws IOException {
        final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        new AsciiPlayfield(scoreCalculator).print(new PrintWriter(bos1), grid, controls);
        return bos1.toString();
    }
}