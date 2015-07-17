package de.kopis.twothousand;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import static org.junit.Assert.assertEquals;

public class GameTest {

    private PlayfieldControl controls = new PlayfieldControl() {
        @Override
        public boolean run(Playfield playfield) {
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
        final AsciiPlayfield ascii = new AsciiPlayfield();
        final Playfield p = new Playfield(4);

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
        System.out.println();
        ascii.print(new PrintWriter(System.out), p, controls);
        p.move(Direction.RIGHT);

        // X X 2 16
        // X X X 4
        // X X X 4
        // X X X 2
        System.out.println("RIGHT");
        ascii.print(new PrintWriter(System.out), p, controls);

        // X X X X
        // X X X 16
        // X X X 8
        // X X 2 2
        p.move(Direction.DOWN);
        System.out.println("DOWN");
        ascii.print(new PrintWriter(System.out), p, controls);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ascii.print(new PrintWriter(bos), p, controls);
        System.out.println(bos);
        assertEquals("   X   X   X   X\n   X   X   X  16\n   X   X   X   8\n   X   X   2   2\n\n", bos.toString());
    }


    @Test
    public void testASpecificState2() throws IOException {
        final AsciiPlayfield ascii = new AsciiPlayfield();
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
        ascii.print(new PrintWriter(System.out), p, controls);
        p.move(Direction.DOWN);

        // X X X X
        // X X X X
        // X X X 4
        // X X X 4
        System.out.println("DOWN");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ascii.print(new PrintWriter(bos), p, controls);
        System.out.println(bos);

        assertEquals("   X   X   X   X\n   X   X   X   X\n   X   X   X   4\n   X   X   X   4\n\n", bos.toString());
    }
}