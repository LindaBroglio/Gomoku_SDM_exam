package it.units.Gomoku;

import it.units.Gomoku.utilities.Color;

public class Board {
    private final Node[][] grid;
    private final Integer boardSize;
    private final Integer howManyToWin;

    public Board(Integer boardSize, Integer howManyToWin) {
        this.boardSize = boardSize;
        this.howManyToWin = howManyToWin;
        this.grid = new Node[boardSize][boardSize];
        initializeGrid(boardSize);
    }

    private void initializeGrid(Integer boardSize){
        for (int i = 0; i < boardSize; i++)
            for (int j = 0; j < boardSize; j++)
                this.grid[i][j] = new Node();
    }

    public Node[][] getGrid() {
        return this.grid;
    }
    public void placeStoneAtPosition(Integer x, Integer y, Color color)  { this.grid[x][y].setColor(color); }
    public boolean isCurrentStonePartOfAWinningStreak(Integer row, Integer col) { return checkInAllDirections(row, col); }

    private boolean checkInAllDirections(Integer row, Integer col){
        return checkForHorizontalStreak(row, col) ||
                checkForVerticalStreak(row, col) ||
                checkForDiagonalStreak(row, col, true) ||
                checkForDiagonalStreak(row, col, false);
    }

    private boolean checkForHorizontalStreak(Integer row, Integer col) {
        int count = 1;
        count += countConsecutiveStonesInDirection(row, col, 0, -1); // Left
        count += countConsecutiveStonesInDirection(row, col, 0, 1);  // Right
        return count >= howManyToWin;
    }

    private boolean checkForVerticalStreak(Integer row, Integer col) {
        int count = 1;
        count += countConsecutiveStonesInDirection(row, col, -1, 0); // Up
        count += countConsecutiveStonesInDirection(row, col, 1, 0);  // Down
        return count >= howManyToWin;
    }

    private boolean checkForDiagonalStreak(Integer row, Integer col, boolean isMainDiagonal) {
        int count = 1;
        count += countConsecutiveStonesInDirection(row, col, -1, isMainDiagonal ? -1 : 1); // Up-left or Up-right
        count += countConsecutiveStonesInDirection(row, col, 1, isMainDiagonal ? 1 : -1);  // Down-right or Down-left
        return count >= howManyToWin;
    }

    private int countConsecutiveStonesInDirection(int startRow, int startCol, int rowIncrement, int colIncrement) {
        int consecutiveCount = 0;
        Color stoneColor = nodeColor(startRow, startCol);
        for (int offset = 1; offset < howManyToWin; offset++) {
            int nextRow = startRow + rowIncrement * offset;
            int nextCol = startCol + colIncrement * offset;
            if (isPositionOutOfBounds(nextRow, nextCol) || isStoneColorDifferent(nextRow, nextCol, stoneColor)) {
                break; // Stop counting if out of bounds or if color is different
            }
            consecutiveCount++;
        }
        return consecutiveCount;
    }

    public void display() {
        int maxNumberWidth = String.valueOf(boardSize).length();

        System.out.print(" ".repeat(maxNumberWidth));
        for (int i = 1; i <= boardSize; i++) {
            System.out.printf("%3d", i);
        }
        System.out.println();
        String formatSpecifier = "%" + maxNumberWidth + "d ";
        for (int i = 0; i < boardSize; i++) {
            System.out.printf(formatSpecifier, i + 1);
            for (Node node : grid[i]) {
                switch (node.getColor()) {
                    case BLACK -> System.out.print(" B ");
                    case WHITE -> System.out.print(" W ");
                    default -> System.out.print(" · ");
                }
            }
            System.out.printf(" %" + maxNumberWidth + "d", i + 1);
            System.out.println();
        }

        System.out.print(" ".repeat(maxNumberWidth));
        for (int i = 1; i <= boardSize; i++) {
            System.out.printf("%3d", i);
        }
        System.out.println();
    }

    public Integer getSize() { return boardSize; }
    public Integer getHowManyToWin() {
        return howManyToWin;
    }
    private boolean isPositionOutOfBounds(int row, int col) { return row < 0 || row >= boardSize || col < 0 || col >= boardSize; }
    private boolean isStoneColorDifferent(int row, int col, Color referenceColor) { return nodeColor(row, col) != referenceColor; }
    private Color nodeColor(Integer i, Integer j) { return grid[i][j].getColor(); }
}
