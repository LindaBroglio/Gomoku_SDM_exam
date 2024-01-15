package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI {
    private Game game;
    private JLabel messageLabel;
    private JPanel boardPanel;
    Integer size;
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    public GUI() {
        size = chooseBoardSize();
        if (size == null) System.exit(0);
        Integer[] gameSpecification = new Integer[]{size, 5};
        startNewGame(gameSpecification);
        JFrame gridFrame = new JFrame("Gomoku");
        gridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Integer cellSize = 550 / size;
        Integer boardSize = cellSize * size;
        boardPanel = createBoardPanel(cellSize, boardSize);
        messageLabel = createMessageLabel();

        for (int x = 0; x <= size + 1; x++) {
            for (int y = 0; y <= size + 1; y++) {
                //ImageIcon blackIcon = new ImageIcon(getClass().getResource("/blackStone.png"));
                //ImageIcon whiteIcon = new ImageIcon(getClass().getResource("/whiteStone.png"));
                CircleButton button = new CircleButton();
                button.setBounds((x * cellSize) - (cellSize / 2), (y * cellSize) - (cellSize / 2), cellSize, cellSize);
                Integer[] coordinates = new Integer[]{x, y};
                messageLabel.setText("Black turn!");
                button.addActionListener(e -> {
                    try {
                        game.makeMove(coordinates);
                        button.setCircleColor(game.isBlackTurn() ? Color.WHITE : Color.BLACK);
                        //button.setCircleIcon(game.isBlackTurn() ? whiteIcon: blackIcon);
                        if (!game.boardIsNotFull()) handleDraw();
                        messageLabel.setText(game.isBlackTurn() ? "Black turn" : "White turn");
                    } catch (TakenNodeException ex) {
                        messageLabel.setText("This node is already taken, change spot!");
                    } catch (OutOfBoardException ex) {
                        button.setCircleColor(null);
                        //button.setIcon(null);
                        messageLabel.setText("You tried a move out of the board: change spot!");
                    } catch (GameWonException ex) {
                        button.setCircleColor(game.isBlackTurn() ? Color.BLACK : Color.WHITE);
                        //button.setCircleIcon(game.isBlackTurn() ? blackIcon: whiteIcon);
                        messageLabel.setText(game.isBlackTurn() ? "Black wins!" : "White wins!");
                        handleGameWon();
                    }
                });
                boardPanel.add(button);
            }
        }

        JButton resignButton = getResignButton();

        JPanel boardContainerPanel = new JPanel(new BorderLayout());
        boardContainerPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel resignButtonPanel = new JPanel();
        resignButtonPanel.setOpaque(false);
        resignButtonPanel.add(resignButton);

        JButton quitButton = new JButton("Quit");
        quitButton.setBounds(10, boardSize + cellSize + 10, 80, 30);

        quitButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(
                    boardPanel,
                    "Do you want to quit the game?",
                    "Quit Game",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null);
            if (option == JOptionPane.YES_OPTION) handleResignation();
        });

        boardContainerPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel quitButtonPanel = new JPanel();
        quitButtonPanel.add(quitButton);

        JPanel containerPanel = new JPanel(new BorderLayout());
        containerPanel.setBorder(new EmptyBorder(50, 50, 50, 50));
        containerPanel.add(messageLabel, BorderLayout.NORTH);
        containerPanel.add(boardContainerPanel, BorderLayout.CENTER);
        containerPanel.add(quitButtonPanel, BorderLayout.SOUTH);

        gridFrame.setContentPane(containerPanel);
        gridFrame.setContentPane(containerPanel);
        gridFrame.pack();
        gridFrame.setLocationRelativeTo(null);
        gridFrame.setVisible(true);
    }

    private JButton getResignButton() {
        JButton resignButton = new JButton();
        resignButton.setPreferredSize(new Dimension(90, 30));
        resignButton.setText("Resign");
        resignButton.setForeground(Color.PINK);
        resignButton.setFont(new Font("Courier", Font.PLAIN, 12));

        resignButton.setBorder(BorderFactory.createLineBorder(Color.PINK, 2, true));
        resignButton.setOpaque(false);

        resignButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(
                    boardPanel,
                    "Do you want to quit the game?",
                    "Quit Game",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    null,
                    null);
            if (option == JOptionPane.YES_OPTION) handleResignation();
        });
        return resignButton;
    }

    private void handleGameWon() {
        int option = JOptionPane.showOptionDialog(boardPanel,"Gomoku! " + (game.isBlackTurn() ? "Black" : "White") + " wins!\nDo you want to start a new game?",
                "GOMOKU!", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,null,
                null,null);
        if (option == JOptionPane.YES_OPTION) restartGame();
        else System.exit(0);
    }

    private void restartGame() {
        JFrame oldFrame = (JFrame) SwingUtilities.getWindowAncestor(boardPanel);
        oldFrame.dispose();
        SwingUtilities.invokeLater(GUI::new);
    }

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

    private JLabel createMessageLabel() {
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.PINK);
        Font labelFont = new Font("Courier", Font.PLAIN, 17);
        messageLabel.setFont(labelFont);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        messageLabel.setOpaque(false);
        messageLabel.setBorder(new EmptyBorder(0, 0, 20, 0));
        return messageLabel;
    }

    private JPanel createBoardPanel(Integer cellSize, Integer boardSize) {
        boardPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                try {
                    Image backgroundImage = ImageIO.read(Objects.requireNonNull(getClass().getResource("/boardpanel.jpg")));
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                } catch (IOException e) { LOGGER.log(Level.SEVERE, "Error loading background image", e); }
                Graphics2D g2d = (Graphics2D) g;
                g2d.setColor(Color.BLACK);
                Stroke originalStroke = g2d.getStroke();
                g2d.setStroke(new BasicStroke(2.0f));
                for (int i = 0; i <= size + 1; i++) {
                    g2d.draw(new Line2D.Float((float) cellSize * i, 0, (float) cellSize * i, (float) boardSize + cellSize));
                    g2d.draw(new Line2D.Float(0, (float) cellSize * i, (float) boardSize + cellSize, (float) cellSize * i));
                }
                g2d.setStroke(originalStroke);
            }
        };
        boardPanel.setPreferredSize(new Dimension(boardSize + cellSize + 3, boardSize + cellSize + 3));
        boardPanel.setLayout(null);
        return boardPanel;
    }

    private void startNewGame(Integer[] gameSpecification) { game = new Game(gameSpecification); }

    private Integer chooseBoardSize() {
        JFrame tempFrame = new JFrame();
        tempFrame.setAlwaysOnTop(true);
        tempFrame.setUndecorated(true);
        tempFrame.setLocationRelativeTo(null);
        tempFrame.setVisible(true);
        Integer[] options = {15, 19};
        int selectedOption =
                JOptionPane.showOptionDialog(tempFrame,
                        "Choose the board size:","Board Size",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        null, options, options[0]);
        tempFrame.dispose();
        if (selectedOption == JOptionPane.CLOSED_OPTION) return null;
        return options[selectedOption];
    }

    private void handleResignation() {
        int option = JOptionPane.showOptionDialog(
                boardPanel,
                (game.isBlackTurn() ? "Black" : "White") + " has resigned." +
                        System.lineSeparator() + "Do you want to start a new game?",
                "Resignation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                null);
        if (option == JOptionPane.YES_OPTION) restartGame();
        else System.exit(0);
    }

    private void handleDraw() {
        int option = JOptionPane.showOptionDialog(
                boardPanel,
                "Board is now full: the game ends in a draw! \nDo you want to start a new game?",
                "Draw",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                null,
                null);
        if (option == JOptionPane.YES_OPTION) restartGame();
        else System.exit(0);
    }
}