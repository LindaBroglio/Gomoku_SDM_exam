package it.units.Gomoku.Exceptions;

public class GameWonException extends GomokuException {
    public GameWonException(boolean blackTurn) {
        super(blackTurn ? "Black wins!" : "White wins!");
    }
}
