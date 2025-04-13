package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class PlayerObject extends SceneObject {
    private boolean onTurn = false;

    private MovementMaker movementMaker;

    public void configure(MovementMaker movementMaker) {
        this.movementMaker = movementMaker;
    }

    public void startTurn() {
        onTurn = true;
    }

    public void endTurn() {
        onTurn = false;
    }

    @Override
    public void onProcess(float delta) {
        if (!onTurn)
            return;

        String movementRaw = Reader.getInstance().readLine();
        if (movementRaw == null)
            return;

        System.out.println(movementRaw);
        int score = movementMaker.makeMove(movementRaw);
        System.out.println("Score: " + score);
    }
}
