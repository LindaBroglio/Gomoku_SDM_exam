package GomokuTest;

import Gomoku.CLI;
import Gomoku.Exceptions.InputExceptions.*;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class CLITest {

    private CLI cli;

    @Test
    void inputQuitCommand() {
        String simulatedUserInput = "quit" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertThrows(QuitException.class, () -> new CLI(scanner));
    }

    @Test
    void inputCorrectGameSpecifications() {
        String simulatedUserInput = "5 3" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> new CLI(scanner));
    }

    @Test
    void inputCorrectGameSpecificationsAndQuit() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "quit" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertThrows(ResignException.class, () -> cli.makeMove());
    }

    @Test
    void inputGameSpecificationsAndInvalidInput() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "hello there" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertThrows(InvalidFormatException.class, () -> cli.makeMove());
    }

    @Test
    void inputGameSpecificationsAndOutOfBoardMove() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "-1 2" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertThrows(OutOfBoardException.class, () -> cli.makeMove());
    }

    @Test
    void inputGameSpecificationsAndLegalMove() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "1 2" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertDoesNotThrow(() -> cli.makeMove());
    }

    @Test
    void inputGameSpecificationsAndSameLegalMoveTwice() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "3 4" + System.lineSeparator() +
                        "3 4" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertDoesNotThrow(() -> cli.makeMove());
        assertThrows(TakenNodeException.class, () -> cli.makeMove());
    }
}

