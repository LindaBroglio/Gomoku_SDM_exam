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

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

public class MoveTest {

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private Board board;

    @BeforeEach
    public void setUp() {
        board = new Board(5, 3);
        System.setOut(new PrintStream(outContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
    }

    private Move createMoveWithInput(String input) {
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        return new Move(board, inContent);
    }

    @Test
    public void displayCorrectBlackTurnMessage() {
        Move move = createMoveWithInput("3 4" + System.lineSeparator());
        String expectedOutput = "Black turn: enter your move (e.g. 3 4):" + System.lineSeparator();
        move.promptNextTurn(true);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void displayCorrectWhiteTurnMessage() {
        Move move = createMoveWithInput("3 4" + System.lineSeparator());
        String expectedOutput = "White turn: enter your move (e.g. 3 4):" + System.lineSeparator();
        move.promptNextTurn(false);
        assertEquals(expectedOutput, outContent.toString());
    }

    @Test
    public void quitCommandOnMoveOneThrowsResignException() {
        Move move = createMoveWithInput("quit" + System.lineSeparator());
        assertThrows(ResignException.class, () -> move.readMove(true, 1));
    }

    @Test
    public void validInputDoesNotThrowExceptions() {
        Move move = createMoveWithInput("3 3" + System.lineSeparator());
        assertDoesNotThrow(() -> move.readMove(false, 2));
    }

    @Test
    public void invalidInputThrowsInvalidFormatException() {
        Move move = createMoveWithInput("hello there" + System.lineSeparator());
        assertThrows(InvalidFormatException.class, () -> move.readMove(true, 1));
    }

    @ParameterizedTest
    @CsvSource({"7 7", "-1 2", "-2 -8", "0 5"})
    public void movesOutsideOfTheBoardThrowOutOfBoardException(String string) throws ResignException, InvalidFormatException {
        Move move = createMoveWithInput(string + System.lineSeparator());
        move.readMove(true, 1);
        assertThrows(OutOfBoardException.class, () -> move.makeMove(true));
    }

    @Test
    public void repeatedMoveThrowsTakenNodeException() throws ResignException, InvalidFormatException, TakenNodeException, OutOfBoardException {
        String inputMove = "2 2" + System.lineSeparator();
        Move firstMove = createMoveWithInput(inputMove);
        firstMove.readMove(true, 1);
        firstMove.makeMove(true);
        Move secondMove = createMoveWithInput(inputMove);
        assertThrows(TakenNodeException.class, () -> {
            secondMove.readMove(false, 2);
            secondMove.makeMove(false);
        });
    }

    @Test
    public void checkingWinnerWhenWinThrowsGameWonException() throws ResignException, InvalidFormatException, TakenNodeException, OutOfBoardException {
        Move[] moveHolder = new Move[1];
        for (int i = 1; i < 4; i++) {
            String input = i + " " + i + System.lineSeparator();
            moveHolder[0] = createMoveWithInput(input);
            moveHolder[0].readMove(true, i);
            moveHolder[0].makeMove(true);
        }
        assertThrows(GameWonException.class, () -> moveHolder[0].checkWinner(true));
    }
}
