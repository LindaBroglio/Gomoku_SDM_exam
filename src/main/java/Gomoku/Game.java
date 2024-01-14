package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;

import java.util.Scanner;

public class Game {
    final Board board;
    private Boolean blackTurn;
    private Integer moveCount;
    private final Move move;

    public Game(Integer[] gameSpecification) {
        this.moveCount = 0;
        this.blackTurn = true;
        Integer boardSize = gameSpecification[0];
        Integer howManyToWin = gameSpecification[1];
        this.board = new Board(boardSize, howManyToWin);
        this.move = new Move(board);
    }

    public void makeMove(Integer[] coordinates) throws TakenNodeException, OutOfBoardException, GameWonException {
        increaseMoveCount();
        move.setX(coordinates[0]);
        move.setY(coordinates[1]);
        move.makeMove(blackTurn);
        move.checkWinner(blackTurn);
        nextTurn();
    }

    private void nextTurn() { blackTurn = !blackTurn; }
    private void increaseMoveCount() { moveCount++; }
    public Integer getMoveCount() { return moveCount; }
    public Integer getBoardSize() { return board.getBoardSize(); }
    public Boolean boardIsNotFull() { return moveCount < getBoardSize() * getBoardSize(); }
    public Boolean isBlackTurn() { return blackTurn; }
}