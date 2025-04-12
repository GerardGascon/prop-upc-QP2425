package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;

public class MenuScene extends Scene {
    public MenuScene() {
        System.out.println("onAttach");
    }

    @Override
    public void onProcess(float delta) {
        System.out.println("onProcess");
        SceneManager.getInstance().load(GameScene.class);
    }

    @Override
    public void onDetach() {
        System.out.println("onDetach");
    }
}
