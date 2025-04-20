package edu.upc.prop.scrabble.presenter.terminal.players;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.domain.ai.AI;

public class AIPlayerObject extends PlayerObject {
    private AI ai;

    public final void configureAI(AI ai) {
        this.ai = ai;
    }

    @Override
    public void onProcess(float delta) {
        if (!isActive())
            return;

        Movement move = ai.run();
        if(move != null) {
            int previousScore = player.getScore();
            placePiece(move);
            System.out.println("Movement: " + move.word() + " " + move.y() + "," + move.x() + " " + (player.getScore() - previousScore));
        }
        else skipTurn();

        // Can't draw pieces yet :c
    }
}
