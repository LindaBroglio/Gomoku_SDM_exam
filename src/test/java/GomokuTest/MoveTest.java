package GomokuTest;

import Gomoku.Board;
import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.Move;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    private Move move;
    private static final Boolean blackTurn = true;
    private static final Boolean whiteTurn = false;

    @BeforeEach
    void setUp() {
        Board board = new Board(5, 3);
        move = new Move(board);
    }

    @Test
    void validMoveDoesNotThrowExceptions() {
        assertDoesNotThrow(() -> {
            move.setX(2);
            move.setY(2);
            move.tryToPlaceStone(blackTurn);
        });
    }

    @ParameterizedTest
    @CsvSource({"0, 5", "16, 5", "5, 0", "5, 16", "-1, 5", "5, -1", "30, 5", "5, 30"})
    void outOfBoundsMoveThrowsException(int x, int y) {
        move.setX(x);
        move.setY(y);
        assertThrows(OutOfBoardException.class, () -> move.tryToPlaceStone(blackTurn));
    }

    @Test
    void sameMoveTwiceThrowsException() {
        move.setX(5);
        move.setY(5);
        assertDoesNotThrow(() -> move.tryToPlaceStone(blackTurn));
        assertThrows(TakenNodeException.class, () -> move.tryToPlaceStone(whiteTurn));
    }

    @Test
    void checkingWinnerWhenNotWinningMoveShouldNotThrowException() {
        move.setX(5);
        move.setY(5);
        assertDoesNotThrow(() -> {
            move.tryToPlaceStone(blackTurn);
            move.checkForWinner(blackTurn);
        });
    }

    @Test
    void checkingWinnerWhenWinningMoveThrowsException() {
        for (int i = 1; i <= 5; i++) {
            move.setX(i);
            move.setY(5);
        }
        assertThrows(GameWonException.class, () -> move.checkForWinner(blackTurn));
    }
}
