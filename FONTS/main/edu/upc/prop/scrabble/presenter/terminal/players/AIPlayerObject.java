package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;

public class AIPlayerObject extends PlayerObject {
    private MovementMaker movementMaker;

    public void configure(MovementMaker movementMaker) {
        this.movementMaker = movementMaker;
    }

    @Override
    public void onProcess(float delta) {
        if (!isActive())
            return;

        throw new RuntimeException("AI Player not implemented yet");
    }
}
