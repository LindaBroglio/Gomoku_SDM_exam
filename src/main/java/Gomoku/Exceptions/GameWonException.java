package Gomoku.Exceptions;

public class GameWonException extends Exception {
    public GameWonException(Boolean blackTurn) {
        super(blackTurn ? "Black wins!" : "White wins!");
    }
}
