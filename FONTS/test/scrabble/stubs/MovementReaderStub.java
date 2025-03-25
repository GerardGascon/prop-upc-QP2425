package scrabble.stubs;

import edu.upc.prop.scrabble.domain.IMovementReader;

public class MovementReaderStub implements IMovementReader {
    private final String movement;

    public MovementReaderStub(String movement) {
        this.movement = movement;
    }

    @Override
    public String readMove() {
        return movement;
    }
}
