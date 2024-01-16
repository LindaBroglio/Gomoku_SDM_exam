package it.units.gomoku.exceptions;

public class GameWonException extends GomokuException {
    public GameWonException(boolean blackTurn) {
        super(blackTurn ? "Black wins!" : "White wins!");
    }
}
