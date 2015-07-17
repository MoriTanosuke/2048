package de.kopis.twothousand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class KeyboardPlayfieldControl implements PlayfieldControl {
    private static final Logger logger = LoggerFactory.getLogger(KeyboardPlayfieldControl.class);

    @Override
    public boolean run(Playfield playfield) {
        // TODO get direction
        try {
            Direction direction = parse();
            if (direction == null) {
                // we want to quit
                return false;
            }
            playfield.move(direction);
        } catch (IOException e) {
            logger.error("Can not control playfield", e);
        }
        return true;
    }

    @Override
    public String getDescription() {
        return "w = UP, s = DOWN, a = LEFT, d = RIGHT, q (or any other key) = QUIT";
    }

    private Direction parse() throws IOException {
        int line = System.in.read();
        switch (line) {
            case 'w':
                return Direction.UP;
            case 's':
                return Direction.DOWN;
            case 'a':
                return Direction.LEFT;
            case 'd':
                return Direction.RIGHT;
            case 'q':
            default:
                return null;
        }
    }
}
