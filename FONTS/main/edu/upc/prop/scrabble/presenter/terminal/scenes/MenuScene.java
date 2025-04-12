package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;

public class MenuScene extends Scene {
    public MenuScene() {
        SceneManager.getInstance().load(GameScene.class);
    }
}
