package it.units.GomokuTest;

import it.units.Gomoku.Exceptions.InputExceptions.InvalidFormatException;
import it.units.Gomoku.Exceptions.InputExceptions.QuitException;
import it.units.Gomoku.utilities.InputValidator;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;

class InputTest {
    private InputValidator validator;

    @BeforeEach
    void setUp() {
        validator = new InputValidator(true, 0);
    }

    @ParameterizedTest
    @CsvSource({"1 2", "-1 -3", "8 8"})
    void doesValidInputThrowException(String string) {
        assertDoesNotThrow(() -> validator.validateInput(string));
    }

    @ParameterizedTest
    @CsvSource({"8", "a", "ab", "ab c", "1 2 3", "."})
    void doesInvalidInputThrowException(String string) {
        assertThrows(InvalidFormatException.class, () -> validator.validateInput(string));
    }

    @Test
    void doesQuitCommandThrowQuitGameException() {
        assertThrows(QuitException.class, () -> validator.validateInput("quit"));
    }
}
