package GomokuTest;

import Gomoku.Game;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    @Test
    public void testGame() {
        Game game = new Game(5, 3);
        assertNotNull(game.getBoard());
    }
}
