package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

public class GUI {

    private final Game game;

    public GUI() {
        Integer[] options = {15, 19};
        int selectedOption = JOptionPane.showOptionDialog(null, "Choose the board size:",
                "Board Size", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
        Integer size = options[selectedOption];
        Integer[] gameSpecification = {size, 5};
        game = new Game(gameSpecification);

        JFrame gridFrame = new JFrame("Gomoku");
        gridFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int cellSize = 600 / size;
        int boardSize = cellSize * size;

        JPanel boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                setBackground(new Color(255, 204, 153));
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                for (int i = 0; i < size; i++) {
                    g2d.draw(new Line2D.Float(cellSize * i, 0, cellSize * i, boardSize));
                    g2d.draw(new Line2D.Float(0, cellSize * i, boardSize, cellSize * i));
                }
            }
        };
        boardPanel.setPreferredSize(new Dimension(boardSize, boardSize));
        boardPanel.setLayout(null);

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                JButton button = new JButton();
                button.setBounds((x * cellSize) - (cellSize / 2), (y * cellSize) - (cellSize / 2), cellSize, cellSize);
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorderPainted(false);
                Integer finalX = x;
                Integer finalY = y;
                button.addActionListener(e -> {
                    try {
                        button.setBackground(game.isBlackTurn() ? Color.BLACK : Color.WHITE);
                        button.setContentAreaFilled(true);
                        game.makeMoveGUI(finalX, finalY);
                        button.setEnabled(false);
                    } catch (TakenNodeException ex) {
                        JOptionPane.showMessageDialog(gridFrame, "This node is already taken. Please choose another one.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                    } catch (OutOfBoardException ex) {
                        JOptionPane.showMessageDialog(gridFrame, "This move is out of the board boundaries. Please choose a valid position.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                    } catch (GameWonException ex) {
                        JOptionPane.showMessageDialog(gridFrame, game.isBlackTurn() ? "Black wins!" : "White wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                    }
                });
                boardPanel.add(button);
            }
        }

        gridFrame.add(boardPanel);
        gridFrame.pack();
        gridFrame.setSize(new Dimension(boardSize, boardSize));
        gridFrame.setLocationRelativeTo(null);
        gridFrame.setVisible(true);
    }
}
