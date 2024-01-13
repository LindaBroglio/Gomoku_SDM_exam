import Gomoku.Exceptions.InputExceptions.QuitException;
import Gomoku.Game;

public class Main {
    public static void main(String[] args) {
        try {
            Game game = new Game();
            game.play();
        } catch (QuitException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
    }
}

