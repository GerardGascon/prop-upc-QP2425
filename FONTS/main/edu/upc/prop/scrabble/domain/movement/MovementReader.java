package edu.upc.prop.scrabble.domain.movement;

import edu.upc.prop.scrabble.data.Movement;

public class MovementReader {
    private final IMovementMaker movementMaker;

    public MovementReader(IMovementMaker reader) {
        this.movementMaker = reader;
    }

    public Movement run() {
        return movementMaker.makeMove();
    }
}
