package Gomoku.Exceptions.InputExceptions;

public class QuitGameException extends Exception {
    public QuitGameException(int turn) {
        super((turn % 2 == 0) ? "White resigns. Black wins!" : "Black resigns. White wins!");
    }
}
