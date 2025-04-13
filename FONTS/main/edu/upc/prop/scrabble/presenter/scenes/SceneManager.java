package edu.upc.prop.scrabble.presenter.scenes;

import edu.upc.prop.scrabble.utils.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class SceneManager {
    private boolean sceneLoaded = false;
    private Scene scene;
    private Pair<Class<? extends Scene>, Object[]> sceneToLoad;

    private static SceneManager instance;

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private SceneManager() {
    }

    public void load(Class<? extends Scene> sceneClass, Object... dependencies) {
        if (!sceneLoaded) {
            sceneLoaded = true;
            loadScene(sceneClass, dependencies);
        } else {
            sceneToLoad = new Pair<>(sceneClass, dependencies);
        }
    }

    private void loadScene(Class<? extends Scene> sceneClass, Object... dependencies) {
        try {
            if (scene != null)
                scene.onDetach();

            scene = instantiateScene(sceneClass, dependencies);
            System.out.println("Loaded scene: " + scene.getClass().getSimpleName());
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

    public void quit() {
        scene.onDetach();
        scene = null;
    }

    public void process(float delta) {
        if (sceneToLoad != null) {
            loadScene(sceneToLoad.first(), sceneToLoad.second());
            sceneToLoad = null;
            return;
        }
        scene.onProcess(delta);
    }

    public boolean isRunning() {
        return scene != null;
    }
}
