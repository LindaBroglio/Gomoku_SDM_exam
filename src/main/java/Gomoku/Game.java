package Gomoku;

import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Color;

import java.util.Scanner;

import static Gomoku.utilities.GameSpecifications.*;

public class Game {
    private final Board board;
    private final Scanner scanner;
    private Integer turn = 0;

    private Integer x, y;

    // Constructor
    public Game(Integer boardSize, Integer howManyToWin) {
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    public Board getBoard() {
        return this.board;
    }

    // Method to start the game
    public void startGame() {
        displayBoard();
        while (gameIsOn()) {
            PromptNextTurn();
            try {
                makeMove();
                checkWinner();
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
        displayResultMessage();
    }

    private void checkWinner() throws GameWonException {
        // conosce tutto
        // chiama un metodo di Board che vuole x e y
        if (this.board.enoughConsecutive(x-1, y-1)) throw new GameWonException(turn);
    }

    private boolean isThereAWinner() {
        return turn == 5;
    }

    private void displayResultMessage() {
        //white won
        if (isThereAWinner() && isWhiteTurn()) {
            System.out.println("Gomoku! White wins!");
            return;
        } else if (isThereAWinner() && !isWhiteTurn()){
            System.out.println("Gomoku! Black wins!");
            return;
        }
        if (!boardIsNotFull()) {
            System.out.println("Game ends in a draw!");
        }
    }

    private boolean isWhiteTurn(){
        return turn % 2 == 0;
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

    private void PromptNextTurn() {
        turn += 1;
        String yourMove = "enter your move (e.g. 3 4):";
        System.out.println((isWhiteTurn() ? "White turn: " : "Black turn: ") + yourMove);
    }

    private void makeMove() throws InvalidFormatException, QuitGameException, IllegalMoveException {
        Integer[] move = getMoveFromTerminal();
        x = move[0]; y = move[1];
        this.board.placeStone(x - 1, y - 1, (turn % 2 == 0) ? Color.WHITE : Color.BLACK);
    }

    public Integer[] getMoveFromTerminal() throws InvalidFormatException, QuitGameException {
        String input = scanner.nextLine();
        if (isQuitCommand(input)) throw new QuitGameException(turn);
        String[] inputs = input.split(" ");
        validateInputLength(inputs);
        return parseInputToIntegers(inputs);
    }

    private boolean isQuitCommand(String input) {
        return input.equalsIgnoreCase("quit");
    }

    private void validateInputLength(String[] inputs) throws InvalidFormatException {
        if (inputs.length != 2) {
            throw new InvalidFormatException("You must enter exactly two integers.");
        }
    }

    private Integer[] parseInputToIntegers(String[] inputs) throws InvalidFormatException {
        try {
            x = Integer.parseInt(inputs[0]);
            y = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Both inputs must be integers.");
        }
        return new Integer[]{x, y};
    }

    // ...
}
