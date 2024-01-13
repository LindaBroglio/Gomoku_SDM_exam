package Gomoku.utilities;

import Gomoku.Board;
import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;

import java.io.InputStream;
import java.util.Scanner;

public class Move {
    private Integer x, y;
    private final Board board;
    private final Scanner scanner;

    public Move(Board board, Scanner scanner) {
        this.board = board;
        this.scanner = scanner;
    }

    public void promptNextTurn(Boolean turn) {
        System.out.println((turn ? "Black" : "White") + " turn: enter your move (e.g. 3 4):");
    }

    public void setX(Integer x) { this.x = x; }
    public void setY(Integer y) { this.y = y; }

    public void readMove(Boolean turn, Integer moveCount) throws ResignException, InvalidFormatException {
        String input = scanner.nextLine();
        Integer[] coordinates = new Integer[0];
        try {
            coordinates = new InputValidator(turn, moveCount).validateInput(input);
        } catch (QuitException e) {
            System.out.println(e.getMessage());
        }
        setX(coordinates[0]);
        setY(coordinates[1]);
    }

    private void checkIfLegalMove() throws OutOfBoardException, TakenNodeException {
        if (outOfBounds()) throw new OutOfBoardException("The coordinates are outside the board.");
        if (nodeIsTaken()) throw new TakenNodeException("The node is already taken.");
    }

    public void makeMove(Boolean turn) throws TakenNodeException, OutOfBoardException {
        checkIfLegalMove();
        board.placeStone(x - 1, y - 1, turn ? Color.BLACK : Color.WHITE);
    }

    public void checkWinner(Boolean turn) throws GameWonException {
        if (board.isCurrentStonePartOfAWinningStreak(x - 1, y - 1)) throw new GameWonException(turn);
    }
    private boolean outOfBounds() { return x < 1 || x > board.getBoardSize() || y < 1 || y > board.getBoardSize(); }
    private boolean nodeIsTaken() { return board.getGrid()[x - 1][y - 1].getColor() != Color.EMPTY; }
}
