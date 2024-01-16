package Gomoku.utilities;

import Gomoku.Exceptions.InputExceptions.InvalidFormatException;
import Gomoku.Exceptions.InputExceptions.QuitException;
import Gomoku.Exceptions.InputExceptions.ResignException;

public class InputValidator {
    private final Boolean turn;
    private final Integer moveCount;

    public InputValidator(Boolean turn, Integer moveCount) {
        this.turn = turn;
        this.moveCount = moveCount;
    }

    public Integer[] validateInput(String userInput) throws InvalidFormatException, ResignException, QuitException {
        if (moveCount == 0 && userInput.equalsIgnoreCase("quit")){ throw new QuitException();}
        if (moveCount != 0 && userInput.equalsIgnoreCase("quit")){ throw new ResignException(turn);}
        String[] userSplitInput = userInput.split(" ");
        if (userSplitInput.length != 2) throw new InvalidFormatException("Input must be exactly two integers.");
        return fromStringToIntegers(userSplitInput);
    }

    private Integer[] fromStringToIntegers(String[] parts) throws InvalidFormatException {
        try {
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            return new Integer[]{x, y};
        } catch (NumberFormatException e) {
            throw new InvalidFormatException("Input must be exactly two integers.");
        }
    }
}
