package Gomoku;

public class Game {
    private final Board board;

    // Constructor
    public Game(int boardSize) {
        this.board = new Board(boardSize);
    }

    public Board getBoard() {
        return this.board;
    }

    // Method to start the game
    // Method to end the game
    // Method to switch turns
    // ...
}
