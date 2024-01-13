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

    public Game(Integer[] gameSpecification, Scanner scanner) {
        this.moveCount = 0;
        this.blackTurn = true;
        Integer boardSize = gameSpecification[0];
        Integer howManyToWin = gameSpecification[1];
        this.board = new Board(boardSize, howManyToWin);
        this.move = new Move(board, scanner);
    }

    public Game(Integer[] gameSpecification) {
        this(gameSpecification, null);
    }

    public void makeMoveCLI() throws ResignException, InvalidFormatException, TakenNodeException, OutOfBoardException, GameWonException {
        move.promptNextTurn(blackTurn);
        increaseMoveCount();
        move.readMove(blackTurn, moveCount);
        move.makeMove(blackTurn);
        move.checkWinner(blackTurn);
        nextTurn();
    }

    public void makeMoveGUI(Integer x, Integer y) throws TakenNodeException, OutOfBoardException, GameWonException {
        increaseMoveCount();
        move.setX(x);
        move.setY(y);
        move.makeMove(blackTurn);
        move.checkWinner(blackTurn);
        nextTurn();
    }

    private void nextTurn() { blackTurn = !blackTurn; }
    private void increaseMoveCount() { moveCount++; }
    public int getBoardSize() { return board.getBoardSize(); }
    public boolean boardIsNotFull() { return moveCount < getBoardSize() * getBoardSize(); }
    public boolean isBlackTurn() { return blackTurn; }
}