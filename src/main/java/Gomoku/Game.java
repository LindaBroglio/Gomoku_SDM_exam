package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;

import java.util.Scanner;

public class Game {
    private final Board board;
    private Integer turn;
    private final Move move;
    private final Scanner scanner;

    public Game(Scanner scanner) {
        this.scanner = scanner;
        this.board = new Board(inputBoardSize(), inputHowManyToWin());
        this.turn = 0;
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

    public void startGame() {
        displayBoard();
        while (boardIsNotFull()) {
            try {
                move.promptNextTurn(++turn);
                move.readMove(turn);
                move.makeMove(turn);
                move.checkWinner(turn);
            } catch (InvalidFormatException | OutOfBoardException | TakenNodeException e) {
                System.out.println(e.getMessage());
                turn--;
                continue;
            } catch (QuitGameException | GameWonException e) {
                System.out.println(e.getMessage());
                break;
            }
            displayBoard();
        }
        displayBoard();
    }

    public int getBoardSize() { return board.getBoardSize(); }
    private boolean boardIsNotFull() { return turn < getBoardSize() * getBoardSize(); }
    private void displayBoard() { this.board.displayBoard(); }
}
