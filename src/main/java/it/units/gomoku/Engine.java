package it.units.gomoku;

import it.units.gomoku.exceptions.GameWonException;
import it.units.gomoku.exceptions.inputexceptions.OutOfBoardException;
import it.units.gomoku.exceptions.inputexceptions.TakenNodeException;

import java.util.Random;

public class Engine {
    private final Game game;
    private final Random random = new Random();
    private Integer[] lastMove;

    public Engine(Game game) { this.game = game; }

    public void makeMove() throws GameWonException {
        int x, y;
        while (true) {
            x = random.nextInt(game.getBoardSize()) + 1;
            y = random.nextInt(game.getBoardSize()) + 1;
            try {
                game.makeMove(new Integer[]{x, y});
                lastMove = new Integer[]{x, y};
                break;
            } catch (TakenNodeException | OutOfBoardException ignored) {}
        }
    }

    public Integer[] getLastMove() { return lastMove; }
}
