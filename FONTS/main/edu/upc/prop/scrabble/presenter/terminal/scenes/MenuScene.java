package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.terminal.GameCreator;

public class MenuScene extends Scene {
    public MenuScene() {
        instantiate(GameCreator.class);
    }
}
