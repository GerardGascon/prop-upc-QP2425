package scrabble;

import edu.upc.prop.scrabble.data.Board;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestBoard {
    @Test
    public void createBoardSetsProperSize() {
        Board sut = new Board(4);

        assertEquals(4, sut.getSize());
    }
}