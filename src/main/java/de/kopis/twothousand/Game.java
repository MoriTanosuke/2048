package de.kopis.twothousand;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Game {
    private final Playfield playfield;
    private final AsciiPlayfield display;

    public static void main(String... args) throws IOException, InterruptedException {
        // TODO configure the playfield size via args later
        new Game(new AsciiPlayfield(), 4).start();
    }

    public Game(final AsciiPlayfield display, int size) {
        this.display = display;
        playfield = new Playfield(size);
        playfield.addRandomTile();
    }

    public void start() throws IOException, InterruptedException {
        int line = '\0';
        // go into main game loop
        while ((line = System.in.read()) != 'q') {
            printGame(playfield, System.out);
            //  parse line and move playfield
            final Playfield.Direction direction = parse(line);
            if (direction != null) {
                playfield.move(direction);
                playfield.addRandomTile();
            } else {
                Thread.sleep(100);
            }
        }
    }

    private void printGame(final Playfield playfield, final OutputStream out) throws IOException {
        final PrintWriter pw = new PrintWriter(out);
        display.print(pw, playfield);
        pw.println("k = UP, j = DOWN, h = LEFT, l = RIGHT, q = QUIT");
        pw.flush();
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
