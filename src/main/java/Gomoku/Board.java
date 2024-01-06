package Gomoku;

public class Board {
    private final Node[][] grid;

    // Constructor
    public Board(int size) {
        this.grid = new Node[size][size];
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.grid[i][j] = new Node(i, j);
    }

    public Node[][] getGrid() {
        return this.grid;
    }

    // Method to place a piece on the board
    public void placeStone(int x, int y, Color color) {
        this.grid[x][y].setColor(color);
    }

    // Method to check if a player has won
    // Method to display the board
    // ...
}
