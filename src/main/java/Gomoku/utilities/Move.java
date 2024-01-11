package Gomoku.utilities;

import Gomoku.Board;
import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.InvalidFormatException;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.QuitGameException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;

import java.util.Scanner;

public class Move {
    private Integer x, y;
    private final Board board;

    public Move(Board board) {
        this.board = board;
    }

    public void promptNextTurn(Integer turn) {
        System.out.println((blackTurn(turn) ? "Black" : "White") + " turn: enter your move (e.g. 3 4):");
    }

    public void readMove(Integer turn) throws QuitGameException, InvalidFormatException {
        String input = new Scanner(System.in).nextLine();
        Integer[] coordinates = new InputValidator(turn).validateInput(input);
        x = coordinates[0];
        y = coordinates[1];
    }

    private void checkIfLegalMove() throws OutOfBoardException, TakenNodeException {
        if (outOfBounds()) throw new OutOfBoardException("The coordinates are outside the board.");
        if (nodeIsTaken()) throw new TakenNodeException("The node is already taken.");
    }

    public void makeMove(Integer turn) throws TakenNodeException, OutOfBoardException {
        checkIfLegalMove();
        board.placeStone(x - 1, y - 1, blackTurn(turn) ? Color.BLACK : Color.WHITE);
    }

    public void checkWinner(Integer turn) throws GameWonException {
        if (board.isCurrentStonePartOfAWinningStreak(x - 1, y - 1)) throw new GameWonException(turn);
    }

    private boolean blackTurn(Integer turn) { return turn % 2 == 1; }

    private boolean outOfBounds() { return x < 1 || x > board.getBoardSize() || y < 1 || y > board.getBoardSize(); }

    private boolean nodeIsTaken() { return board.getGrid()[x - 1][y - 1].getColor() != Color.EMPTY; }
}
