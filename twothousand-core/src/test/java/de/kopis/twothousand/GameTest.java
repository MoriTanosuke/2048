package de.kopis.twothousand;

import de.kopis.twothousand.controls.PlayfieldControl;
import de.kopis.twothousand.controls.PlayfieldControlParse;
import de.kopis.twothousand.displays.AsciiPlayfield;
import de.kopis.twothousand.models.Direction;
import de.kopis.twothousand.models.Playfield;
import de.kopis.twothousand.models.Tile;
import de.kopis.twothousand.scores.ScoreCalculator;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class GameTest {

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
    public void testASpecificState() throws IOException {
        final AsciiPlayfield ascii = new AsciiPlayfield(calculator);
        final Playfield p = new Playfield(4);

        // initial state
        p.addTile(new Tile(0, 1, 2));
        p.addTile(new Tile(0, 2, 8));
        p.addTile(new Tile(0, 3, 8));
        p.addTile(new Tile(1, 2, 2));
        p.addTile(new Tile(1, 3, 2));
        p.addTile(new Tile(2, 3, 4));
        p.addTile(new Tile(3, 3, 2));

        // X 2 8 8
        // X X 2 2
        // X X X 4
        // X X X 2
        final ByteArrayOutputStream bos1 = new ByteArrayOutputStream();
        ascii.print(bos1, p, controls);
        assertEquals("   X   2   8   8\n   X   X   2   2\n   X   X   X   4\n   X   X   X   2\n\n\n", bos1.toString());

        p.move(Direction.RIGHT);
        // X X 2 16
        // X X X 4
        // X X X 4
        // X X X 2
        final ByteArrayOutputStream bos2 = new ByteArrayOutputStream();
        ascii.print(bos2, p, controls);
        assertEquals("   X   X   2  16\n   X   X   X   4\n   X   X   X   4\n   X   X   X   2\n\n\n", bos2.toString());

        p.move(Direction.DOWN);
        // X X X X
        // X X X 16
        // X X X 8
        // X X 2 2
        final ByteArrayOutputStream bos3 = new ByteArrayOutputStream();
        ascii.print(bos3, p, controls);
        assertEquals("   X   X   X   X\n   X   X   X  16\n   X   X   X   8\n   X   X   2   2\n\n\n", bos3.toString());
    }


    @Test
    public void testASpecificState2() throws IOException {
        final AsciiPlayfield ascii = new AsciiPlayfield(calculator);
        final Playfield p = new Playfield(4);

        p.addTile(new Tile(0, 3, 2));
        p.addTile(new Tile(1, 3, 2));
        p.addTile(new Tile(2, 3, 2));
        p.addTile(new Tile(3, 3, 2));

        // X X X 2
        // X X X 2
        // X X X 2
        // X X X 2
        System.out.println();
        ascii.print(System.out, p, controls);
        p.move(Direction.DOWN);

        // X X X X
        // X X X X
        // X X X 4
        // X X X 4
        System.out.println("DOWN");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ascii.print(bos, p, controls);
        System.out.println(bos);

        assertEquals("   X   X   X   X\n   X   X   X   X\n   X   X   X   4\n   X   X   X   4\n\n\n", bos.toString());
    }
}