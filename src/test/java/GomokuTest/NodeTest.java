package GomokuTest;

import Gomoku.Color;
import Gomoku.Node;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
    @Test
    public void testNode() {
        Node node = new Node(1, 2);
        assertEquals(Color.EMPTY, node.getColor());
        assertEquals(1, node.getX_coordinate());
        assertEquals(2, node.getY_coordinate());
    }
}