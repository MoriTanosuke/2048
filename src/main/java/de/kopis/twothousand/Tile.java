package de.kopis.twothousand;


public class Tile {
    public final int x;
    public final int y;
    public final int value;
    private boolean moved = false;

    public Tile(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }

    @Override
    public String toString() {
        return "Tile{" +
                "x=" + x +
                ", y=" + y +
                ", value=" + value +
                ", moved=" + moved +
                '}';
    }
}
