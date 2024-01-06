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

    // Method to check if a player has won (HARD)


    // Method to display the board
    public void displayBoard() {
        for (Node[] nodes : grid) {
            for (Node node : nodes) {
                switch (node.getColor()) {
                    case BLACK -> System.out.print("B ");  // Hollow dot for black
                    case WHITE -> System.out.print("W ");  // Full dot for white
                    default -> System.out.print(". ");  // Dot for empty
                }
            }
            System.out.println();
        }
    }


    // ...
}
