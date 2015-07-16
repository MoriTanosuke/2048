package de.kopis.twothousand;

import java.io.IOException;
import java.io.PrintWriter;

public class Game {
    private final Playfield playfield;

    public static void main(String... args) throws IOException, InterruptedException {
        // TODO configure the playfield size via args later
        new Game(4).start();
    }

    public Game(int size) {
        playfield = new Playfield(size);
        playfield.addRandomTile();
    }

    public void start() throws IOException, InterruptedException {
        // get the console for user IO
        final PrintWriter out = new PrintWriter(System.out);

        int line = '\0';
        // go into main game loop
        while ((line = System.in.read()) != 'q') {
            //  parse line and move playfield
            final Playfield.Direction direction = parse(line);
            if (direction != null) {
                playfield.move(direction);
                playfield.addRandomTile();
            } else {
                Thread.sleep(100);
            }
            playfield.print(out);
            out.println("k = UP, j = DOWN, h = LEFT, l = RIGHT, q = QUIT");
            out.flush();
        }
    }

    private Playfield.Direction parse(int line) {
        switch (line) {
            case 'k':
                return Playfield.Direction.UP;
            case 'j':
                return Playfield.Direction.DOWN;
            case 'h':
                return Playfield.Direction.LEFT;
            case 'l':
                return Playfield.Direction.RIGHT;
            default:
                break;
        }
        return null;
    }
}
