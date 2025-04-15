package edu.upc.prop.scrabble.presenter.scenes;

import edu.upc.prop.scrabble.utils.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Class used to manage all the scenes present in the game and switch between them
 * @author Gerard Gascón
 */
public class SceneManager {
    private boolean sceneLoaded = false;
    private Scene scene;
    private Pair<Class<? extends Scene>, Object[]> sceneToLoad;

    private static SceneManager instance;

    /**
     * Get or instantiate an instance of SceneManager
     * @return The instance of SceneManager
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    private SceneManager() {
    }

    /**
     * Loads a new scene. If there's one active, it destroys that one before loading the new one
     * @param sceneClass The class type of the new scene
     * @param dependencies The dependencies of the new scene
     * @see Scene
     */
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

    /**
     * Closes the game
     */
    public void quit() {
        scene.onDetach();
        scene = null;
    }

    /**
     * Updates the scenes and its objects
     * @param delta Time difference from the last call
     * @see Scene
     * @see SceneObject
     */
    public void process(float delta) {
        if (sceneToLoad != null) {
            loadScene(sceneToLoad.first(), sceneToLoad.second());
            sceneToLoad = null;
            return;
        }
        scene.onProcess(delta);
    }

    /**
     * Check if there's an active scene
     * @return <b>true</b> if there is an active scene
     * <b>false</b> if there are no active scenes
     */
    public boolean isRunning() {
        return scene != null;
    }
}
