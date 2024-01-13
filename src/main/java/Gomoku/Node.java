package Gomoku;

import Gomoku.utilities.Color;

public class Node {
    private Color color;

    public Node() { this.color = Color.EMPTY; }

    public void setColor(Color color) { this.color = color; }

    public Color getColor() {
        return this.color;
    }
}
