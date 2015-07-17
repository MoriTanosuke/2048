package de.kopis.twothousand;

/**
 * Implement this interface if you want to add a new control scheme.
 */
public interface PlayfieldControl {
    /**
     * Get player input and move the {@link Playfield}.
     *
     * @return <code>true</code> if {@link Playfield} was moved, else <code>false</code>
     */
    boolean run(Playfield playfield);

    /**
     * Returns a text representation of the current control scheme. Useful to display somewhere to the player.
     *
     * @return
     */
    String getDescription();
}
