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
        this.game = new Game(inputBoardSizeAndWinningNumber());
    }

    public CLI() throws QuitException {
        this(new Scanner(System.in));
    }

    private Integer[] inputBoardSizeAndWinningNumber() throws QuitException {
        System.out.print("Please enter the board size and the number of pieces needed to win (e.g., '8 5'): ");
        while (true) {
            try {
                String userInput = scanner.nextLine();
                Integer[] validatedInputs = inputValidator.validateInput(userInput);
                if (validatedInputs[1] > 0 && validatedInputs[0] >= validatedInputs[1]) return validatedInputs;
                System.out.println("Invalid board size or number of pieces to win. Please try again.");
            } catch (InvalidFormatException | ResignException e) { System.out.println(e.getMessage()); }
        }
    }
/*
    public void playFromCommandLine() {
        displayBoard();
        while (game.boardIsNotFull()) {
            System.out.println("move: " + game.getMoveCount());
            promptNextTurn(game.isBlackTurn());
            try {
                makeMove();
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
    }*/
    public void playFromCommandLine() {
        while (game.boardIsNotFull()) {
            displayBoard();
            promptNextTurn(game.isBlackTurn());
            if (!tryToMakeAValidMove()) break;
        }
        if (!game.boardIsNotFull()) displayDrawMessage();
    }

    private boolean tryToMakeAValidMove() {
        try {
            makeMove();
            return true;
        } catch (InvalidFormatException | OutOfBoardException | TakenNodeException e) {
            System.out.println(e.getMessage());
            return true;
        } catch (ResignException e) {
            System.out.println(e.getMessage());
            return false;
        } catch (GameWonException e) {
            displayBoard();
            System.out.println(e.getMessage());
            return false;
        }
    }

    private void promptNextTurn(Boolean turn) {
        System.out.println((turn ? "Black" : "White") + " turn: enter your move (e.g. 3 4):");
    }

    public void makeMove() throws ResignException, InvalidFormatException, TakenNodeException, GameWonException, OutOfBoardException {
        game.makeMove(readMove(game.isBlackTurn(), game.getMoveCount()));
    }

    private Integer[] readMove(Boolean turn, Integer moveCount) throws ResignException, InvalidFormatException {
        while (true) {
            String input = scanner.nextLine();
            try { return new InputValidator(turn, moveCount).validateInput(input); }
            catch (QuitException e) { throw new ResignException(game.isBlackTurn()); }
        }
    }

    private void displayDrawMessage() { System.out.println("Board is now full: Game ends in a draw!"); }
    private void displayBoard() { game.board.displayBoard(); }
}
