package it.units.gomoku.exceptions.inputexceptions;

import it.units.gomoku.exceptions.GomokuException;

public class QuitException extends GomokuException {
    public QuitException() {super("Exiting the game, bye!");}
}
