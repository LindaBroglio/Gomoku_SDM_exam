package Gomoku.Exceptions.InputExceptions;

import Gomoku.Exceptions.GomokuException;

public class QuitException extends GomokuException {
    private static final String RESIGN_MESSAGE = "You decided to exit from the game, ciao!";
    public QuitException() {
        super(RESIGN_MESSAGE);
    }
}
