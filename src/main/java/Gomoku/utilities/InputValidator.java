package Gomoku.utilities;

import Gomoku.Exceptions.InputExceptions.InvalidFormatException;
import Gomoku.Exceptions.InputExceptions.QuitGameException;

import java.util.Scanner;

public class InputValidator {

    private final Integer turn;

    public InputValidator(Integer turn) {
        this.turn = turn;
    }

    public Integer[] validateInput(String input) throws InvalidFormatException, QuitGameException {
        if (isQuitCommand(input)) throw new QuitGameException(turn);
        String[] inputs = input.split(" ");
        validateInputLength(inputs);
        return parseInputToIntegers(inputs);
    }

    private boolean isQuitCommand(String input) {
        return input.equalsIgnoreCase("quit");
    }

    private void validateInputLength(String[] inputs) throws InvalidFormatException {
        if (inputs.length != 2) {
            throw new InvalidFormatException("You must enter exactly two integers.");
        }
    }

    private Integer[] parseInputToIntegers(String[] inputs) throws InvalidFormatException {
        Integer x, y;
        try {
            x = Integer.parseInt(inputs[0]);
            y = Integer.parseInt(inputs[1]);
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Both inputs must be integers.");
        }
        return new Integer[]{x, y};
    }
}
