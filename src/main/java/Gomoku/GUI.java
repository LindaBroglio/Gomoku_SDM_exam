package Gomoku;

import Gomoku.Exceptions.GameWonException;
import Gomoku.Exceptions.InputExceptions.OutOfBoardException;
import Gomoku.Exceptions.InputExceptions.TakenNodeException;
import Gomoku.utilities.BackgroundPanel;
import Gomoku.utilities.CircleButton;

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
    private static final String TEMPLE_IMAGE_PATH = "/temple.png";
    private static final String BLACK = "Black";
    private static final String WHITE = "White";

    public GUI() {
        size = chooseBoardSize();
        if (size == null) System.exit(0);
        startNewGame(new Integer[]{size, 5});
        JFrame gridFrame = createGridFrame();
        createNodeButtons();
        JButton resignButton = createResignButton();
        JPanel mainPanel = createMainPanel(resignButton);
        setBackgroundPanel(gridFrame, mainPanel);
        adjustAppearance(gridFrame);
    }

    private Integer chooseBoardSize() {
        ImageIcon icon = createImageIcon(TEMPLE_IMAGE_PATH);
        JFrame tempFrame = new JFrame();
        tempFrame.setAlwaysOnTop(true);
        tempFrame.setUndecorated(true);
        tempFrame.setLocationRelativeTo(null);
        tempFrame.setVisible(true);
        Integer[] options = {15, 19};
        int selectedOption = JOptionPane.showOptionDialog(tempFrame,
                        "Choose the board size:", "Let's play Gomoku!",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE,
                        icon, options, options[0]);
        tempFrame.dispose();
        if (selectedOption == JOptionPane.CLOSED_OPTION) return null;
        return options[selectedOption];
    }

    private void startNewGame(Integer[] gameSpecification) {
        game = new Game(gameSpecification);
    }

    private JFrame createGridFrame() {
        JFrame gridFrame = new JFrame("Gomoku");
        gridFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        return gridFrame;
    }

    private void createNodeButtons() {
        Integer cellSize = 550 / size;
        Integer boardSize = cellSize * size;
        boardPanel = createBoardPanel(cellSize, boardSize);
        messageLabel = createMessageLabel();
        for (int x = 0; x <= size + 1; x++) {
            for (int y = 0; y <= size + 1; y++) {
                boardPanel.add(createNodeButton(new Integer[]{x, y}, cellSize));
            }
        }
    }

    private CircleButton createNodeButton(Integer[] coordinates, int cellSize) {
        ImageIcon blackIcon = createImageIcon("/blackStone.png");
        ImageIcon whiteIcon = createImageIcon("/whiteStone.png");
        CircleButton button = new CircleButton();
        button.setBounds((coordinates[0] * cellSize) - (cellSize / 2), (coordinates[1] * cellSize) - (cellSize / 2), cellSize, cellSize);
        messageLabel.setText("Black turn: place your stone");
        button.addActionListener(e -> handleNodeButtonAction(button, coordinates, blackIcon, whiteIcon));
        return button;
    }

    private void handleNodeButtonAction(CircleButton button, Integer[] coordinates, ImageIcon blackIcon, ImageIcon whiteIcon) {
        try {
            game.makeMove(coordinates);
            button.setCircleIcon(game.isBlackTurn() ? whiteIcon : blackIcon);
            if (game.boardIsFull()) handleDraw();
            messageLabel.setText((game.isBlackTurn() ? "Black turn:" : "White turn:") + " place your stone");
        } catch (TakenNodeException ex) {
            handleTakenNodeException();
        } catch (OutOfBoardException ex) {
            handleOutOfBoardException(button);
        } catch (GameWonException ex) {
            handleGameWonException(button, blackIcon, whiteIcon);
        }
    }

    private void handleTakenNodeException() {
        messageLabel.setText("This node is already taken, change spot!");
    }

    private void handleOutOfBoardException(CircleButton button) {
        button.setIcon(null);
        messageLabel.setText("You tried a move out of the board: change spot!");
    }

    private void handleGameWonException(CircleButton button, ImageIcon blackIcon, ImageIcon whiteIcon) {
        button.setCircleIcon(game.isBlackTurn() ? blackIcon : whiteIcon);
        messageLabel.setText(game.isBlackTurn() ? "Black wins!" : "White wins!");
        handleGameWon();
    }

    private JButton createResignButton() {
        ImageIcon icon = createImageIcon(TEMPLE_IMAGE_PATH);
        JButton resignButton = new JButton();
        resignButton.setPreferredSize(new Dimension(90, 30));
        resignButton.setText("Resign");
        resignButton.setForeground(Color.PINK);
        resignButton.setFont(new Font("Courier", Font.PLAIN, 12));

        resignButton.setBorder(BorderFactory.createLineBorder(Color.PINK, 2, true));
        resignButton.setOpaque(false);

        resignButton.addActionListener(e -> {
            int option = JOptionPane.showOptionDialog(boardPanel,
                    "Are you sure you want to resign?",
                    "Resign?",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    icon,
                    null,
                    null);
            if (option == JOptionPane.YES_OPTION) handleResignation();
        });
        return resignButton;
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
        BackgroundPanel backgroundPanel;
        backgroundPanel = new BackgroundPanel("/bg.jpg");
        backgroundPanel.setLayout(new BorderLayout());
        backgroundPanel.add(mainPanel, BorderLayout.CENTER);
        gridFrame.setContentPane(backgroundPanel);
    }

    private void adjustAppearance(JFrame gridFrame) {
        gridFrame.pack();
        gridFrame.setLocationRelativeTo(null);
        gridFrame.setResizable(false);
        gridFrame.setVisible(true);
    }

    private void restartGame() {
        JFrame oldFrame = (JFrame) SwingUtilities.getWindowAncestor(boardPanel);
        oldFrame.dispose();
        SwingUtilities.invokeLater(GUI::new);
    }

    private void handleGameWon() {
        ImageIcon icon = createImageIcon(TEMPLE_IMAGE_PATH);
        int option = JOptionPane.showOptionDialog(boardPanel,
                "Gomoku! " + (game.isBlackTurn() ? BLACK : WHITE) + " wins!" + System.lineSeparator() + "Do you want to play again?",
                "GOMOKU!",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                null,
                null);
        if (option == JOptionPane.YES_OPTION) restartGame();
        else System.exit(0);
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
                    g2d.draw(new Line2D.Float(
                            (float) cellSize * i,
                            0,
                            (float) cellSize * i,
                            (float) boardSize + cellSize));
                    g2d.draw(new Line2D.Float(
                            0,
                            (float) cellSize * i,
                            (float) boardSize + cellSize,
                            (float) cellSize * i));
                }
                g2d.setStroke(originalStroke);
            }
        };
        boardPanel.setPreferredSize(new Dimension(boardSize + cellSize + 1, boardSize + cellSize + 1));
        boardPanel.setLayout(null);
        return boardPanel;
    }

    private void handleResignation() {
        ImageIcon icon = createImageIcon(TEMPLE_IMAGE_PATH);
        int option = JOptionPane.showOptionDialog(boardPanel,
                (game.isBlackTurn() ? BLACK : WHITE) + " has resigned: " +
                        (game.isBlackTurn() ? WHITE : BLACK) +
                        " wins!\nDo you want to start a new game?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                icon,
                null,
                null);
        if (option == JOptionPane.YES_OPTION) restartGame();
        else System.exit(0);
    }

    private void handleDraw() {
        ImageIcon icon = createImageIcon(TEMPLE_IMAGE_PATH);
        int option = JOptionPane.showOptionDialog(boardPanel,
                "Board is now full: the game ends in a draw! \nDo you want to start a new game?",
                "Draw",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                icon,
                null,
                null);
        if (option == JOptionPane.YES_OPTION) restartGame();
        else System.exit(0);
    }

    public ImageIcon createImageIcon(String imageName) {
        try {
            return new ImageIcon(Objects.requireNonNull(getClass().getResource(imageName)));
        } catch (NullPointerException e) {
            LOGGER.log(Level.SEVERE, "Image not found", e);
        }
        return null;
    }

    public Integer getSize() { return size; }
}