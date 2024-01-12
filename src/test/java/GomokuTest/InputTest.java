package GomokuTest;

import Gomoku.*;
import Gomoku.Exceptions.InputExceptions.*;
import Gomoku.utilities.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InputTest {
    private InputValidator validator;

    @BeforeEach
    public void setUp() {
        validator = new InputValidator(1);
    }

    @ParameterizedTest
    @CsvSource({"1 2", "-1 -3", "8 8"})
    public void doesValidInputThrowException(String string) {
        assertDoesNotThrow(() -> validator.validateInput(string));
    }

    @ParameterizedTest
    @CsvSource({"8", "a", "ab", "ab c", "1 2 3", "."})
    public void doesInvalidInputThrowException(String string) {
        assertThrows(InvalidFormatException.class, () -> validator.validateInput(string));
    }

    @Test
    public void doesQuitCommandThrowQuitGameException() {
        assertThrows(QuitGameException.class, () -> validator.validateInput("quit"));
    }
}