package edu.upc.prop.scrabble.presenter.scenes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SceneManager {
    private Scene scene;

    public void load(Class<? extends Scene> sceneClass, Object... dependencies) {
        try {
            if (scene != null)
                scene.onDetach();

            scene = instantiateScene(sceneClass, dependencies);
            scene.setSceneStack(this);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate scene: " + sceneClass.getName(), e);
        }
    }

    private Scene instantiateScene(Class<? extends Scene> sceneClass, Object... dependencies) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> target = getSceneConstructor(sceneClass);
        return (Scene) target.newInstance(dependencies);
    }

    private static Constructor<?> getSceneConstructor(Class<? extends Scene> sceneClass) {
        Constructor<?>[] constructors = sceneClass.getDeclaredConstructors();
        return Arrays.stream(constructors)
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new RuntimeException("No constructors found"));
    }

    void quit() {
        scene.onDetach();
        scene = null;
    }

    public void process(float delta) {
        scene.onProcess(delta);
    }

    public boolean isRunning() {
        return scene != null;
    }
}
