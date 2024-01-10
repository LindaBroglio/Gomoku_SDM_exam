package Gomoku;

public class Node {
    private Color color; // private because we want encapsulation

    public Node() { this.color = Color.EMPTY; }

    public void setColor(Color color) { this.color = color; }

    public Color getColor() {
        return this.color;
    }
}
