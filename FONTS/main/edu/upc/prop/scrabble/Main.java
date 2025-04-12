package edu.upc.prop.scrabble;

import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.terminal.scenes.GameScene;
import edu.upc.prop.scrabble.presenter.terminal.scenes.MenuScene;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Scrabble");

        SceneManager.getInstance().load(MenuScene.class);

        long lastTime = System.nanoTime();
        double delta;

        while(SceneManager.getInstance().isRunning()){
            long now = System.nanoTime();
            delta = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            SceneManager.getInstance().process((float)delta);
//            System.out.println(delta);
        }

        System.out.println("Bye.");
    }
}