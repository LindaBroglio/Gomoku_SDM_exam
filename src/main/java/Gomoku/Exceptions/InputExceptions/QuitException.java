package Gomoku.Exceptions.InputExceptions;

public class QuitException extends Exception {
    private static final String RESIGN_MESSAGE = "You decided to exit from the game, ciao!";

    public QuitException() {
        super(RESIGN_MESSAGE);
    }
}
