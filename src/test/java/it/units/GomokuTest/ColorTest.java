package it.units.GomokuTest;

import it.units.Gomoku.utilities.Color;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ColorTest {
    @Test
    void testColorValues() {
        assertEquals(0, Color.EMPTY.ordinal());
        assertEquals(1, Color.BLACK.ordinal());
        assertEquals(2, Color.WHITE.ordinal());
    }
}

