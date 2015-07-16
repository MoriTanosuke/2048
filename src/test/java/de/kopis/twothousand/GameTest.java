package de.kopis.twothousand;

import junit.framework.TestCase;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

public class GameTest extends TestCase {

    @Test
    public void testASpecificState() throws IOException {
        Playfield p = new Playfield(4);

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
        p.print(new PrintWriter(System.out));
        p.move(Playfield.Direction.RIGHT);

        // X X 2 16
        // X X X 4
        // X X X 4
        // X X X 2
        System.out.println("RIGHT");
        p.print(new PrintWriter(System.out));

        // X X X X
        // X X X 16
        // X X X 8
        // X X 2 2
        p.move(Playfield.Direction.DOWN);
        System.out.println("DOWN");
        p.print(new PrintWriter(System.out));
    }


    @Test
    public void testASpecificState2() throws IOException {
        Playfield p = new Playfield(4);

        p.addTile(new Tile(0, 3, 2));
        p.addTile(new Tile(1, 3, 2));
        p.addTile(new Tile(2, 3, 2));
        p.addTile(new Tile(3, 3, 2));

        // X X X 2
        // X X X 2
        // X X X 2
        // X X X 2
        System.out.println();
        p.print(new PrintWriter(System.out));
        p.move(Playfield.Direction.DOWN);

        // X X X X
        // X X X X
        // X X X 4
        // X X X 4
        System.out.println("DOWN");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        p.print(new PrintWriter(bos));
        System.out.println(bos);

        assertEquals("   X   X   X   X\n   X   X   X   X\n   X   X   X   4\n   X   X   X   4\n", bos.toString());
    }
}