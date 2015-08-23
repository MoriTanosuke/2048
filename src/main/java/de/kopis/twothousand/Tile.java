package de.kopis.twothousand;

import de.kopis.twothousand.exceptions.TileCombinationException;
import de.kopis.twothousand.exceptions.TileValueException;

public class Tile {
    private final int value;
    private int age = 0;

    public Tile(int value) {
        if (value % 2 != 0) {
            throw new TileValueException(value);
        }
        this.value = value;
    }

    public Tile combine(Tile other) throws TileCombinationException {
        if (this == other) {
            throw new TileCombinationException();
        }
        if (!this.canBeCombined() || !other.canBeCombined()) {
            throw new TileCombinationException();
        }
        if (this.getValue() != other.getValue()) {
            throw new TileCombinationException();
        }
        return new Tile(this.getValue() + other.getValue());
    }

    public void turn() {
        age += 1;
    }

    public boolean canBeCombined() {
        return age > 0;
    }

    public int getValue() {
        return value;
    }
}
