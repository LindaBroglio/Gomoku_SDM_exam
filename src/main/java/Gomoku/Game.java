package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;

import java.util.Scanner;

import static Gomoku.utilities.GameSpecifications.*;

public class Game {
    private final Board board;
    private Integer turn = 0;
    private Move move;

    // Constructor
    public Game(Integer boardSize, Integer howManyToWin) {
        this.board = new Board();
        this.move = new Move(board);
    }

    public Board getBoard() {
        return this.board;
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
        return turn < boardSize() * boardSize();
    }
    private void displayBoard() {
        this.board.displayBoard();
    }
}
