package Gomoku;

public class Board {
    private Node[][] grid;

    // Constructor
    public Board(int size) {
        this.grid = new Node[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.grid[i][j] = new Node(i, j);
            }
        }
    }

    // Method to place a piece on the board
    // Method to check if a player has won
    // Method to display the board
    // ...
}
