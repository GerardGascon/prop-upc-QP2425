package scrabble;

import edu.upc.prop.scrabble.domain.IMovementReader;

public class MovementReaderMock implements IMovementReader {
    private final String movement;

    public MovementReaderMock(String movement) {
        this.movement = movement;
    }

    @Override
    public String readMove() {
        return movement;
    }
}
