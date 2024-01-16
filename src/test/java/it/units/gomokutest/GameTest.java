package it.units.gomokutest;

import it.units.gomoku.exceptions.GameWonException;
import it.units.gomoku.exceptions.inputexceptions.OutOfBoardException;
import it.units.gomoku.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class GameTest {

    private Game game;

    @BeforeEach
    void setUp() {
        Integer[] gameSpecification = new Integer[]{5, 3};
        game = new Game(gameSpecification);
    }

    @Test
    void validMoveShouldNotThrowExceptionsAndIncreaseMoveCount() {
        Integer[] coordinates = {1, 1};
        assertDoesNotThrow(() -> game.makeMove(coordinates));
        assertEquals(1, game.getMoveCount());
    }

    @Test
    void invalidMoveShouldThrowExceptionAndNotIncreaseMoveCount() {
        assertDoesNotThrow(() -> game.makeMove(new Integer[]{2, 2}));
        assertThrows(OutOfBoardException.class, () -> game.makeMove(new Integer[]{-1, 7}));
        assertEquals(1, game.getMoveCount());
    }

    @Test
    void validMoveShouldChangeTurn() {
        Integer[] coordinates = {1, 1};
        assertTrue(game.isBlackTurn());
        assertDoesNotThrow(() -> game.makeMove(coordinates));
        assertFalse(game.isBlackTurn());
    }

    @Test
    void winningMoveSequenceShouldThrowGameWonException() {
        assertThrows(GameWonException.class, (() -> {
            for (int i = 1; i < 4; i++) {
                game.makeMove(new Integer[]{i, 1});
                game.makeMove(new Integer[]{i, 2});
            }
        }));
    }

    @Test
    void boardFullButNoWinner() {
        Integer[] gameSpecification = new Integer[]{3, 3};
        game = new Game(gameSpecification);
        assertDoesNotThrow(() -> {
            game.makeMove(new Integer[]{1, 1});
            game.makeMove(new Integer[]{1, 3});
            game.makeMove(new Integer[]{1, 2});
            game.makeMove(new Integer[]{2, 1});
            game.makeMove(new Integer[]{2, 2});
            game.makeMove(new Integer[]{3, 2});
            game.makeMove(new Integer[]{2, 3});
            game.makeMove(new Integer[]{3, 3});
            game.makeMove(new Integer[]{3, 1});
        });
        assertTrue(game.boardIsFull());
    }
}
