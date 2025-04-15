package edu.upc.prop.scrabble.presenter.scenes;

import edu.upc.prop.scrabble.presenter.scenes.exceptions.SceneObjectWithParametrizedConstructorException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Base class for representing an in-game scene
 * @author Gerard Gascón
 */
public abstract class Scene {
    private final List<SceneObject> objects = new ArrayList<>();

    /**
     * Constantly call to update the objects present in the scene
     * @param delta Time difference from the last call
     * @see SceneObject
     */
    void onProcess(float delta) {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.onProcess(delta);
        }
    }

    /**
     * Detaches all scene objects present on the current scene
     * @see SceneObject
     */
    void onDetach() {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.onDisable();
            o.onDetach();
        }
    }

    /**
     * Instantiates a new object on the scene
     * @param o The class of the object to instantiate
     * @return The instance of the newly instantiated object
     * @throws SceneObjectWithParametrizedConstructorException If the object to be instantiated has a constructor with parameters
     * @throws RuntimeException If an error occurs during the object creation
     * @see SceneObject
     */
    public <T extends SceneObject> T instantiate(Class<T> o) {
        try {
            Constructor<?> target = getObjectConstructor(o);
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

    private static Constructor<?> getObjectConstructor(Class<? extends SceneObject> sceneClass) {
        Constructor<?>[] constructors = sceneClass.getDeclaredConstructors();
        return Arrays.stream(constructors)
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new RuntimeException("No constructors found"));
    }

    /**
     * Destroy an object from the scene
     * @param o Instance of the object to destroy
     * @throws NullPointerException If the object to destroy is null
     * @see SceneObject
     */
    public void destroy(SceneObject o) {
        if (o == null)
            throw new NullPointerException("Object cannot be null");
        objects.remove(o);
        o.onDisable();
        o.onDetach();
    }
}
