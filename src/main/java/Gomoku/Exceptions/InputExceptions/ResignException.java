package Gomoku.Exceptions.InputExceptions;

import Gomoku.Exceptions.GomokuException;

public class ResignException extends GomokuException {
    public ResignException(boolean turn) {
        super(turn ? "Black resigns. White wins!" : "White resigns. Black wins!");
    }
}
