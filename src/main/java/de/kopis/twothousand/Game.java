package de.kopis.twothousand;

import java.io.IOException;

public class Game {
    private final Playfield playfield;
    private final AsciiPlayfield display;
    private final PlayfieldControl controls;

    public static void main(String... args) throws IOException, InterruptedException {
        // TODO configure the playfield size via args later
        new Game(new AsciiPlayfield(new ScoreCalculator()), 4).start();
    }

    public Game(final AsciiPlayfield display, int size) {
        this.display = display;
        playfield = new Playfield(4);
        playfield.addRandomTile();
        controls = new KeyboardPlayfieldControl();
    }

    public void start() throws IOException, InterruptedException {
        display.print(System.out, playfield, controls);
        // go into main game loop
        while (controls.run(playfield)) {
            display.print(System.out, playfield, controls);
            playfield.addRandomTile();
        }
    }
}
