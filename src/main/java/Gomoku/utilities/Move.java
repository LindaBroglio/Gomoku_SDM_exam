package Gomoku.utilities;

import Gomoku.Board;
import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.InvalidFormatException;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.QuitGameException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;

import java.util.Scanner;

import static Gomoku.utilities.GameSpecifications.boardSize;

public class Move {
    private Integer x, y;
    private Board board;

    private final Scanner scanner;
    public Move(Board board) {
        this.board = board;
        this.scanner = new Scanner(System.in);
    }

    public void PromptNextTurn(Integer turn) {
        String yourMove = "enter your move (e.g. 3 4):";
        System.out.println((isWhiteTurn(turn) ? "White turn: " : "Black turn: ") + yourMove);
    }

    public void readMove(Integer turn) throws QuitGameException, InvalidFormatException {
        String input = scanner.nextLine();
        Integer[] xy = new InputValidator(turn).validateInput(input);
        x = xy[0];
        y = xy[1];
    }

    public void checkIfLegalMove() throws OutOfBoardException, TakenNodeException {
        if (outOfBounds()) throw new OutOfBoardException("The coordinates are outside the board.");
        if (nodeIsTaken()) throw new TakenNodeException("The node is already taken.");
    }

    public void checkWinner(Integer turn) throws GameWonException {
        if (this.board.isCurrentStonePartOfAWinningStreak(x - 1, y - 1)) throw new GameWonException(turn);
    }

    public void makeMove(Integer turn) throws TakenNodeException, OutOfBoardException {
        board.placeStone(x - 1, y - 1, isWhiteTurn(turn) ? Color.WHITE : Color.BLACK);
    }

    private boolean isWhiteTurn(Integer turn){
        return turn % 2 == 0;
    }

    private boolean outOfBounds() {
        return x < 0 || x >= boardSize() || y < 0 || y >= boardSize();
    }

    private boolean nodeIsTaken() {
        return nodeColor(x,y) != Color.EMPTY;
    }

    private Color nodeColor(Integer i, Integer j) {
        return this.board.getGrid()[i][j].getColor();
    }
}
