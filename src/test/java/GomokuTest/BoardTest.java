package GomokuTest;

import Gomoku.Board;
import Gomoku.Color;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testBoard() {
        Board board = new Board(5);
        assertEquals(5, board.getGrid().length);
        assertEquals(5, board.getGrid()[0].length);
        assertEquals(Color.EMPTY, board.getGrid()[0][0].getColor());
    }

    @Test
    public void testChangingNodeColor() {
        Board board = new Board(5);
        board.getGrid()[0][0].setColor(Color.BLACK);
        assertEquals(Color.BLACK, board.getGrid()[0][0].getColor());
    }

    @Test
    public void placeStoneTest() throws OutOfBoardException, TakenNodeException {
        Board board = new Board(5);
        board.placeStone(0, 0, Color.BLACK);
        assertEquals(Color.BLACK, board.getGrid()[0][0].getColor());
    }

    @Test
    public void placeStoneOutOfBoundsTest() {
        Board board = new Board(5);
        Assertions.assertThrows(OutOfBoardException.class, () -> board.placeStone(6, 6, Color.BLACK));
    }

    @Test
    public void placeStoneTakenNodeTest() {
        Board board = new Board(5);
        Assertions.assertThrows(TakenNodeException.class, () -> {
            board.placeStone(0, 0, Color.BLACK);
            board.placeStone(0, 0, Color.WHITE);
        });
    }

    @Test
    public void DisplayBoardTest() throws OutOfBoardException, TakenNodeException {
        Board board = new Board(5);
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