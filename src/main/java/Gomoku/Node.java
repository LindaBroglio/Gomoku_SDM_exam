package Gomoku;

public class Node {
    private Color color;
    private int x;
    private int y;
    private Node east_neighbour;
    private Node south_neighbour;
    private Node south_east_neighbour;

    // Constructor
    public Node(int x, int y) {
        this.color = Color.EMPTY;
        this.x = x;
        this.y = y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean has_same_east_neighbour(){
        return this.color == east_neighbour.color;
    }
    public boolean has_same_south_neighbour(){
        return this.color == south_neighbour.color;
    }
    public boolean has_same_south_east_neighbour(){
        return this.color == south_east_neighbour.color;
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

    // Getter and Setter methods for color, x_coordinate, and y_coordinate
    // ...
}
