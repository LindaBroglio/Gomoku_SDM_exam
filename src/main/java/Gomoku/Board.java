package Gomoku;

import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Color;
import Gomoku.utilities.GameSpecifications;

import static Gomoku.utilities.GameSpecifications.boardSize;
import static Gomoku.utilities.GameSpecifications.howManyToWin;

public class Board {
    private final Node[][] grid;

    // Constructor
    public Board() {
        this.grid = new Node[boardSize()][boardSize()];
        for (int i = 0; i < boardSize(); i++)
            for (int j = 0; j < boardSize(); j++)
                this.grid[i][j] = new Node();
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


    public boolean enoughConsecutive(Integer i, Integer j) {
        return horizontalCheck(i, j) |
                verticalCheck(i, j) |
                diagonalCheck(i, j, true) |
                diagonalCheck(i, j, false);
    }

    private boolean horizontalCheck(Integer i, Integer j) {
        int count = 0;
        for (Integer index : getOrthogonalIndices(j)) {
            if (sameColor(i, j, i, index)) count++;
            else count = 0;
            if (count >= howManyToWin()) return true;
        }
        return false;
    }

    private boolean verticalCheck(Integer i, Integer j) {
        int count = 0;
        for (Integer index : getOrthogonalIndices(i)) {
            if (sameColor(i, j, index, j)) count++;
            else count = 0;
            if (count >= howManyToWin()) return true;
        }
        return false;
    }

    private boolean diagonalCheck(Integer i, Integer j, boolean isMainDiagonal) {
        Integer[] rowIndices = getDiagonalIndices(i);
        Integer[] colIndices = getDiagonalIndices(j);
        int count = 0;
        for (int k = 0; k < Math.min(rowIndices.length, colIndices.length); k++) {
            int colIndex = isMainDiagonal ? colIndices[k] : colIndices[colIndices.length - 1 - k];
            if (sameColor(i, j, rowIndices[k], colIndex)) count++;
            else count = 0;
            if (count >= howManyToWin()) return true;
        }
        return false;
    }

    private Integer[] getOrthogonalIndices(Integer index) {
        int start = Math.max(0, index - howManyToWin());
        int end = Math.min(boardSize() - 1, index + howManyToWin());
        Integer[] Range = new Integer[end - start + 1];
        for (int i = start; i <= end; i++) Range[i - start] = i;
        return Range;
    }

    private Integer[] getDiagonalIndices(Integer index) {
        int start = Math.max(0, index - howManyToWin() + 1);
        int end = Math.min(boardSize() - 1, index + howManyToWin() - 1);
        Integer[] Range = new Integer[end - start + 1];
        for (int k = start; k <= end; k++) Range[k - start] = k;
        return Range;
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
