package scrabble;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.domain.IMovementReader;
import edu.upc.prop.scrabble.domain.MovementReader;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestMovementReader {
    @Test
    public void testReadMoveReturnsCorrectMovement() {
        IMovementReader reader = new MovementReaderMock("TEST C0");
        MovementReader sut = new MovementReader(reader);

        Movement res = sut.run();

        assertEquals("TEST", res.word());
    }
}
