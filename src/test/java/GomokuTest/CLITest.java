package GomokuTest;

import Gomoku.CLI;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.Game;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

public class CLITest {

    @Test
    void inputQuitCommand() throws QuitException {
        String simulatedUserInput = "quit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Integer[] gameSpecification = {15, 5};
        Game game = new Game(gameSpecification);
        CLI cli = new CLI();
        assertThrows(QuitException.class, cli::playFromCommandLine);
    }

    // Additional tests for CLI class...
}

