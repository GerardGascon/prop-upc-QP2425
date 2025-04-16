package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class HumanPlayerObject extends PlayerObject {
    @Override
    public void onProcess(float delta) {
        if (!isActive())
            return;

        String movementRaw = Reader.getInstance().readLine();
        if (movementRaw == null)
            return;

        System.out.println(movementRaw);
        movementMaker.makeMove(movementRaw);
    }
}
