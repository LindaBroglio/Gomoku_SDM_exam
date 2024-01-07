package Gomoku.Exceptions.InputExceptions;

import Gomoku.Exceptions.GomokuException;

public class InputException extends GomokuException {
    public InputException(String message) { super(message + " Please try again."); }
}
