package it.units.Gomoku.Exceptions.InputExceptions;

import it.units.Gomoku.Exceptions.GomokuException;

public class InputException extends GomokuException {
    public InputException(String message) { super(message + " Please try again."); }
}
