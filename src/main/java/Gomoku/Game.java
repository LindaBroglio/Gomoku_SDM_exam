package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.InputValidator;
import Gomoku.utilities.Move;

import java.io.InputStream;
import java.util.Scanner;

public class Game {
    private final Board board;
    private Boolean turn;
    private Integer moveCount;
    private final Move move;
    private final Scanner scanner;
    private final InputValidator inputValidator;

    public Game(InputStream in) throws QuitException{
        this.moveCount = 0;
        this.turn = true;
        this.scanner = new Scanner(in);
        this.inputValidator = new InputValidator(true, moveCount);
        Integer[] inputs = inputBoardSizeAndWinningNumber();
        this.board = new Board(inputs[0], inputs[1]);
        this.move = new Move(board, in);
    }

    public Game() throws QuitException {
        this(System.in);
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
                System.out.println(e.getMessage());;
            }
        } while (howManyToWin <= 0 || boardSize < howManyToWin);

        return new Integer[]{boardSize, howManyToWin};
    }


    public void play() {
        displayBoard();
        while (boardIsNotFull()) {
            try {
                makeAMove();
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
            nextTurn();
        }
        if (!boardIsNotFull()) displayDrawMessage();
    }


    private void displayDrawMessage() { System.out.println("Board is now full: Game ends in a draw!"); }

    private void makeAMove() throws ResignException, InvalidFormatException, TakenNodeException, OutOfBoardException, GameWonException {
        move.promptNextTurn(turn);
        increaseMoveCount();
        move.readMove(turn, moveCount);
        move.makeMove(turn);
        move.checkWinner(turn);
    }
    private void nextTurn() {
        turn = !turn;
        displayBoard();

    }
    private void increaseMoveCount() { moveCount = moveCount + 1; }
    public int getBoardSize() { return board.getBoardSize(); }
    private boolean boardIsNotFull() { return moveCount < getBoardSize() * getBoardSize(); }
    private void displayBoard() { this.board.displayBoard(); }
}
