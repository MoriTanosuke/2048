package de.kopis.twothousand;

import de.kopis.twothousand.controls.KeyboardPlayfieldControl;
import de.kopis.twothousand.controls.PlayfieldControl;
import de.kopis.twothousand.displays.AsciiPlayfield;
import de.kopis.twothousand.models.Playfield;
import de.kopis.twothousand.scores.SimpleScoreCalculator;

import java.io.IOException;

public class Game {
    private final Playfield playfield;
    private final AsciiPlayfield display;
    private final PlayfieldControl controls;

    public static void main(String... args) throws IOException, InterruptedException {
        // TODO configure the playfield size via args later
        new Game(new AsciiPlayfield(new SimpleScoreCalculator()), 4).start();
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
            // add new random tile BEFORE printing the playfield!
            //TODO this is a bug, random tile should be always added after movement
            playfield.addRandomTile();
            display.print(System.out, playfield, controls);
        }
    }
}
