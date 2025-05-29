package scrabble.stubs;

import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

public class GameSaverMock extends GameSaver {
    public GameSaverMock() {
        super(null, null, null, null);
    }

    @Override
    public void run() {
        // Does nothing
    }
}
