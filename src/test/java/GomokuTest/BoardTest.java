package GomokuTest;

import Gomoku.Board;
import Gomoku.Color;
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
    public void placeStoneTest() {
        Board board = new Board(5);
        board.placeStone(0, 0, Color.BLACK);
        assertEquals(Color.BLACK, board.getGrid()[0][0].getColor());
    }

    @Test
    public void DisplayBoardTest() {
        // Create a new board
        Board board = new Board(5);
        board.placeStone(0, 0, Color.BLACK);
        board.placeStone(0, 1, Color.WHITE);

        // Prepare to capture the console output
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call the method
        board.displayBoard();

        // Check the output
        String ls = System.lineSeparator();
        String expectedOutput = "B W . . . " + ls +
                                ". . . . . " + ls +
                                ". . . . . " + ls +
                                ". . . . . " + ls +
                                ". . . . . " + ls;
        assertEquals(expectedOutput, outContent.toString());
    }
}