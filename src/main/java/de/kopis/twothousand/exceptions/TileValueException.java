package de.kopis.twothousand.exceptions;

public class TileValueException extends RuntimeException {
    public TileValueException(final int tileValue) {
        super("Tile value is broken: " + tileValue);
    }
}
