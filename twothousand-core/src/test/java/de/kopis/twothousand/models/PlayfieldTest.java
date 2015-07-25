package de.kopis.twothousand.models;

import de.kopis.twothousand.controls.PlayfieldControl;
import de.kopis.twothousand.controls.PlayfieldControlParse;
import de.kopis.twothousand.displays.AsciiPlayfield;
import de.kopis.twothousand.scores.ScoreCalculator;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.joining;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayfieldTest {
    private ScoreCalculator calculator = new ScoreCalculator() {
        @Override
        public int calculateScore(Playfield playfield) {
            return 0;
        }

        @Override
        public boolean hasWon(Playfield playfield) {
            return false;
        }

        @Override
        public String getScoreDescription(Playfield playfield) {
            return "";
        }
    };

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
    public void playfieldCanReturnARow() {
        final Playfield grid = new Playfield(4);
        // 2XXX
        // X2XX
        // XX2X
        // XXX2
        final Tile originalTile = new Tile(0, 0, 2);
        grid.addTile(originalTile);
        final Tile originalTile2 = new Tile(1, 1, 2);
        grid.addTile(originalTile2);
        final Tile originalTile3 = new Tile(2, 2, 2);
        grid.addTile(originalTile3);
        final Tile originalTile4 = new Tile(3, 3, 2);
        grid.addTile(originalTile4);

        // get 1st row - 2XXX
        final List<Tile> tiles1 = grid.getRow(0);
        final String s1 = tiles1.stream().map(x -> {
            return x != null ? Integer.toString(x.value) : "X";
        }).collect(joining(""));
        assertEquals("2XXX", s1);
        // get 2nd row - X2XX
        final List<Tile> tiles2 = grid.getRow(1);
        final String s2 = tiles2.stream().map(x -> {
            return x != null ? Integer.toString(x.value) : "X";
        }).collect(joining(""));
        assertEquals("X2XX", s2);
        // get 3rd row - XX2X
        final List<Tile> tiles3 = grid.getRow(2);
        final String s3 = tiles3.stream().map(x -> {
            return x != null ? Integer.toString(x.value) : "X";
        }).collect(joining(""));
        assertEquals("XX2X", s3);
        // get 3rd row - XXX2
        final List<Tile> tiles4 = grid.getRow(3);
        final String s4 = tiles4.stream().map(x -> {
            return x != null ? Integer.toString(x.value) : "X";
        }).collect(joining(""));
        assertEquals("XXX2", s4);
    }

    @Test
    public void playfieldCanMoveATileThroughEmptySpaces() throws IOException {
        final Playfield grid = new Playfield(4);
        final Tile originalTile = new Tile(3, 3, 2);
        grid.addTile(originalTile);

        final String s1 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   2\n\n\n", s1);

        grid.move(Direction.UP);
        final String s2 = dump(grid);
        assertEquals("   X   X   X   2\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n\n\n", s2);
        final Tile t1 = grid.getTile(0, 3);
        assertEquals(originalTile.value, t1.value);
        assertTrue(grid.getTile(3, 3) == null);

        grid.move(Direction.DOWN);
        final String s3 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   2\n\n\n", s3);
        final Tile t2 = grid.getTile(3, 3);
        assertEquals(originalTile.value, t2.value);
        assertTrue(grid.getTile(2, 3) == null);

        grid.move(Direction.LEFT);
        final String s4 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   2   X   X   X\n\n\n", s4);
        final Tile t3 = grid.getTile(3, 0);
        assertEquals(originalTile.value, t3.value);
        assertTrue(grid.getTile(3, 3) == null);

        grid.move(Direction.RIGHT);
        final String s5 = dump(grid);
        assertEquals("   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n" +
                "   X   X   X   2\n\n\n", s5);
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
                "   X   X   X   2\n\n\n", s1);

        grid.move(Direction.UP);
        final String s2 = dump(grid);
        assertEquals("   X   X   X   4\n" +
                "   X   X   X   2\n" +
                "   X   X   X   X\n" +
                "   X   X   X   X\n\n\n", s2);
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
        assertEquals("   X   X   X   4\n   X   X   X   X\n   X   X   X   X\n   X   X   X   2\n\n\n", s1);

        // move the whole playfield
        grid.move(Direction.UP);

        final String s2 = dump(grid);
        assertEquals("   X   X   X   4\n   X   X   X   2\n   X   X   X   X\n   X   X   X   X\n\n\n", s2);
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
        new AsciiPlayfield(calculator).print(bos1, grid, controls);
        return bos1.toString();
    }
}