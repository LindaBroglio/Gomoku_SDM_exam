package Gomoku;

import Gomoku.Exceptions.InputExceptions.*;

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
    public void placeStone(int x, int y, Color color) throws OutOfBoardException, TakenNodeException {
        if (outOfBounds(x, y)) throw new OutOfBoardException("The coordinates are outside the board.");
        if (nodeIsTaken(x, y)) throw new TakenNodeException("The node is already taken.");
        this.grid[x][y].setColor(color);
    }

    private boolean outOfBounds(int x, int y) {
        return x < 0 || x >= boardSize() || y < 0 || y >= boardSize();
    }

    private boolean nodeIsTaken(int x, int y) {
        return this.grid[x][y].getColor() != Color.EMPTY;
    }

    public Integer boardSize() {
        return this.grid.length;
    }

    // Method to check if a player has won (HARD)


    // Method to display the board
    public void displayBoard() {
        for (Node[] nodes : grid) {
            for (Node node : nodes) {
                switch (node.getColor()) {
                    case BLACK -> System.out.print("B ");
                    case WHITE -> System.out.print("W ");
                    default -> System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

}
