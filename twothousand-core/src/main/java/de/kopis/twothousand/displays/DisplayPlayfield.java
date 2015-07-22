package de.kopis.twothousand.displays;

import de.kopis.twothousand.controls.PlayfieldControl;
import de.kopis.twothousand.models.Playfield;

import java.io.IOException;
import java.io.OutputStream;

public interface DisplayPlayfield {
    /**
     * Prints the whole playfield using the given {@link OutputStream}.
     *
     * @param out     {@link OutputStream} to use for printing. Is flushed after playfield is written
     * @param pf      {@link Playfield} to display
     * @param control {@link PlayfieldControl} to display to player
     * @throws IOException
     */
    void print(OutputStream out, Playfield pf, PlayfieldControl control) throws IOException;
}
