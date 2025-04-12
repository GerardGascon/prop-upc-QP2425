package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.presenter.scenes.Scene;

public class MenuScene extends Scene {
    public MenuScene() {
        System.out.println("onAttach");
    }

    @Override
    public void onProcess(float delta) {
        System.out.println("onProcess");
        load(GameScene.class);
    }

    @Override
    public void onDetach() {
        System.out.println("onDetach");
    }
}
