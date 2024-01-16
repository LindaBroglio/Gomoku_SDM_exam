package it.units.gomoku;

import it.units.gomoku.utilities.Color;

public class Node {
    private Color color;
    public Node() { this.color = Color.EMPTY; }
    public void setColor(Color color) { this.color = color; }
    public Color getColor() {
        return this.color;
    }
}
