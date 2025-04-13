package scrabble;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementParser;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMovementParser {
    @Test
    public void testMakeMoveReturnsCorrectWord() {
        Movement res = MovementParser.parse("TEST C1");

        assertEquals("TEST", res.word());
    }

    @Test
    public void testMakeMoveReturnsCorrectPosition() {
        Movement res = MovementParser.parse("TEST C1");

        assertEquals(2, res.x());
        assertEquals(0, res.y());
    }

    @Test
    public void testMakeMoveReturnsCorrectPositionInverted() {
        Movement res = MovementParser.parse("TEST 1C");

        assertEquals(2, res.x());
        assertEquals(0, res.y());
    }

    @Test
    public void testMakeMoveReturnsCorrectDirectionHorizontal() {
        Movement res = MovementParser.parse("TEST C1");

        assertEquals(Direction.Horizontal, res.direction());
    }

    @Test
    public void testMakeMoveReturnsCorrectDirectionVertical() {
        Movement res = MovementParser.parse("TEST 1C");

        assertEquals(Direction.Vertical, res.direction());
    }
}
