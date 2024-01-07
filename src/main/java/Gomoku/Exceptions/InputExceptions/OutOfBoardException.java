package Gomoku.Exceptions.InputExceptions;

import Gomoku.Exceptions.InputExceptions.IllegalMoveException;

public class OutOfBoardException extends IllegalMoveException {
    public OutOfBoardException(String message) {
        super(message);
    }
}
