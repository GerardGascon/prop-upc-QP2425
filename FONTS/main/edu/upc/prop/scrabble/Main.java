package edu.upc.prop.scrabble;

import edu.upc.prop.scrabble.presenter.scenes.SceneStack;
import edu.upc.prop.scrabble.presenter.terminal.scenes.GameScene;

public class Main {
    public static void main(String[] args) {
        SceneStack sceneStack = new SceneStack();
        sceneStack.push(GameScene.class);

        long lastTime = System.nanoTime();
        double delta;

        while(!sceneStack.isEmpty()){
            long now = System.nanoTime();
            delta = (now - lastTime) / 1_000_000_000.0;
            lastTime = now;

            sceneStack.process((float)delta);
            sceneStack.clear();
        }
    }
}