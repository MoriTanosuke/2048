package de.kopis.twothousand;

import java.io.IOException;
import java.io.OutputStream;

public interface DisplayPlayfield {
    // oops, created another functional interface by accident... now IDEA is marking anonymous inner classes for refactoring :)
    void print(OutputStream out, Playfield pf, PlayfieldControl control) throws IOException;
}
