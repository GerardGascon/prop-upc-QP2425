package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class HumanPlayerObject extends PlayerObject {
    @Override
    public void onProcess(float delta) {
        if (!isActive())
            return;

        String movementRaw = Reader.getInstance().readLine();
        if (movementRaw == null)
            return;

        int previousScore = player.getScore();
        Movement move = movementMaker.makeMove(movementRaw);
        placePiece(move);
        System.out.println("Movement: " + movementRaw + " " + (player.getScore() - previousScore));
    }
}
