package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.InputValidator;

import java.util.Scanner;

public class CLI {
    private final Game game;
    private final Scanner scanner;
    private final InputValidator inputValidator;

    public CLI(Scanner scanner) throws QuitException {
        this.scanner = scanner;
        this.inputValidator = new InputValidator(true, 0);
        Integer[] gameSpecification = inputBoardSizeAndWinningNumber();
        this.game = new Game(gameSpecification, scanner);
    }

    public CLI() throws QuitException {
        this(new Scanner(System.in));
    }

    private Integer[] inputBoardSizeAndWinningNumber() throws QuitException {
        Integer boardSize = 0, howManyToWin = 0;
        do {
            System.out.print("Please enter the board size and the number of pieces needed to win (e.g., '8 5'): ");
            String userInput = scanner.nextLine();
            try {
                Integer[] validatedInputs = inputValidator.validateInput(userInput);
                boardSize = validatedInputs[0];
                howManyToWin = validatedInputs[1];
            } catch (InvalidFormatException | ResignException e) {
                System.out.println(e.getMessage());
            }
        } while (howManyToWin <= 0 || boardSize < howManyToWin);
        return new Integer[]{boardSize, howManyToWin};
    }

    public void play() {
        displayBoard();
        while (game.boardIsNotFull()) {
            try {
                game.makeMoveCLI();
            } catch (InvalidFormatException | OutOfBoardException | TakenNodeException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (ResignException e) {
                System.out.println(e.getMessage());
                break;
            } catch (GameWonException e) {
                displayBoard();
                System.out.println(e.getMessage());
                break;
            }
            displayBoard();
        }
        if (!game.boardIsNotFull()) displayDrawMessage();
    }

    private void displayDrawMessage() { System.out.println("Board is now full: Game ends in a draw!"); }
    private void displayBoard() { this.game.board.displayBoard(); }
}
