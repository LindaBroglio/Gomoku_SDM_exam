package it.units.gomoku;

import it.units.gomoku.exceptions.GameWonException;
import it.units.gomoku.exceptions.inputexceptions.OutOfBoardException;
import it.units.gomoku.exceptions.inputexceptions.TakenNodeException;
import it.units.gomoku.utilities.Color;

public class Move {
    private Integer x;
    private Integer y;
    private final Board board;

    public Move(Board board) {
        this.board = board;
    }

    public void setX(Integer x) { this.x = x; }
    public void setY(Integer y) { this.y = y; }

    public void tryToPlaceStone(boolean turn) throws TakenNodeException, OutOfBoardException {
        checkIfMoveIsLegal();
        board.placeStoneAtPosition(x - 1, y - 1, turn ? Color.BLACK : Color.WHITE);
    }

    private void checkIfMoveIsLegal() throws OutOfBoardException, TakenNodeException {
        if (outOfBounds()) throw new OutOfBoardException("You are trying to place a stone outside the board.");
        if (nodeIsTaken()) throw new TakenNodeException("This node is already taken.");
    }

    public void checkForWinner(Boolean turn) throws GameWonException {
        if (board.isCurrentStonePartOfAWinningStreak(x - 1, y - 1)) throw new GameWonException(turn);
    }
    private boolean outOfBounds() { return x < 1 || x > board.getSize() || y < 1 || y > board.getSize(); }
    private boolean nodeIsTaken() { return board.getGrid()[x - 1][y - 1].getColor() != Color.EMPTY; }
}
