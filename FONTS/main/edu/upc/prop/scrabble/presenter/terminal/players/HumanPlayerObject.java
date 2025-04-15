package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class HumanPlayerObject extends PlayerObject {
    private MovementMaker movementMaker;

    public void configure(MovementMaker movementMaker) {
        this.movementMaker = movementMaker;
    }

    @Override
    public void onProcess(float delta) {
        if (!onTurn)
            return;

        String movementRaw = Reader.getInstance().readLine();
        if (movementRaw == null)
            return;

        System.out.println(movementRaw);
        movementMaker.makeMove(movementRaw);
    }
}
