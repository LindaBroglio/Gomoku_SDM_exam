package it.units.gomokutest;

import it.units.gomoku.Board;
import it.units.gomoku.utilities.Color;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {
    private Board board;
    @BeforeEach
    void initBoard(){
        board = new Board(5, 3);
    }

    @Test
    void isBoardSizeCorrect() {
        assertEquals(board.getSize(), board.getGrid().length);
        assertEquals(board.getSize(), board.getGrid()[0].length);
    }

    @Test
    void isANodeIsEmptyAfterInitialization() {
        assertEquals(Color.EMPTY, board.getGrid()[0][0].getColor());
    }

    @Test
    void placeOneStone() {
        board.placeStoneAtPosition(0, 0, Color.BLACK);
        assertEquals(Color.BLACK, board.getGrid()[0][0].getColor());
    }

    @Test
    void doEnoughConsecutiveHorizontalSameColorStonesMakeYouWin() {
        for (int j = 0; j < board.getHowManyToWin(); j++) {
            board.placeStoneAtPosition(0, j, Color.BLACK);
        }
        assertTrue(board.isCurrentStonePartOfAWinningStreak(0, board.getHowManyToWin()));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void doesHorizontalCheckWorkWithFullFirstRow(int index) {
        for (int j = 0; j < board.getSize(); j++) {
            board.placeStoneAtPosition(0, j, Color.BLACK);
        }
        assertTrue(board.isCurrentStonePartOfAWinningStreak(0, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void doesHorizontalCheckWorkWhenNotAWinningStreak(int index) {
        for (int j = 0; j < board.getSize(); j++) {
            if (j % 2 == 0) board.placeStoneAtPosition(0, j, Color.BLACK);
            else board.placeStoneAtPosition(0, j, Color.WHITE);
        }
        assertFalse(board.isCurrentStonePartOfAWinningStreak(0, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void doesVerticalCheckWorksWithFullFirstColumn(int index) {
        for (int i = 0; i < board.getSize(); i++) {
            board.placeStoneAtPosition(i, 0, Color.BLACK);
        }
        assertTrue(board.isCurrentStonePartOfAWinningStreak(index, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void doesVerticalCheckWorksWithPartiallyFullFirstColumn(int index) {
        board.placeStoneAtPosition(0, 0, Color.EMPTY);
        board.placeStoneAtPosition(1, 0, Color.BLACK);
        board.placeStoneAtPosition(2, 0, Color.BLACK);
        board.placeStoneAtPosition(3, 0, Color.BLACK);
        board.placeStoneAtPosition(4, 0, Color.WHITE);
        assertTrue(board.isCurrentStonePartOfAWinningStreak(index, 0));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3})
    void doesDiagonalCheckWorkWhenFullyInsideTheBoard(int index) {
        board.placeStoneAtPosition(1, 1, Color.BLACK);
        board.placeStoneAtPosition(2, 2, Color.BLACK);
        board.placeStoneAtPosition(3, 3, Color.BLACK);
        assertTrue(board.isCurrentStonePartOfAWinningStreak(index, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2})
    void doesDiagonalCheckWorkWhenCloseToTheEdge(int index) {
        board.placeStoneAtPosition(0, 0, Color.BLACK);
        board.placeStoneAtPosition(1, 1, Color.BLACK);
        board.placeStoneAtPosition(2, 2, Color.BLACK);
        assertTrue(board.isCurrentStonePartOfAWinningStreak(index, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void doesOffDiagonalCheckWork(int index) {
        board.placeStoneAtPosition(4, 0, Color.BLACK);
        board.placeStoneAtPosition(3, 1, Color.BLACK);
        board.placeStoneAtPosition(2, 2, Color.BLACK);
        assertTrue(board.isCurrentStonePartOfAWinningStreak(4 - index, index));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void doesDiagonalCheckWorkWithCheckeredPattern(int index) {
        for (int i = 0; i < board.getSize(); i++)
            for (int j = 0; j < board.getSize(); j++)
                if ((i + j) % 2 == 0) board.placeStoneAtPosition(i, j, Color.WHITE);
                else board.placeStoneAtPosition(i, j, Color.BLACK);
        assertTrue(board.isCurrentStonePartOfAWinningStreak(0,index));
    }

    @Test
    void emptySquareFullSquareTest() {
        // first row
        board.placeStoneAtPosition(1, 1, Color.BLACK);
        board.placeStoneAtPosition(1, 2, Color.BLACK);
        board.placeStoneAtPosition(1, 3, Color.BLACK);
        // second row
        board.placeStoneAtPosition(2, 1, Color.BLACK);
        // center is empty
        board.placeStoneAtPosition(2, 3, Color.BLACK);
        // third row
        board.placeStoneAtPosition(3, 1, Color.BLACK);
        board.placeStoneAtPosition(3, 2, Color.BLACK);
        board.placeStoneAtPosition(3, 3, Color.BLACK);
        assertFalse(board.isCurrentStonePartOfAWinningStreak(2,2));
        // fill center
        board.placeStoneAtPosition(2, 2, Color.BLACK);
        assertTrue(board.isCurrentStonePartOfAWinningStreak(2,2));
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 1, 2, 3, 4})
    void checkIfMultipleWinningSequencesCauseIssues(int index) {
        // Horizontal winning sequence
        board.placeStoneAtPosition(0, 0, Color.BLACK);
        board.placeStoneAtPosition(0, 1, Color.BLACK);
        board.placeStoneAtPosition(0, 2, Color.BLACK);
        // Vertical winning sequence
        board.placeStoneAtPosition(0, 4, Color.BLACK);
        board.placeStoneAtPosition(1, 4, Color.BLACK);
        board.placeStoneAtPosition(2, 4, Color.BLACK);
        // Diagonal winning sequence
        board.placeStoneAtPosition(2, 0, Color.BLACK);
        board.placeStoneAtPosition(3, 1, Color.BLACK);
        board.placeStoneAtPosition(4, 2, Color.BLACK);
        assertTrue(board.isCurrentStonePartOfAWinningStreak(index, index));
    }

    @Test
    void DisplayBoardTest() {
        board.placeStoneAtPosition(0, 0, Color.BLACK);
        board.placeStoneAtPosition(0, 1, Color.WHITE);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        board.display();
        String ls = System.lineSeparator();
        String expectedOutput =
                "   1  2  3  4  5" + ls +
                "1  B  W  ·  ·  ·  1" + ls +
                "2  ·  ·  ·  ·  ·  2" + ls +
                "3  ·  ·  ·  ·  ·  3" + ls +
                "4  ·  ·  ·  ·  ·  4" + ls +
                "5  ·  ·  ·  ·  ·  5" + ls +
                "   1  2  3  4  5" + ls;
        assertEquals(expectedOutput, outContent.toString());
    }

}
