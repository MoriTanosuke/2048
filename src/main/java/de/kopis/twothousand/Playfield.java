package de.kopis.twothousand;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;

public class Playfield {
    private static final Logger logger = Logger.getLogger(Playfield.class.getName());

    public enum Direction {
        UP,
        DOWN,
        LEFT,
        RIGHT
    }

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
        logger.info(String.format("Adding random tile to %d,%d", x, y));
        addTile(new Tile(x, y, 2));
    }

    public boolean hasAvailableSlots() {
        logger.info(String.format("Placed tiles: %d, Max: %d", tiles.size(), maxX * maxY));
        return tiles.size() <= maxX * maxY;
    }

    public void addTile(Tile tile) {
        if (tile.x < 0) throw new IllegalArgumentException("'x' too small");
        if (tile.y < 0) throw new IllegalArgumentException("'x' too small");

        if (tile.x >= maxX) throw new IllegalArgumentException("'x' too large");
        if (tile.y >= maxY) throw new IllegalArgumentException("'y' too large");

        logger.info(String.format("Adding tile value=%d at %d,%d", tile.value, tile.x, tile.y));
        tiles.add(tile);
    }

    public void moveTile(int x, int y, Direction direction) {
        Tile tile = getTile(x, y);

        if (tile == null) {
            // no tile found, nothing to move
            return;
        }

        // tile matched, now move to direction
        switch (direction) {
            case UP:
                move(x, y, x - 1, y);
                break;
            case DOWN:
                move(x, y, x + 1, y);
                break;
            case LEFT:
                move(x, y, x, y - 1);
                break;
            case RIGHT:
                move(x, y, x, y + 1);
                break;
        }
    }

    private void move(int x, int y, int newX, int newY) {
        if (newX < 0) return;
        if (newY < 0) return;
        if (newX > maxX) return;
        if (newY > maxY) return;

        Tile thisTile = getTile(x, y);
        int newValue = thisTile != null ? thisTile.value : 0;

        Tile otherTile = getTile(newX, newY);
        if (otherTile != null) {
            if (thisTile.value == otherTile.value) {
                newValue = thisTile.value + otherTile.value;
                removeTile(x, y);
                addTile(new Tile(newX, newY, newValue));
            }
        } else {
            removeTile(x, y);
            addTile(new Tile(newX, newY, newValue));
        }
    }

    private Tile removeTile(int x, int y) {
        for (Tile t : tiles.toArray(new Tile[0])) {
            if (t.x == x && t.y == y) {
                logger.info(String.format("Removing tile at %d,%d", t.x, t.y));
                tiles.remove(t);
                return t;
            }
        }
        return null;
    }

    public Tile getTile(int x, int y) {
        Tile tile = null;
        for (Tile t : tiles) {
            if (t.x == x && t.y == y) {
                tile = t;
            }
        }
        if (tile != null) {
            logger.info(String.format("Tile value=%d at %d,%d", tile.value, tile.x, tile.y));
        } else {
            logger.info(String.format("No tile found at %d,%d", x, y));
        }
        return tile;
    }

    /**
     * Prints the whole playfield using the given {@link Writer}.
     *
     * @param out {@link Writer} to use for printing. Is flushed after playfield is written.
     * @throws IOException
     */
    public void print(Writer out) throws IOException {
        for (int x = 0; x < maxX; x++) {
            for (int y = 0; y < maxY; y++) {
                Tile t = getTile(x, y);
                if (t == null) {
                    out.write("X");
                } else {
                    out.write(Integer.toString(t.value));
                }
            }
            out.write("\n");
        }
        out.flush();
    }
}
