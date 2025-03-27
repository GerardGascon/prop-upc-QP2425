package scrabble;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.presenter.terminal.MovementMaker;
import edu.upc.prop.scrabble.presenter.terminal.utils.IReader;
import edu.upc.prop.scrabble.utils.Direction;
import org.junit.Test;
import scrabble.stubs.ReaderStub;

import static org.junit.Assert.*;

public class TestMovementMaker {
    @Test
    public void testMakeMoveReturnsCorrectWord() {
        IReader reader = new ReaderStub("TEST C0");
        MovementMaker sut = new MovementMaker(reader);

        Movement res = sut.makeMove();

        assertEquals("TEST", res.word());
    }

    @Test
    public void testMakeMoveReturnsCorrectPosition() {
        IReader reader = new ReaderStub("TEST C0");
        MovementMaker sut = new MovementMaker(reader);

        Movement res = sut.makeMove();

        assertEquals(2, res.x());
        assertEquals(0, res.y());
    }

    @Test
    public void testMakeMoveReturnsCorrectPositionInverted() {
        IReader reader = new ReaderStub("TEST 0C");
        MovementMaker sut = new MovementMaker(reader);

        Movement res = sut.makeMove();

        assertEquals(2, res.x());
        assertEquals(0, res.y());
    }

    @Test
    public void testMakeMoveReturnsCorrectDirectionHorizontal() {
        IReader reader = new ReaderStub("TEST C0");
        MovementMaker sut = new MovementMaker(reader);

        Movement res = sut.makeMove();

        assertEquals(Direction.Horizontal, res.direction());
    }

    @Test
    public void testMakeMoveReturnsCorrectDirectionVertical() {
        IReader reader = new ReaderStub("TEST 0C");
        MovementMaker sut = new MovementMaker(reader);

        Movement res = sut.makeMove();

        assertEquals(Direction.Vertical, res.direction());
    }
}
