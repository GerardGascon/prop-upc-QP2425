package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.presenter.scenes.GameLoop;
import edu.upc.prop.scrabble.presenter.terminal.scenes.MenuScene;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Scrabble");

        GameLoop gameLoop = new GameLoop(MenuScene.class);
        gameLoop.run();

        System.out.println("Bye.");
    }
}