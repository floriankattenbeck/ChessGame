package main;

public class Vector2 {
    public int x, y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 rotate90(boolean clockwise) {
        return clockwise ? new Vector2(y, -x) : new Vector2(-y, x);
    }
}