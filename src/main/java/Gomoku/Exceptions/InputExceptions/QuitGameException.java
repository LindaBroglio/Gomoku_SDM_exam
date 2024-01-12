package Gomoku.Exceptions.InputExceptions;

public class QuitGameException extends Exception {
    public QuitGameException(Boolean turn) {
        super(turn ? "Black resigns. White wins!" : "White resigns. Black wins!");
    }
}
