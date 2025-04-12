package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.presenter.scenes.Scene;

public class MenuScene extends Scene {
    public MenuScene() {
        System.out.println("onAttach");
    }

    @Override
    public void onProcess(float delta) {
        System.out.println("onProcess");
        free(); // Used only to avoid an infinite loop
    }

    @Override
    public void onDetach() {
        System.out.println("onDetach");
    }
}
