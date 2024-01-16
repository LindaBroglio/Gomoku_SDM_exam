package it.units.gomokutest;

import it.units.gomoku.CLI;
import it.units.gomoku.exceptions.GameWonException;
import it.units.gomoku.exceptions.inputexceptions.*;
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
    void invalidFormatGameSpecificationDoesNotThrow() {
        String simulatedUserInput =
                "hello there" + System.lineSeparator() +
                        "8 5" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> new CLI(scanner));
    }

    @Test
    void validFormatButIllegalSpecificationDoesNotThrow() {
        String simulatedUserInput =
                "5 8" + System.lineSeparator() +
                        "8 5" + System.lineSeparator();
        InputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> new CLI(scanner));
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

    @Test
    void inputGameSpecificationsAndWinningSequence() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "1 1" + System.lineSeparator() +
                        "2 1" + System.lineSeparator() +
                        "1 2" + System.lineSeparator() +
                        "2 2" + System.lineSeparator() +
                        "1 3" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertTrue(cli.isBlackTurn());
        for (int i = 0; i < 4; i++) assertDoesNotThrow(() -> cli.makeMove());
        assertThrows(GameWonException.class, () -> cli.makeMove());
        assertTrue(cli.isBlackTurn()); // Turn is not changed after winning
        assertEquals(5, cli.getMoveCount()); // Move is not increased after winning
    }

    @Test
    void playAGameWhereBlackWins() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "1 1" + System.lineSeparator() +
                        "2 1" + System.lineSeparator() +
                        "1 2" + System.lineSeparator() +
                        "2 2" + System.lineSeparator() +
                        "1 3" + System.lineSeparator() +
                        "no" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertDoesNotThrow(() -> cli.playFromCommandLine());
    }

    @Test
    void playAGameWhereBlackQuits() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "1 1" + System.lineSeparator() +
                        "2 1" + System.lineSeparator() +
                        "quit" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertDoesNotThrow(() -> cli.playFromCommandLine());
    }

    @Test
    void playAGameWhereWhiteIsNotAbleToMakeACorrectMoveAndGivesUp() {
        String simulatedUserInput =
                "5 3" + System.lineSeparator() +
                        "1 1" + System.lineSeparator() +
                        "0 5" + System.lineSeparator() +
                        "why" + System.lineSeparator() +
                        "1 2 3" + System.lineSeparator() +
                        "quit" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertDoesNotThrow(() -> cli.playFromCommandLine());
    }

    @Test
    void playADrawnGame() {
        String simulatedUserInput =
                "3 3" + System.lineSeparator() +
                        "1 1" + System.lineSeparator() +
                        "1 3" + System.lineSeparator() +
                        "1 2" + System.lineSeparator() +
                        "2 1" + System.lineSeparator() +
                        "2 2" + System.lineSeparator() +
                        "3 2" + System.lineSeparator() +
                        "2 3" + System.lineSeparator() +
                        "3 3" + System.lineSeparator() +
                        "3 1" + System.lineSeparator();
        ByteArrayInputStream in = new ByteArrayInputStream(simulatedUserInput.getBytes());
        Scanner scanner = new Scanner(in);
        assertDoesNotThrow(() -> cli = new CLI(scanner));
        assertDoesNotThrow(() -> cli.playFromCommandLine());
    }
}

