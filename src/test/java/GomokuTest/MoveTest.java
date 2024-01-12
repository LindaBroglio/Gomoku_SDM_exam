package GomokuTest;

import Gomoku.Board;
import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    private Move move;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    @BeforeEach
    public void setUp() {
        Board board = new Board(5, 3);
        move = new Move(board);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    @Test
    public void displayCorrectBlackTurnMessage() {
        String expectedOutput = "Black turn: enter your move (e.g. 3 4):" + System.lineSeparator();
        move.promptNextTurn(1);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void displayCorrectWhiteTurnMessage() {
        String expectedOutput = "White turn: enter your move (e.g. 3 4):" + System.lineSeparator();
        move.promptNextTurn(2);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void quitCommandThrowsQuitGameException() {
        String input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThrows(QuitGameException.class, () -> move.readMove(1));
    }

    @Test
    public void validInputDoesNotThrowExceptions() {
        String input = "3 3" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> move.readMove(1));
    }

    @Test
    public void invalidInputThrowsInvalidFormatException() {
        String input = "hello there" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThrows(InvalidFormatException.class, () -> move.readMove(1));
    }

    @ParameterizedTest
    @CsvSource({"7 7", "-1 2", "-2 -8", "0 5"})
    public void movesOutsideOfTheBoardThrowOutOfBoardException(String string) throws QuitGameException, InvalidFormatException {
        String input = string + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(1);
        assertThrows(OutOfBoardException.class, () -> move.makeMove(1));
    }

    @Test
    void repeatedMoveThrowsTakenNodeException() throws QuitGameException, InvalidFormatException {
        String input = "2 2" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(1);
        assertDoesNotThrow(() -> move.makeMove(1));
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(2);
        assertThrows(TakenNodeException.class, () -> move.makeMove(2));
    }

    @Test
    void checkingWinnerWhenNoWinDoesNotThrowGameWonException() throws QuitGameException, InvalidFormatException, TakenNodeException, OutOfBoardException {
        String input = "1 1" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(1);
        move.makeMove(1);
        input = "1 2" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(2);
        move.makeMove(2);
        assertDoesNotThrow(() -> move.checkWinner(2));
    }

    @Test
    void checkingWinnerWhenWinThrowsGameWonException() throws QuitGameException, InvalidFormatException, TakenNodeException, OutOfBoardException {
        String str, input;
        for (int i = 1; i < 4; i++) {
            str = String.valueOf(i);
            input = str + " " + str + System.lineSeparator();
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            move.readMove(1);
            move.makeMove(1);
        }
        assertThrows(GameWonException.class, () -> move.checkWinner(1));
    }
}
