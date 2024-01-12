import Gomoku.Exceptions.InputExceptions.QuitException;
import Gomoku.Game;

public class Main {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.play();
        } catch (QuitException e) {
            // Handle the QuitException (if needed)
            System.out.println(e.getMessage());
            // Exit the program without errors
            System.exit(0);
        }
    }
}

