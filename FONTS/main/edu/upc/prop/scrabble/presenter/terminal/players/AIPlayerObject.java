package edu.upc.prop.scrabble.presenter.terminal.players;

public class AIPlayerObject extends PlayerObject {
    @Override
    public void onProcess(float delta) {
        if (!isActive())
            return;

        throw new RuntimeException("AI Player not implemented yet");
    }
}
