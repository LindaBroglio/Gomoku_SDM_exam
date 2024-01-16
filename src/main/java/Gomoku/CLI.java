package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.InputValidator;

import java.util.Scanner;

public class CLI {
    private Game game;
    private final Scanner scanner;
    private final InputValidator inputValidator;

    public CLI(Scanner scanner) throws QuitException {
        this.scanner = scanner;
        this.inputValidator = new InputValidator(true, 0);
        this.game = new Game(inputBoardSizeAndHowManyToWin());
    }

    public CLI() throws QuitException { this(new Scanner(System.in)); }

    private Integer[] inputBoardSizeAndHowManyToWin() throws QuitException {
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

    public void playFromCommandLine() {
        while (!boardIsFull()) {
            displayBoard();
            promptNextTurn(isBlackTurn());
            if (!manageToMakeAValidMove()) break;
        }
        if (boardIsFull()) displayDrawMessage();
    }

    private boolean manageToMakeAValidMove() {
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
            handleGameWonException(e);
            return false;
        }
    }

    private void handleGameWonException(GameWonException e) {
        displayBoard();
        System.out.println(e.getMessage());
        if (askToPlayAgain()) {
            try {
                resetGame();
                playFromCommandLine();
            } catch (QuitException ex) {
                System.out.println(ex.getMessage());
                System.exit(0);
            }
        }
    }

    private void promptNextTurn(Boolean turn) {
        System.out.println((turn ? "Black" : "White") + " turn: enter your move (e.g. 3 4):");
    }

    public void makeMove() throws ResignException, InvalidFormatException, TakenNodeException, GameWonException, OutOfBoardException {
        game.makeMove(readMove(isBlackTurn(), getMoveCount()));
    }

    private Integer[] readMove(Boolean turn, Integer moveCount) throws ResignException, InvalidFormatException {
        while (true) {
            String input = scanner.nextLine();
            try { return new InputValidator(turn, moveCount).validateInput(input); }
            catch (QuitException e) { throw new ResignException(isBlackTurn()); }
        }
    }

    private boolean askToPlayAgain() {
        System.out.print("Game over. Would you like to play again? (yes/no): ");
        while (true) {
            String userInput = scanner.nextLine().trim().toLowerCase();
            if (userInput.equalsIgnoreCase("yes")) return true;
            else if (userInput.equalsIgnoreCase("no")) return false;
            else System.out.println("Enter yes or no");
        }
    }

    private void resetGame() throws QuitException {
        this.game = new Game(inputBoardSizeAndHowManyToWin());
    }

    private boolean boardIsFull() {return game.boardIsFull();}
    private void displayDrawMessage() { System.out.println("Board is now full: Game ends in a draw!"); }
    private void displayBoard() { game.displayBoard(); }
    public boolean isBlackTurn() { return game.isBlackTurn(); }
    public Integer getMoveCount() { return game.getMoveCount(); }
}
