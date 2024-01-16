package Gomoku.Exceptions.InputExceptions;

import Gomoku.Exceptions.GomokuException;

public class ResignException extends GomokuException {
    public ResignException(boolean turn) {
        super(turn ? "Black resigns: white wins!" : "White resigns: black wins!");
    }
}
