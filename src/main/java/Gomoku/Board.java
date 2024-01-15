package Gomoku;

import Gomoku.utilities.Color;

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

    public void placeStone(Integer x, Integer y, Color color)  {
        this.grid[x][y].setColor(color);
    }

    public boolean isCurrentStonePartOfAWinningStreak(Integer row, Integer col) {
        return checkInAllDirections(row, col);
    }

    private boolean checkInAllDirections(Integer row, Integer col){
        return isWinningHorizontalStreak(row, col) ||
                isWinningVerticalStreak(row, col) ||
                isWinningDiagonalStreak(row, col, true) || // Main diagonal
                isWinningDiagonalStreak(row, col, false);  // Non-main diagonal
    }

    private boolean isWinningHorizontalStreak(Integer row, Integer col) {
        int count = 1; // Include the current stone
        count += countConsecutiveStonesInDirection(row, col, 0, -1); // Left direction
        count += countConsecutiveStonesInDirection(row, col, 0, 1);  // Right direction
        return count >= howManyToWin;
    }

    private boolean isWinningVerticalStreak(Integer row, Integer col) {
        int count = 1; // Include the current stone
        count += countConsecutiveStonesInDirection(row, col, -1, 0); // Up direction
        count += countConsecutiveStonesInDirection(row, col, 1, 0);  // Down direction
        return count >= howManyToWin;
    }

    private boolean isWinningDiagonalStreak(Integer row, Integer col, boolean isMainDiagonal) {
        int count = 1; // Include the current stone
        count += countConsecutiveStonesInDirection(row, col, -1, isMainDiagonal ? -1 : 1); // Up-left or Up-right direction
        count += countConsecutiveStonesInDirection(row, col, 1, isMainDiagonal ? 1 : -1);  // Down-right or Down-left direction
        return count >= howManyToWin;
    }

    private int countConsecutiveStonesInDirection(int startRow, int startCol, int rowIncrement, int colIncrement) {
        int consecutiveCount = 0;
        Color stoneColor = nodeColor(startRow, startCol);
        for (int offset = 1; offset < howManyToWin; offset++) {
            int nextRow = startRow + rowIncrement * offset;
            int nextCol = startCol + colIncrement * offset;
            if (isPositionOutOfBounds(nextRow, nextCol) || isStoneColorDifferent(nextRow, nextCol, stoneColor)) {
                break; // Stop counting if out of bounds or color is different
            }
            consecutiveCount++;
        }
        return consecutiveCount;
    }

    private boolean isPositionOutOfBounds(int row, int col) {
        return row < 0 || row >= boardSize || col < 0 || col >= boardSize;
    }

    private boolean isStoneColorDifferent(int row, int col, Color referenceColor) {
        return nodeColor(row, col) != referenceColor;
    }

    private Color nodeColor(Integer i, Integer j) {
        return grid[i][j].getColor();
    }

    public void displayBoard() {
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

    public Integer getBoardSize() {
        return boardSize;
    }

    public Integer getHowManyToWin() {
        return howManyToWin;
    }
}
