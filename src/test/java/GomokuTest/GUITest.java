package GomokuTest;

import Gomoku.GUI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GUITest {

    @Test
    @Disabled
    void instantiateGUIAndTestAButton() {
        GUI gui = new GUI();
        //JButton node = gui.getButtons(1,1);
        int cellSize = 600 / 15;
        //node.doClick();
        //assertNotNull(node);
        //assertEquals(cellSize, node.getWidth());
        //assertEquals(cellSize, node.getHeight());
    }
}
