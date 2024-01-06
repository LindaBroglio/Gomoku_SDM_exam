package Gomoku;

public class Node {
    private Color color;
    private int x_coordinate;
    private int y_coordinate;

    // Constructor
    public Node(int x_coordinate, int y_coordinate) {
        this.color = Color.EMPTY;
        this.x_coordinate = x_coordinate;
        this.y_coordinate = y_coordinate;
    }

    // Getter and Setter methods for color, x_coordinate, and y_coordinate
    // ...
}
