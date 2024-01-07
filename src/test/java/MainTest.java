import Gomoku.Exceptions.InputExceptions.TakenNodeException;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    @Test
    public void testMain() throws TakenNodeException {
        String input = "quit\n"; // The input data for the test
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in); // Set System.in to the test input data

        Main.main(new String[]{});

        assertTrue(true);
    }
}
