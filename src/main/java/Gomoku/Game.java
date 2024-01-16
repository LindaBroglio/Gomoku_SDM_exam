package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;

public class Game {
    private final Board board;
    private Boolean blackTurn;
    private Integer moveCount;
    private final Move move;

    public Game(Integer[] gameSpecifications) {
        this.moveCount = 0;
        this.blackTurn = true;
        Integer boardSize = gameSpecifications[0];
        Integer howManyToWin = gameSpecifications[1];
        this.board = new Board(boardSize, howManyToWin);
        this.move = new Move(board);
    }

    public void makeMove(Integer[] coordinates) throws TakenNodeException, OutOfBoardException, GameWonException {
        move.setX(coordinates[0]);
        move.setY(coordinates[1]);
        move.tryToPlaceStone(blackTurn);
        increaseMoveCount();
        move.checkForWinner(blackTurn);
        nextTurn();
    }

    public void displayBoard() { board.display(); }
    private void nextTurn() { blackTurn = !blackTurn; }
    private void increaseMoveCount() { moveCount++; }
    public Integer getMoveCount() { return moveCount; }
    public Integer getBoardSize() { return board.getSize(); }
    public boolean boardIsFull() { return moveCount >= getBoardSize() * getBoardSize(); }
    public boolean isBlackTurn() { return blackTurn; }
}