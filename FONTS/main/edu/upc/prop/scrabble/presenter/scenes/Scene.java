package edu.upc.prop.scrabble.presenter.scenes;

import edu.upc.prop.scrabble.presenter.scenes.exceptions.SceneObjectWithParametrizedConstructorException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for representing an in-game scene.
 * <p>
 * This class provides the ability to manage and interact with objects within a scene, including processing
 * updates, instantiating objects, and handling the destruction of objects.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class Scene {
    private final List<SceneObject> objects = new ArrayList<>();

    /**
     * Continuously updates the objects present in the scene.
     * <p>
     * This method is called repeatedly to process any updates for the scene objects.
     * </p>
     *
     * @param delta Time difference from the last update (delta time)
     * @see SceneObject
     */
    void onProcess(float delta) {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.onProcess(delta);
        }
    }

    /**
     * Detaches all scene objects present in the current scene.
     * <p>
     * This method disables and detaches all objects, effectively removing them from the scene.
     * </p>
     *
     * @see SceneObject
     */
    void onDetach() {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.disable();
            o.onDetach();
        }
    }

    /**
     * Instantiates a new object in the scene.
     * <p>
     * This method tries to create a new instance of the given class and add it to the scene. If the class
     * has a constructor with parameters, it will throw an exception, as such constructors are not supported.
     * </p>
     *
     * @param o The class of the object to instantiate
     * @return The newly instantiated object
     * @throws SceneObjectWithParametrizedConstructorException If the object to be instantiated has a constructor with parameters
     * @throws RuntimeException If an error occurs during the object creation
     * @see SceneObject
     */
    public <T extends SceneObject> T instantiate(Class<T> o) {
        try {
            Constructor<?> target = o.getConstructor();
            T object;
            try {
                object = o.cast(target.newInstance());
            } catch (IllegalArgumentException e) {
                throw new SceneObjectWithParametrizedConstructorException("Parametrized constructors on SceneObject class are not supported.");
            }

            objects.add(object);
            object.setScene(this);
            object.enable();

            return object;
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate scene: " + o.getName(), e);
        }
    }

    /**
     * Destroys an object from the scene.
     * <p>
     * This method removes the specified object from the scene, disables it, and detaches it.
     * </p>
     *
     * @param o Instance of the object to destroy
     * @throws NullPointerException If the object to destroy is null
     * @see SceneObject
     */
    public void destroy(SceneObject o) {
        if (o == null)
            throw new NullPointerException("Object cannot be null");
        objects.remove(o);
        o.disable();
        o.onDetach();
    }
}
