package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.utils.Direction;

public class MovementReader {
    private final IMovementReader reader;

    public MovementReader(IMovementReader reader) {
        this.reader = reader;
    }

    public Movement run() {
        String movementRaw = reader.readMove();

        return new Movement("a", 3, 2, Direction.Horizontal);
    }
}
