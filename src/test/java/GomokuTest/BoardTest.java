package GomokuTest;

import Gomoku.Board;
import Gomoku.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {
    @Test
    public void testBoard() {
        Board board = new Board(5);
        assertEquals(5, board.getGrid().length);
        assertEquals(5, board.getGrid()[0].length);
        assertEquals(Color.EMPTY, board.getGrid()[0][0].getColor());
    }
}