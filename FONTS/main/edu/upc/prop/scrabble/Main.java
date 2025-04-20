package edu.upc.prop.scrabble;

import edu.upc.prop.scrabble.presenter.GameLoop;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.terminal.scenes.GameScene;
import edu.upc.prop.scrabble.presenter.terminal.scenes.MenuScene;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Scrabble");

        GameLoop gameLoop = new GameLoop(MenuScene.class);
        gameLoop.run();

        System.out.println("Bye.");
    }
}