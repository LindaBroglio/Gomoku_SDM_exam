package it.units.Gomoku.Exceptions.InputExceptions;

import it.units.Gomoku.Exceptions.GomokuException;

public class ResignException extends GomokuException {
    public ResignException(boolean turn) {
        super(turn ? "Black resigns: white wins!" : "White resigns: black wins!");
    }
}
