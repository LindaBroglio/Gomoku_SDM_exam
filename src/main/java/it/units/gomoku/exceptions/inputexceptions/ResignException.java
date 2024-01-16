package it.units.gomoku.exceptions.inputexceptions;

import it.units.gomoku.exceptions.GomokuException;

public class ResignException extends GomokuException {
    public ResignException(boolean turn) {
        super(turn ? "Black resigns: white wins!" : "White resigns: black wins!");
    }
}
