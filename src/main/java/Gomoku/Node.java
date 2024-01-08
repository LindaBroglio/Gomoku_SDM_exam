package Gomoku;

public class Node {
    private Color color;
    private final int x;
    private final int y;

    // Constructor
    public Node(int x, int y) {
        this.color = Color.EMPTY;
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return this.color;
    }

    public int getX_coordinate() {
        return this.x;
    }

    public int getY_coordinate() {
        return this.y;
    }
}
