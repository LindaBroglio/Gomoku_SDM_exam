package GomokuTest;

import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Game;
import Gomoku.utilities.Move;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoveTest {

    private Move move;
    private Game game;

    @Test
    @Disabled
    public void testMove_ValidMove() {
        assertDoesNotThrow(() -> {
            move.promptNextTurn(1);
            move.readMove(1);
            move.makeMove(1);
        });
    }

    @Test
    @Disabled
    public void testMove_OutOfBounds() {
        assertThrows(OutOfBoardException.class, () -> {
            move.promptNextTurn(1);
            move.readMove(1);
            move.makeMove(1);
        });
    }

    @Test
    @Disabled
    public void testGame_StartGame() {
        assertDoesNotThrow(() -> game.startGame());
    }
}
