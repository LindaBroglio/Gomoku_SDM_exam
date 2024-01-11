package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;

import java.util.Scanner;

public class Game {
    private final Board board;
    private Integer turn = 0;
    private final Move move;

    public Integer inputHowManyToWin() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the number of pieces needed to win: ");
        return scanner.nextInt();
    }
    public Integer inputBoardSize() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Please enter the board size: ");
        return scanner.nextInt();
    }

    // Constructor
    public Game() {
        Integer howManyToWin = inputHowManyToWin();
        Integer boardSize = inputBoardSize();
        this.board = new Board(boardSize, howManyToWin);
        this.move = new Move(board);
    }

    public Board getBoard() {
        return this.board;
    }

    public int getBoardSize() {
        return board.getBoardSize();
    }

    public int getHowManyToWin() {
        return board.getHowManyToWin();
    }

    // Method to start the game
    public void startGame() {
        displayBoard();
        while (gameIsOn()) {
            turn += 1;
            move.PromptNextTurn(turn);
            try {
                move.readMove(turn);
                move.checkIfLegalMove();
                move.makeMove(turn);
                move.checkWinner(turn);
            } catch (InvalidFormatException | IllegalMoveException e) {
                System.out.println(e.getMessage());
                turn -= 1;
                continue;
            } catch (QuitGameException | GameWonException e) {
                System.out.println(e.getMessage());
                break;
            }
            displayBoard();
        }
        displayBoard();
    }

    private boolean gameIsOn(){
        return (boardIsNotFull());
    }
    private boolean boardIsNotFull(){
        return turn < getBoardSize() * getBoardSize();
    }
    private void displayBoard() {
        this.board.displayBoard();
    }
}
