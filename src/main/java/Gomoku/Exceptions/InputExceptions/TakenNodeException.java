package Gomoku.Exceptions.InputExceptions;

import Gomoku.Exceptions.InputExceptions.IllegalMoveException;

public class TakenNodeException extends IllegalMoveException {
    public TakenNodeException(String message) {
        super(message);
    }
}
