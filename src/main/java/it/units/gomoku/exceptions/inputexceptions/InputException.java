package it.units.gomoku.exceptions.inputexceptions;

import it.units.gomoku.exceptions.GomokuException;

public class InputException extends GomokuException {
    public InputException(String message) { super(message + " Please try again."); }
}
