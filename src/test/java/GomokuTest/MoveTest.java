package GomokuTest;

import Gomoku.Board;
import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
        move.promptNextTurn(true);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void displayCorrectWhiteTurnMessage() {
        String expectedOutput = "White turn: enter your move (e.g. 3 4):" + System.lineSeparator();
        move.promptNextTurn(false);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void quitCommandThrowsQuitGameException() {
        String input = "quit\n";
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThrows(ResignException.class, () -> move.readMove(true, 1));
    }

    @Test
    public void validInputDoesNotThrowExceptions() {
        String input = "3 3" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertDoesNotThrow(() -> move.readMove(false, 2));
    }

    @Test
    public void invalidInputThrowsInvalidFormatException() {
        String input = "hello there" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        assertThrows(InvalidFormatException.class, () -> move.readMove(true, 1));
    }

    @ParameterizedTest
    @CsvSource({"7 7", "-1 2", "-2 -8", "0 5"})
    public void movesOutsideOfTheBoardThrowOutOfBoardException(String string) throws ResignException, InvalidFormatException {
        String input = string + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(true, 1);
        assertThrows(OutOfBoardException.class, () -> move.makeMove(true));
    }

    @Test
    void repeatedMoveThrowsTakenNodeException() throws ResignException, InvalidFormatException {
        String input = "2 2" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(true, 1);
        assertDoesNotThrow(() -> move.makeMove(true));
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(false, 2);
        assertThrows(TakenNodeException.class, () -> move.makeMove(false));
    }

    @Test
    void checkingWinnerWhenNoWinDoesNotThrowGameWonException() throws ResignException, InvalidFormatException, TakenNodeException, OutOfBoardException {
        String input = "1 1" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(true, 1);
        move.makeMove(true);
        input = "1 2" + System.lineSeparator();
        System.setIn(new ByteArrayInputStream(input.getBytes()));
        move.readMove(false, 2);
        move.makeMove(false);
        assertDoesNotThrow(() -> move.checkWinner(false));
    }

    @Test
    void checkingWinnerWhenWinThrowsGameWonException() throws ResignException, InvalidFormatException, TakenNodeException, OutOfBoardException {
        String str, input;
        for (int i = 1; i < 4; i++) {
            str = String.valueOf(i);
            input = str + " " + str + System.lineSeparator();
            System.setIn(new ByteArrayInputStream(input.getBytes()));
            move.readMove(true, 1);
            move.makeMove(true);
        }
        assertThrows(GameWonException.class, () -> move.checkWinner(true));
    }
}
