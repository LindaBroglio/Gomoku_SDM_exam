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
    @ValueSource(ints = {0, 1, 2})
    void checkIfVerticalCheckWorks(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(1, 0, Color.BLACK);
        board.placeStone(2, 0, Color.BLACK);
        board.placeStone(3, 0, Color.WHITE);
        board.placeStone(4, 0, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(index, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void checkIfDiagonalCheckWorksWhenFullyInsideTheBoard(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(1, 1, Color.BLACK);
        board.placeStone(2, 2, Color.BLACK);
        board.placeStone(3, 3, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(index, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void checkIfDiagonalCheckWorksWhenCloseToTheEdge(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(1, 1, Color.BLACK);
        board.placeStone(2, 2, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(index, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void checkIfOffDiagonalCheckWorks(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        board.placeStone(4, 0, Color.BLACK);
        board.placeStone(3, 1, Color.BLACK);
        board.placeStone(2, 2, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(4 - index, index));
    }

    @Test
    void checkeredPatternTest() throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        for (int i = 0; i < board.boardSize(); i++)
            for (int j = 0; j < board.boardSize(); j++)
                if ((i + j) % 2 == 0) board.placeStone(i, j, Color.WHITE);
                else board.placeStone(i, j, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(0,0));
        Assertions.assertTrue(board.enoughConsecutive(0,1));
        Assertions.assertTrue(board.enoughConsecutive(0,3));
        Assertions.assertTrue(board.enoughConsecutive(0,4));
        Assertions.assertTrue(board.enoughConsecutive(4,0));
        Assertions.assertTrue(board.enoughConsecutive(4,1));
        Assertions.assertTrue(board.enoughConsecutive(4,3));
        Assertions.assertTrue(board.enoughConsecutive(4,4));
        Assertions.assertTrue(board.enoughConsecutive(2,2));




    }

    @Test
    void emptySquareFullSquareTest() throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        // first row
        board.placeStone(1, 1, Color.BLACK);
        board.placeStone(1, 2, Color.BLACK);
        board.placeStone(1, 3, Color.BLACK);
        // second row
        board.placeStone(2, 1, Color.BLACK);
        // center is empty
        board.placeStone(2, 3, Color.BLACK);
        // third row
        board.placeStone(3, 1, Color.BLACK);
        board.placeStone(3, 2, Color.BLACK);
        board.placeStone(3, 3, Color.BLACK);
        Assertions.assertFalse(board.enoughConsecutive(2,2));
        // fill center
        board.placeStone(2, 2, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(2,2));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void checkIfMultipleWinningSequencesWork(int index) throws TakenNodeException, OutOfBoardException {
        Board board = new Board(5, 3);
        // Horizontal winning sequence
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(0, 1, Color.BLACK);
        board.placeStone(0, 2, Color.BLACK);
        // Vertical winning sequence
        board.placeStone(0, 4, Color.BLACK);
        board.placeStone(1, 4, Color.BLACK);
        board.placeStone(2, 4, Color.BLACK);
        // Diagonal winning sequence
        board.placeStone(2, 0, Color.BLACK);
        board.placeStone(3, 1, Color.BLACK);
        board.placeStone(4, 2, Color.BLACK);
        Assertions.assertTrue(board.enoughConsecutive(index, index));
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