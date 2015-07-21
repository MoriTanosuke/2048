package de.kopis.twothousand;

import java.io.IOException;

/**
 * Implement this interface if you want to add a new control scheme.
 */
public interface PlayfieldControl {
    /**
     * @see #run(PlayfieldControlParse, Playfield)
     */
    boolean run(Playfield playfield);

    /**
     * Get player input using the provided "parseMethod" and move the {@link Playfield}.
     *
     * @param parseMethod  method to get a valid {@link Direction} from the player
     * @param playfield {@link Playfield} to control
     * @return <code>true</code> if {@link Playfield} was moved, else <code>false</code>
     */
    boolean run(PlayfieldControlParse parseMethod, Playfield playfield);

    /**
     * Returns a text representation of the current control scheme. Useful to display somewhere to the player.
     *
     * @return
     */
    String getDescription();
}
