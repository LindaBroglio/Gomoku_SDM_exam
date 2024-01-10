package GomokuTest;

import Gomoku.utilities.Color;
import Gomoku.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class NodeTest {
   Node node;

   @BeforeEach
   void init(){
       node = new Node();
   }

    @Test
    void getEmptyNodeColor() {
        assertEquals(Color.EMPTY, node.getColor());
    }

    @Test
    void setColorOnce() {
        node.setColor(Color.BLACK);
        assertEquals(Color.BLACK, node.getColor());
    }

    @Test
    void setColorTwice() {
        node.setColor(Color.BLACK);
        node.setColor(Color.WHITE);
        assertEquals(Color.WHITE, node.getColor());
    }
}