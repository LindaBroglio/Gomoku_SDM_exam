package GomokuTest;

import Gomoku.Color;
import Gomoku.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {

    @Test
    void getEmptyNodeColor() {
        Node node = new Node();
        assertEquals(Color.EMPTY, node.getColor());
    }

    @Test
    void setColorOnce() {
        Node node = new Node();
        node.setColor(Color.BLACK);
        assertEquals(Color.BLACK, node.getColor());
    }

    @Test
    void setColorTwice() {
        Node node = new Node();
        node.setColor(Color.BLACK);
        node.setColor(Color.WHITE);
        assertEquals(Color.WHITE, node.getColor());
    }
}