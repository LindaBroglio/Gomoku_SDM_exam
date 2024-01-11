package Gomoku.utilities;

import Gomoku.Exceptions.InputExceptions.InvalidFormatException;
import Gomoku.Exceptions.InputExceptions.QuitGameException;

public class InputValidator {

    private final Integer turn;

    public InputValidator(Integer turn) {
        this.turn = turn;
    }

    public Integer[] validateInput(String userInput) throws InvalidFormatException, QuitGameException {
        if (userInput.equalsIgnoreCase("quit")) throw new QuitGameException(turn);
        String[] userSplitInput = userInput.split(" ");
        if (userSplitInput.length != 2) throw new InvalidFormatException("Enter exactly two integers.");
        return fromStringToInteger(userSplitInput);
    }

    private Integer[] fromStringToInteger(String[] parts) throws InvalidFormatException {
        try {
            Integer x = Integer.parseInt(parts[0]);
            Integer y = Integer.parseInt(parts[1]);
            return new Integer[]{x, y};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Inputs must be integers.");
        }
    }
}
