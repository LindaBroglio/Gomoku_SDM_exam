package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Line2D;

public class GUI {

    private final Game game;

    public GUI() {
        Integer[] options = {15, 19};
        JFrame tempFrame = new JFrame();
        tempFrame.setAlwaysOnTop(true);
        tempFrame.setUndecorated(true);
        tempFrame.setLocationRelativeTo(null);
        tempFrame.setVisible(true);

        int selectedOption = JOptionPane.showOptionDialog(tempFrame, "Choose the board size:",
                "Board Size", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                null, options, options[0]);
        tempFrame.dispose();

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
                for (int i = 0; i <= size + 1; i++) {
                    g2d.draw(new Line2D.Float(cellSize * i, 0, cellSize * i, boardSize + cellSize));
                    g2d.draw(new Line2D.Float(0, cellSize * i, boardSize + cellSize, cellSize * i));
                }
            }
        };
        boardPanel.setPreferredSize(new Dimension(boardSize + cellSize + 1, boardSize + cellSize + 1));
        boardPanel.setLayout(null);

        class CircleButton extends JButton {
            private Color circleColor;

            public CircleButton() {
                setOpaque(false);
                setContentAreaFilled(false);
                setBorderPainted(false);
            }

            public void setCircleColor(Color color) {
                this.circleColor = color;
                repaint();
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (circleColor != null) {
                    g.setColor(circleColor);
                    g.fillOval(0, 0, getWidth() - 1, getHeight() - 1);
                }
            }
        }


        for (int x = 0; x <= size + 1; x++) {
            for (int y = 0; y <= size + 1; y++) {
                CircleButton button = new CircleButton();
                button.setBounds((x * cellSize) - (cellSize / 2), (y * cellSize) - (cellSize / 2), cellSize, cellSize);
                Integer[] coordinates = new Integer[]{x, y};
                button.addActionListener(e -> {
                    try {
                        button.setCircleColor(game.isBlackTurn() ? Color.BLACK : Color.WHITE);
                        button.setContentAreaFilled(false);
                        game.makeMove(coordinates);
                    } catch (TakenNodeException ex) {
                        button.setCircleColor(game.isBlackTurn() ? Color.WHITE : Color.BLACK);
                        JOptionPane.showMessageDialog(gridFrame, "This node is already taken. Please choose another one.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                    } catch (OutOfBoardException ex) {
                        button.setCircleColor(null);
                        JOptionPane.showMessageDialog(gridFrame, "This move is out of the board boundaries. Please choose a valid position.", "Invalid Move", JOptionPane.ERROR_MESSAGE);
                    } catch (GameWonException ex) {
                        JOptionPane.showMessageDialog(gridFrame, game.isBlackTurn() ? "Black wins!" : "White wins!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
                        System.exit(0);
                    }
                });
                boardPanel.add(button);
            }
        }

        JPanel containerPanel = new JPanel(new GridBagLayout());
        containerPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        containerPanel.add(boardPanel, gbc);
        containerPanel.setSize(new Dimension(boardSize, boardSize));
        gridFrame.setContentPane(containerPanel);
        gridFrame.pack();
        gridFrame.setLocationRelativeTo(null);
        gridFrame.setVisible(true);
    }
}
