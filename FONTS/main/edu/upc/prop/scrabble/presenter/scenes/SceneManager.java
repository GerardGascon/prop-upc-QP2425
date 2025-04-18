package edu.upc.prop.scrabble.presenter.scenes;

import edu.upc.prop.scrabble.utils.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * Class used to manage all the scenes present in the game and switch between them.
 * <p>
 * The SceneManager is responsible for loading and managing different scenes during the game.
 * It handles the transitions between scenes, the destruction of scenes when switching, and ensures
 * that the proper dependencies are passed when a new scene is loaded.
 * </p>
 *
 * @author Gerard Gascón
 */
public class SceneManager {
    private boolean sceneLoaded = false;
    private Scene scene;
    private Pair<Class<? extends Scene>, Object[]> sceneToLoad;

    private static SceneManager instance;

    /**
     * Get or instantiate an instance of SceneManager.
     * <p>
     * This method ensures that only one instance of the SceneManager exists at any time.
     * </p>
     *
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
     * Loads a new scene. If there's one active, it destroys that one before loading the new one.
     * <p>
     * This method is responsible for switching between scenes. If a scene is already active, it detaches
     * that scene and loads the new one. If no scene is loaded, it simply loads the new scene.
     * </p>
     *
     * @param sceneClass   The class type of the new scene
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
     * Closes the game and destroys the current active scene.
     * <p>
     * This method is responsible for cleaning up resources and closing the game, detaching the active
     * scene before shutting down.
     * </p>
     */
    public void quit() {
        scene.onDetach();
        scene = null;
    }

    /**
     * Updates the active scene and its objects.
     * <p>
     * This method is called continuously to update the current scene. If there is a scene queued to load,
     * it will load it. Otherwise, it processes the current active scene.
     * </p>
     *
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
     * Checks if there is an active scene currently running.
     * <p>
     * This method allows the game or application to check if there is a scene to interact with.
     * </p>
     *
     * @return <b>true</b> if there is an active scene, <b>false</b> if no scene is active
     */
    public boolean isRunning() {
        return scene != null;
    }
}
