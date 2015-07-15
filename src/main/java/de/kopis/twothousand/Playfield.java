package de.kopis.twothousand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playfield {
    private final int maxX;
    private final int maxY;
    private List<Tile> tiles = new ArrayList<Tile>();

    public Playfield(int size) {
        this.maxX = size;
        this.maxY = size;
    }

    public void addRandomTile() {
        Random r = new Random(System.nanoTime());
        int x = 1 + r.nextInt(maxX - 1);
        int y = 1 + r.nextInt(maxY - 1);
        addTile(new Tile(x, y, 2));
    }

    public boolean hasAvailableSlots() {
        System.out.println(tiles.size() + " <? " + maxX * maxY);
        return tiles.size() <= maxX * maxY;
    }

    private void addTile(Tile tile) {
        tiles.add(tile);
    }
}
