package GomokuTest;

import Gomoku.Board;
import Gomoku.Exceptions.InputExceptions.QuitGameException;
import Gomoku.Game;
import Gomoku.utilities.InputValidator;
import Gomoku.utilities.Move;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

class MoveTest {

    void setFakeStdInput(String fakeInput) {
        ByteArrayInputStream fakeStandardInput = new ByteArrayInputStream(fakeInput.getBytes());
        System.setIn(fakeStandardInput);
    }

    @BeforeEach
    @Disabled
    void setUp() {
        String inputData = "5\n3\n";
        InputStream in = new ByteArrayInputStream(inputData.getBytes());
        System.setIn(in);

        //game = new Game();
        //board = game.getBoard();
    }

    @Test
    @Disabled
    void isQuitGameExceptionThrownIfInputIsQuit() {
        //setFakeStdInput("quit" + System.lineSeparator());
        //InputValidator inputValidator = new InputValidator(1);
        //assertThrows(QuitGameException.class, inputValidator.validateInput("quit"));
    }

    @Test
    @Disabled
    void displayTheCorrectNextTurnMessage() {
        //move.PromptNextTurn(1);
        String expectedOutput = "Black turn: enter your move (e.g. 3 4):" + System.lineSeparator();
        //assertEquals(expectedOutput, outContent.toString());
    }

    @AfterEach
    @Disabled
    public void restoreStreams() {
        //System.setOut(originalOut);
    }
}
