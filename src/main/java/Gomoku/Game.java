package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;

import java.util.Scanner;

public class Game {
    private final Board board;
    private Boolean turn;
    private Integer moveCount;
    private final Move move;
    private final Scanner scanner;

    public Game(Scanner scanner) {
        this.moveCount = 0;
        this.turn = true;
        this.scanner = scanner;
        this.board = new Board(inputBoardSize(), inputHowManyToWin());
        this.move = new Move(board);
    }

    public Game() {
        this(new Scanner(System.in));
    }

    private Integer inputBoardSize() {
        System.out.print("Please enter the board size: ");
        return scanner.nextInt();
    }

    private Integer inputHowManyToWin() {
        System.out.print("Please enter the number of pieces needed to win: ");
        return scanner.nextInt();
    }

    public void play() {
        displayBoard();
        while (boardIsNotFull()) {
            try {
                makeAMove();
            } catch (InvalidFormatException | OutOfBoardException | TakenNodeException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (QuitGameException e) {
                System.out.println(e.getMessage());
                break;
            } catch (GameWonException e) {
                displayBoard();
                System.out.println(e.getMessage());
                break;
            }
            nextTurn();
        }
        if (!boardIsNotFull()) displayDrawMessage();
    }

    private void displayDrawMessage() { System.out.println("Board is now full: Game ends in a draw!"); }

    private void makeAMove() throws QuitGameException, InvalidFormatException, TakenNodeException, OutOfBoardException, GameWonException {
        move.promptNextTurn(turn);
        move.readMove(turn);
        move.makeMove(turn);
        move.checkWinner(turn);
    }
    private void nextTurn() {
        turn = !turn;
        displayBoard();
        increaseMoveCount();
    }
    private void increaseMoveCount() { moveCount = moveCount + 1; }
    public int getBoardSize() { return board.getBoardSize(); }
    private boolean boardIsNotFull() { return moveCount < getBoardSize() * getBoardSize(); }
    private void displayBoard() { this.board.displayBoard(); }
}
