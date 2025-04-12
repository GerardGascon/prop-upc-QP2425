package edu.upc.prop.scrabble.presenter.scenes;

import java.util.ArrayList;
import java.util.List;

public class SceneStack {
    private final List<Scene> scenes = new ArrayList<>();

    public void replace(Class<? extends Scene> sceneClass) {
        clear();
        push(sceneClass);
    }

    public void push(Class<? extends Scene> sceneClass) {
        try {
            Scene scene = sceneClass.getDeclaredConstructor().newInstance();

            scenes.add(scene);
            scene.onAttach();
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate scene: " + sceneClass.getName(), e);
        }
    }

    private void pop(Scene scene) {
        scenes.remove(scene);
        scene.onDetach();
    }

    public void clear() {
        List<Scene> sceneStack = List.copyOf(scenes);
        sceneStack.forEach(this::pop);
    }

    public void process(float delta) {
        List<Scene> scenesToRemove = new ArrayList<>();

        for (Scene scene : scenes) {
            scene.onProcess(delta);
            if (scene.isFreeRequested())
                scenesToRemove.add(scene);
        }

        scenesToRemove.forEach(this::pop);
    }

    public boolean isEmpty() {
        return scenes.isEmpty();
    }
}
