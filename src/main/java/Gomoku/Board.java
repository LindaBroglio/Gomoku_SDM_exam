package Gomoku;

import Gomoku.Exceptions.InputExceptions.*;

public class Board {
    private final Node[][] grid;
    private final Integer howManyToWin;

    // Constructor
    public Board(int size, Integer howManyToWin) {
        this.grid = new Node[size][size];
        this.howManyToWin = howManyToWin;
        for (int i = 0; i < size; i++)
            for (int j = 0; j < size; j++)
                this.grid[i][j] = new Node(i, j);
    }

    public Node[][] getGrid() {
        return this.grid;
    }

    // Method to place a piece on the board
    public void placeStone(Integer x, Integer y, Color color) throws OutOfBoardException, TakenNodeException {
        if (outOfBounds(x, y)) throw new OutOfBoardException("The coordinates are outside the board.");
        if (nodeIsTaken(x, y)) throw new TakenNodeException("The node is already taken.");
        this.grid[x][y].setColor(color);
    }

    private boolean outOfBounds(Integer x, Integer y) {
        return x < 0 || x >= boardSize() || y < 0 || y >= boardSize();
    }

    private boolean nodeIsTaken(Integer x, Integer y) {
        return this.grid[x][y].getColor() != Color.EMPTY;
    }

    public Integer boardSize() {
        return this.grid.length;
    }

    // Method to check if a player has won (HARD)
    public boolean enoughConsecutive(Integer i, Integer j) {
        return horizontalCheck(i, j) | verticalCheck(i,j);
    }

    private boolean horizontalCheck(Integer i, Integer j) {
        Integer count = 0;
        for (Integer index : indices(j)) {
            if (sameColor(i, j, i, index)) count++;
            else count = 0;
        }
        return count >= howManyToWin;
    }

    private boolean verticalCheck(Integer i, Integer j) {
        Integer count = 0;
        for (Integer index : indices(i)) {
            if (sameColor(i, j, index, j)) count++;
            else count = 0;
        }
        return count >= howManyToWin;
    }

    public Integer[] indices(int index) {
        int start = Math.max(0, index - howManyToWin);
        int end = Math.min(boardSize() - 1, index + howManyToWin);
        Integer[] range = new Integer[end - start + 1]; // Corrected size
        for (int i = start; i <= end; i++) range[i - start] = i; // Corrected index
        return range;
    }

    private boolean sameColor(Integer i, Integer j, Integer k, Integer l) {
        return this.grid[i][j].getColor() == this.grid[k][l].getColor();
    }

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
