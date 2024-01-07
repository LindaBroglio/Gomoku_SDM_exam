package Gomoku;

import Gomoku.Exceptions.InputExceptions.*;

import java.util.Scanner;
public class Game {
    private final Board board;
    private final Scanner scanner;
    private Integer turn = 1;

    // Constructor
    public Game(int boardSize) {
        this.board = new Board(boardSize);
        this.scanner = new Scanner(System.in);
    }

    public Board getBoard() {
        return this.board;
    }

    // Method to start the game
    public void startGame() {
        while (true) {
            displayBoardAndPromptNextTurn();
            try {
                makeMove();
            } catch (InvalidFormatException | IllegalMoveException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (QuitGameException e) {
                System.out.println(e.getMessage());
                break;
            }
            turn += 1;
        }
    }

    private void displayBoardAndPromptNextTurn() {
        this.board.displayBoard();
        String yourMove = "enter your move (e.g. 3 4):";
        System.out.println((turn % 2 == 0 ? "White turn: " : "Black turn: ") + yourMove);
    }

    private void makeMove() throws InvalidFormatException, QuitGameException, IllegalMoveException {
        Integer[] move = getMoveFromTerminal();
        int x = move[0];
        int y = move[1];
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
        int x, y;
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
