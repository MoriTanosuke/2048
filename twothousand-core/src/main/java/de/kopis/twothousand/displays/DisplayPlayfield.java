package de.kopis.twothousand.displays;

import de.kopis.twothousand.controls.PlayfieldControl;
import de.kopis.twothousand.models.Playfield;

import java.io.IOException;
import java.io.OutputStream;

public interface DisplayPlayfield {
    void print(OutputStream out, Playfield pf, PlayfieldControl control) throws IOException;
}
