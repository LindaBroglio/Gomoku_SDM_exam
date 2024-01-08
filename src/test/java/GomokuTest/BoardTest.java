package GomokuTest;

import Gomoku.Board;
import Gomoku.Color;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testBoard() {
        Board board = new Board(5, 3);
        assertEquals(5, board.getGrid().length);
        assertEquals(5, board.getGrid()[0].length);
        assertEquals(Color.EMPTY, board.getGrid()[0][0].getColor());
    }

    @Test
    public void testChangingNodeColor() {
        Board board = new Board(5, 3);
        board.getGrid()[0][0].setColor(Color.BLACK);
        assertEquals(Color.BLACK, board.getGrid()[0][0].getColor());
    }

    @Test
    public void placeStoneTest() throws OutOfBoardException, TakenNodeException {
        Board board = new Board(5, 3);
        board.placeStone(0, 0, Color.BLACK);
        assertEquals(Color.BLACK, board.getGrid()[0][0].getColor());
    }

    @Test
    public void placeStoneOutOfBoundsTest() {
        Board board = new Board(5, 3);
        Assertions.assertThrows(OutOfBoardException.class, () -> board.placeStone(6, 6, Color.BLACK));
    }

    @Test
    public void placeStoneTakenNodeTest() {
        Board board = new Board(5, 3);
        Assertions.assertThrows(TakenNodeException.class, () -> {
            board.placeStone(0, 0, Color.BLACK);
            board.placeStone(0, 0, Color.WHITE);
        });
    }

    @Test
    void checkIfEnoughConsecutiveHorizontalSameColorStonesMakeYouWin() throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(0, 1, Color.WHITE);
        board.placeStone(0, 2, Color.BLACK);
        board.placeStone(0, 3, Color.BLACK);
        board.placeStone(0, 4, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(0, 2));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void checkIfHorizontalCheckWorksWithFullFirstRow(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(0, 1, Color.BLACK);
        board.placeStone(0, 2, Color.BLACK);
        board.placeStone(0, 3, Color.BLACK);
        board.placeStone(0, 4, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(0, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void checkIfVerticalCheckWorksWithFullFirstRow(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(1, 0, Color.BLACK);
        board.placeStone(2, 0, Color.BLACK);
        board.placeStone(3, 0, Color.BLACK);
        board.placeStone(4, 0, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(index, 0));
    }

    @Disabled
    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void checkIfFirstDiagonalCheckWorksWithFullFirstRow(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(1, 0, Color.BLACK);
        board.placeStone(2, 1, Color.BLACK);
        board.placeStone(3, 2, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(index +1, index));
    }

    @Test
    public void DisplayBoardTest() throws OutOfBoardException, TakenNodeException {
        Board board = new Board(5, 3);
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(0, 1, Color.WHITE);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        board.displayBoard();
        String ls = System.lineSeparator();
        String expectedOutput = "B W . . . " + ls +
                                ". . . . . " + ls +
                                ". . . . . " + ls +
                                ". . . . . " + ls +
                                ". . . . . " + ls;
        assertEquals(expectedOutput, outContent.toString());
    }
}