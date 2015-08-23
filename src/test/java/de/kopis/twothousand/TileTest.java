package de.kopis.twothousand;

import de.kopis.twothousand.exceptions.TileCombinationException;
import de.kopis.twothousand.exceptions.TileValueException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TileTest {
    @Test
    public void tilesCanBeCombined() throws TileCombinationException {
        Tile t1 = new Tile(2);
        Tile t2 = new Tile(2);
        t1.turn();
        t2.turn();
        Tile t3 = t1.combine(t2);
        assertEquals(4, t3.getValue());
    }

    @Test(expected = TileCombinationException.class)
    public void tilesCanNotBeCombinedIfAlreadyCombined() throws TileCombinationException {
        Tile t1 = new Tile(2);
        Tile t2 = new Tile(2);
        Tile t3 = t1.combine(t2);
        Tile t4 = new Tile(4);
        t3.combine(t4);
    }

    @Test(expected = TileCombinationException.class)
    public void tilesCanOnlyBeCombinedIfSameValue() throws TileCombinationException {
        Tile t1 = new Tile(2);
        Tile t2 = new Tile(4);
        t1.turn();
        t2.turn();
        t1.combine(t2);
    }

    @Test(expected = TileCombinationException.class)
    public void onlyDifferentTilesCanBeCombined() throws TileCombinationException {
        Tile t1 = new Tile(2);
        t1.turn();
        t1.combine(t1);
    }

    @Test(expected = TileValueException.class)
    public void tileValueHasToBeModulo2() {
        new Tile(3);
    }
}
