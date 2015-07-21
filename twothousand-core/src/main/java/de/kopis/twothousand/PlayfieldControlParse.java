package de.kopis.twothousand;

import java.io.IOException;

/**
 * Functional interface to parse a {@link Direction} from player.
 */
public interface PlayfieldControlParse {
    Direction parse() throws IOException;
}
