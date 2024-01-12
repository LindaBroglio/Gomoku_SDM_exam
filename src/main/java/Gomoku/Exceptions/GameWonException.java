package Gomoku.Exceptions;

public class GameWonException extends Exception {
    public GameWonException(int turn) {
        super(((turn % 2) == 0) ? "White wins!" : "Black wins!");
    }
}