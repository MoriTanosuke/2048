package de.kopis.twothousand.models;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Playfield {
    private static final Logger logger = LoggerFactory.getLogger(Playfield.class);

    private final int maxX;
    private final int maxY;
    private List<Tile> tiles = new ArrayList<Tile>();

    public Playfield(int size) {
        this.maxX = size;
        this.maxY = size;
    }

    public void addRandomTile() {
        final Random r = new Random(System.nanoTime());

        int x = 0;
        int y = 0;
        do {
            x = 1 + r.nextInt(getMaxX() - 1);
            y = 1 + r.nextInt(getMaxY() - 1);

            //TODO make the tile appear at the border
            // 50-50 chance to place this top or bottom
            int border = 0;
            if (r.nextInt(100) > 50) {
                border = 1;
            } else {
                border = 0;
            }
            // 50-50 chance to place this on x/y border
            if (r.nextInt(100) > 50) {
                x = (getMaxX() - 1) * border;
            } else {
                y = (getMaxY() - 1) * border;
            }
        } while (getTile(x, y) != null);

        logger.info(String.format("Adding random tile to %d,%d", x, y));
        addTile(new Tile(x, y, getStartValue()));
    }

    private int getStartValue() {
        final Random r = new Random(System.nanoTime());
        if (r.nextDouble() > 0.9) {
            return 4;
        }
        return 2;
    }

    public boolean hasAvailableSlots() {
        logger.info(String.format("Placed tiles: %d, Max: %d", tiles.size(), getMaxX() * getMaxY()));
        return tiles.size() <= getMaxX() * getMaxY();
    }

    /**
     * Moves all {@link Tile}s on the Playfield.
     */
    public void move(final Direction direction) {
        logger.info("Moving playfield {}", direction);

        if (direction == null) {
            // don't move
            return;
        }

        // reset all moved tiles
        for (Tile t : tiles) {
            t.setMoved(false);
        }

        // move all fields, start point depends on direction
        switch (direction) {
            case DOWN:
                for (int x = getMaxX(); x >= 0; x--) {
                    for (int y = 0; y < getMaxY(); y++) {
                        final Tile originalTile = getTile(x, y);
                        do {
                            moveTile(x, y, direction);
                        } while (getTile(x, y) != null && originalTile != getTile(x, y));
                    }
                }
                break;
            case UP:
                for (int x = 0; x < getMaxX(); x++) {
                    for (int y = 0; y < getMaxY(); y++) {
                        final Tile originalTile = getTile(x, y);
                        do {
                            moveTile(x, y, direction);
                        } while (getTile(x, y) != null && originalTile != getTile(x, y));
                    }
                }
                break;
            case RIGHT:
                for (int x = getMaxX(); x >= 0; x--) {
                    for (int y = getMaxY(); y >= 0; y--) {
                        final Tile originalTile = getTile(x, y);
                        do {
                            moveTile(x, y, direction);
                        } while (getTile(x, y) != null && originalTile != getTile(x, y));
                    }
                }
                break;
            case LEFT:
                for (int x = 0; x < getMaxX(); x++) {
                    for (int y = getMaxY(); y >= 0; y--) {
                        final Tile originalTile = getTile(x, y);
                        do {
                            moveTile(x, y, direction);
                        } while (getTile(x, y) != null && originalTile != getTile(x, y));
                    }
                }
                break;
        }

        //TODO always add a new random tile after a move - but is this the right place?
    }

    /**
     * Move one {@link Tile} on the Playfield in a {@link Direction}.
     *
     * @param tileX     xposition on Playfield
     * @param tileY     yposition on Playfield
     * @param direction one of {@link Direction#UP}, {@link Direction#DOWN}, {@link Direction#LEFT}, {@link Direction#RIGHT}
     */
    private void moveTile(final int tileX, final int tileY, final Direction direction) {
        int x = tileX;
        int y = tileY;

        Tile tile = getTile(x, y);

        if (tile == null || tile.isMoved()) {
            // no tile found or tile already moved, nothing to move
            return;
        }

        Tile t = null;
        do {
            // tile matched, now move to direction
            switch (direction) {
                case UP:
                    t = move(x, y, x - 1, y);
                    break;
                case DOWN:
                    t = move(x, y, x + 1, y);
                    break;
                case LEFT:
                    t = move(x, y, x, y - 1);
                    break;
                case RIGHT:
                    t = move(x, y, x, y + 1);
                    break;
            }

            if (t == null) break;
            x = t.x;
            y = t.y;
        } while (true); // run until the inner break stops us

    }

    private Tile move(final int x, final int y, final int newX, final int newY) {
        if (newX < 0 || newY < 0 || newX >= getMaxX() || newY >= getMaxY()) {
            Tile t = getTile(x, y);
            t.setMoved(true);
            return null;
        }

        logger.info("Moving tile {},{} to {},{}", x, y, newX, newY);

        final Tile thisTile = getTile(x, y);
        logger.debug("thisTile {}", thisTile);

        if (thisTile.isMoved()) {
            return null;
        }

        final Tile otherTile = getTile(newX, newY);
        Tile newTile = null;
        if (otherTile != null) {
            logger.debug("otherTile {}", otherTile);
            if (thisTile != null && thisTile.value == otherTile.value) {
                final int newValue = thisTile.value + otherTile.value;
                newTile = new Tile(newX, newY, newValue);
                newTile.setMoved(true);
                logger.info("Replacing tile {}", otherTile, newTile);
                removeTile(x, y);
                addTile(newTile);
            } else {
                logger.info("Can not move tile {} to {}", thisTile, otherTile);
            }
        } else {
            newTile = new Tile(newX, newY, thisTile != null ? thisTile.value : 0);
            removeTile(x, y);
            addTile(newTile);
        }

        return newTile;
    }

    public void addTile(Tile tile) {
        if (tile.x < 0) throw new IllegalArgumentException("'x' too small");
        if (tile.y < 0) throw new IllegalArgumentException("'x' too small");

        if (tile.x >= getMaxX()) throw new IllegalArgumentException("'x' too large");
        if (tile.y >= getMaxY()) throw new IllegalArgumentException("'y' too large");

        logger.info("Adding tile {}", tile);
        Tile oldTile = getTile(tile.x, tile.y);
        if (oldTile != null) {
            logger.debug("There is already a tile {}", oldTile);
            tiles.remove(oldTile);
        }
        tiles.add(tile);
    }

    private Tile removeTile(final int x, final int y) {
        for (Tile t : tiles.toArray(new Tile[0])) {
            if (t.x == x && t.y == y) {
                logger.debug("Removing tile at {},{}", t.x, t.y);
                tiles.remove(t);
                return t;
            }
            /*
            else {
                logger.debug("Not removing tile, because {},{}!={},{}", x, y, t.x, t.y);
            }
            */
        }
        return null;
    }

    public List<Tile> getRow(int row) {
        final List<Tile> tiles = new ArrayList<>();
        for (int y = 0; y < maxY; y++) {
            tiles.add(getTile(row, y));
        }
        return tiles;
    }

    public Tile getTile(int x, int y) {
        //TODO maybe use Optional<Tile> as return value?
        for (Tile t : tiles) {
            if (t.x == x && t.y == y) {
                logger.debug("Returning tile {}", t);
                return t;
            }
        }
        return null;
    }

    public int getMaxX() {
        return maxX;
    }

    public int getMaxY() {
        return maxY;
    }
}
