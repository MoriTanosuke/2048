package de.kopis.twothousand.exceptions;

public class TileCombinationException extends Exception {
    public TileCombinationException() {
        super("These tiles can not be combined");
    }
}
