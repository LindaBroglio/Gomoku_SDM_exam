package GomokuTest;

import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.Game;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;
public class GameTest {

    @Test
    void inputQuitCommand() {
        String simulatedUserInput = "quit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        assertThrows(QuitException.class, () -> new Game(in));
    }

    @Test
    void inputGameSpecifications() {
        String simulatedUserInput = "5 3" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        assertDoesNotThrow(() -> new Game(in));
    }

    @Test
    void inputGameSpecificationsAndQuit() throws QuitException {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                "quit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Game game = new Game(in);
        assertThrows(ResignException.class, game::makeAMove);
    }

    @Test
    void inputGameSpecificationsAndLegalMove() throws QuitException {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                "4 4" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Game game = new Game(in);
        assertDoesNotThrow(game::makeAMove);
    }

    @Test
    void inputGameSpecificationsAndInvalidInput() throws QuitException {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                "hello there" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Game game = new Game(in);
        assertThrows(InvalidFormatException.class, game::makeAMove);
    }

    @Test
    void inputGameSpecificationsAndIllegalMove() throws QuitException {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                "-2 13" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Game game = new Game(in);
        assertThrows(OutOfBoardException.class, game::makeAMove);
    }

    @Test
    void inputGameSpecificationsLegalMoveAndSameMoveAgain() throws QuitException {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                "2 2" + System.lineSeparator() +
                "2 2" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Game game = new Game(in);
        assertThrows(TakenNodeException.class, () -> {
            game.makeAMove();
            game.makeAMove();
        });
    }
}
