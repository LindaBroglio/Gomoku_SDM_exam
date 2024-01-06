package Gomoku;

import java.util.Scanner;
public class Game {
    private final Board board;
    private final Scanner scanner;
    private Integer turn = 0;

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
            turn += 1;
            this.board.displayBoard();
            System.out.println("Enter your move (x y): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Game ended.");
                break;
            }
            int x = scanner.nextInt();
            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input. Game ended.");
                break;
            }
            int y = scanner.nextInt();
            if (turn % 2 == 0) this.board.placeStone(x - 1, y - 1, Color.WHITE);
            if (turn % 2 == 1) this.board.placeStone(x - 1, y - 1, Color.BLACK);
        }
    }

    // Method to end the game
    // Method to switch turns
    // ...
}
