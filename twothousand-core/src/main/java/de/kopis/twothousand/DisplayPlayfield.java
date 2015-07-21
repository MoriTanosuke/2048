package de.kopis.twothousand;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by carsten on 21.07.15.
 */
public interface DisplayPlayfield {
    void print(OutputStream out, Playfield pf, PlayfieldControl control) throws IOException;
}
