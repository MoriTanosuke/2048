package de.kopis.twothousand;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class PlayfieldTest {
    @Test
    public void emptyGridHasAvailableTilesUntilFull() {
        final int size = 4;

        final Playfield grid = new Playfield(size);
        assertTrue(grid.hasAvailableSlots());
        grid.addRandomTile();
        assertTrue(grid.hasAvailableSlots());

        // fill the whole grid with random tiles
        for (int i = 1; i <= (size * size) - 1; i++) {
            grid.addRandomTile();
            assertTrue(grid.hasAvailableSlots());
        }
        grid.addRandomTile();
        assertFalse(grid.hasAvailableSlots());
    }
}