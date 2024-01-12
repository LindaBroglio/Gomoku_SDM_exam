package Gomoku.Exceptions.InputExceptions;

public class ResignException extends Exception {
    public ResignException(Boolean turn) {
        super(turn ? "Black resigns. White wins!" : "White resigns. Black wins!");
    }
}
