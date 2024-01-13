package GomokuTest;

import Gomoku.Board;
import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.Game;
import Gomoku.utilities.Move;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;
public class GameTest {

    @Test
    void doesQuitCommandThrowQuitGameException() {
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
        String simulatedUserInput = "5 3" + System.lineSeparator() + "quit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Game game = new Game(in);
        assertThrows(ResignException.class, game::play);
    }
}
