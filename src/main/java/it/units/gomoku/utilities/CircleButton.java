package it.units.gomoku.utilities;

import javax.swing.*;
import java.awt.*;

public class CircleButton extends JButton {
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
