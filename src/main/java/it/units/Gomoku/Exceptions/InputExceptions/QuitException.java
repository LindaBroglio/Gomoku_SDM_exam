package it.units.Gomoku.Exceptions.InputExceptions;

import it.units.Gomoku.Exceptions.GomokuException;

public class QuitException extends GomokuException {
    public QuitException() {super("Exiting the game, bye!");}
}
