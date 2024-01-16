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
import java.net.URL;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GUI {
    private Game game;
    private JLabel messageLabel;
    private JPanel boardPanel;
    Integer size;
    private final CircleButton[][] buttons;
    private static final Logger LOGGER = Logger.getLogger(GUI.class.getName());

    public GUI() {
        size = chooseBoardSize();
        if (size == null) System.exit(0);
        startNewGame(new Integer[]{size, 5});
        JFrame gridFrame = createGridFrame();
        createButtons();
        JButton resignButton = createResignButton();
        JPanel mainPanel = createMainPanel(resignButton);
        setBackgroundPanel(gridFrame, mainPanel);
        gridFrame.pack();
        gridFrame.setLocationRelativeTo(null);
        gridFrame.setVisible(true);
    }

    private void startNewGame(Integer[] gameSpecification) { game = new Game(gameSpecification); }

    private JFrame createGridFrame() {
        JFrame gridFrame = new JFrame("Gomoku");
        gridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return gridFrame;
    }

    private void createButtons() {
        Integer cellSize = 550 / size;
        Integer boardSize = cellSize * size;
        boardPanel = createBoardPanel(cellSize, boardSize);
        messageLabel = createMessageLabel();
        for (int x = 0; x <= size + 1; x++) {
            for (int y = 0; y <= size + 1; y++) {
                buttons[x][y] = createButton(new Integer[]{x, y}, cellSize);
                boardPanel.add(buttons[x][y]);
            }
        }
    }

    private CircleButton createButton(Integer[] coordinates, int cellSize) {
        ImageIcon blackIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/blackStone.png")));
        ImageIcon whiteIcon = new ImageIcon(Objects.requireNonNull(getClass().getResource("/whiteStone.png")));
        CircleButton button = new CircleButton();
        button.setBounds((coordinates[0] * cellSize) - (cellSize / 2), (coordinates[1] * cellSize) - (cellSize / 2), cellSize, cellSize);
        messageLabel.setText("Black turn!");
        button.addActionListener(e -> handleButtonAction(button, coordinates, blackIcon, whiteIcon));
        return button;
    }

    private void handleButtonAction(CircleButton button, Integer[] coordinates, ImageIcon blackIcon, ImageIcon whiteIcon) {
        try {
            game.makeMove(coordinates);
            button.setCircleIcon(game.isBlackTurn() ? whiteIcon : blackIcon);
            if (!game.boardIsNotFull()) handleDraw();
            messageLabel.setText(game.isBlackTurn() ? "Black turn" : "White turn");
        } catch (TakenNodeException ex) {
            handleTakenNodeException();
        } catch (OutOfBoardException ex) {
            handleOutOfBoardException(button);
        } catch (GameWonException ex) {
            handleGameWonException(button, blackIcon, whiteIcon);
        }
    }

    private void handleTakenNodeException() { messageLabel.setText("This node is already taken, change spot!"); }

    private void handleOutOfBoardException(CircleButton button) {
        button.setIcon(null);
        messageLabel.setText("You tried a move out of the board: change spot!");
    }

    private void handleGameWonException(CircleButton button, ImageIcon blackIcon, ImageIcon whiteIcon) {
        button.setCircleIcon(game.isBlackTurn() ? blackIcon : whiteIcon);
        messageLabel.setText(game.isBlackTurn() ? "Black wins!" : "White wins!");
        handleGameWon();
    }

    private void handleGameWon() {
        int option = JOptionPane.showOptionDialog(boardPanel,
                "Gomoku! " + (game.isBlackTurn() ? "Black" : "White") +
                        " wins!" + System.lineSeparator() +
                        "Do you want to start a new game?",
                "GOMOKU!", JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null, null,null);
        if (option == JOptionPane.YES_OPTION) restartGame();
        else System.exit(0);
    }

    private JPanel createMainPanel(JButton resignButton) {
        JPanel boardContainerPanel = new JPanel(new BorderLayout());
        boardContainerPanel.add(boardPanel, BorderLayout.CENTER);

        JPanel resignButtonPanel = new JPanel();
        resignButtonPanel.setOpaque(false);
        resignButtonPanel.add(resignButton);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        messagePanel.setOpaque(false);
        messagePanel.add(messageLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(50, 60, 40, 60));
        mainPanel.setOpaque(false);

        mainPanel.add(messagePanel);
        mainPanel.add(boardContainerPanel);
        mainPanel.add(Box.createVerticalStrut(25));
        mainPanel.add(resignButtonPanel, BorderLayout.SOUTH);

        return mainPanel;
    }

    private void setBackgroundPanel(JFrame gridFrame, JPanel mainPanel) {
        BackgroundPanel backgroundPanel = new BackgroundPanel("/bg.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        gridFrame.setContentPane(backgroundPanel);
    }

    private JButton createResignButton() {
        JButton resignButton = new JButton();
        resignButton.setPreferredSize(new Dimension(90, 30));
        resignButton.setText("Resign");
        resignButton.setForeground(Color.PINK);
        resignButton.setFont(new Font("Courier", Font.PLAIN, 12));

        resignButton.setBorder(BorderFactory.createLineBorder(Color.PINK, 2, true));
        resignButton.setOpaque(false);

        resignButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(boardPanel,
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

    private void restartGame() {
        JFrame oldFrame = (JFrame) SwingUtilities.getWindowAncestor(boardPanel);
        oldFrame.dispose();
        SwingUtilities.invokeLater(GUI::new);
    }

    private JLabel createMessageLabel() {
        messageLabel = new JLabel();
        messageLabel.setForeground(Color.PINK);
        Font labelFont = new Font("Courier", Font.PLAIN, 17);
        messageLabel.setFont(labelFont);
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
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

    static class CircleButton extends JButton {
        private ImageIcon circleIcon;

        public CircleButton() {
            setOpaque(false);
            setContentAreaFilled(false);
            setBorderPainted(false);
        }

        public void setCircleIcon(ImageIcon icon) {
            this.circleIcon = icon;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (circleIcon != null) {
                g.drawImage(circleIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        }
    }

    static class BackgroundPanel extends JPanel {
        private Image backgroundImage;

        public BackgroundPanel(String imagePath) {
            URL imageUrl = getClass().getResource(imagePath);
            if (imageUrl != null) backgroundImage = new ImageIcon(imageUrl).getImage();
            else System.err.println("Resource not found: " + imagePath);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}