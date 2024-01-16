package Gomoku.Exceptions.InputExceptions;

import Gomoku.Exceptions.GomokuException;

public class QuitException extends GomokuException {
    public QuitException() {super("Exiting the game, bye!");}
}
