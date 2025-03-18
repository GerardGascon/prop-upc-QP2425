package scrabble;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.domain.IMovementReader;
import edu.upc.prop.scrabble.domain.MovementReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMovementReader {
    @Test
    public void testReadMoveReturnsCorrectWord() {
        IMovementReader reader = new MovementReaderMock("TEST C0");
        MovementReader sut = new MovementReader(reader);

        Movement res = sut.run();

        assertEquals("TEST", res.word());
    }

    @Test
    public void testReadMoveReturnsCorrectPosition() {
        IMovementReader reader = new MovementReaderMock("TEST C0");
        MovementReader sut = new MovementReader(reader);

        Movement res = sut.run();

        assertEquals("TEST", res.word());
    }

    @Test
    public void testReadMoveReturnsCorrectPositionInverted() {
        IMovementReader reader = new MovementReaderMock("TEST 0C");
        MovementReader sut = new MovementReader(reader);

        Movement res = sut.run();

        assertEquals(2, res.x());
        assertEquals(0, res.y());
    }
}
