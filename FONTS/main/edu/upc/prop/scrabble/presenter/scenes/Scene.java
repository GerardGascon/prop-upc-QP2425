package edu.upc.prop.scrabble.presenter.scenes;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public abstract class Scene {
    private final List<SceneObject> objects = new ArrayList<>();

    void onProcess(float delta) {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.onProcess(delta);
        }
    }

    void onDetach() {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.onDisable();
            o.onDetach();
        }
    }

    public <T extends SceneObject> T instantiate(Class<T> o) {
        try {
            Constructor<?> target = getObjectConstructor(o);
            T object;
            try {
                object = o.cast(target.newInstance());
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Parametrized constructors on SceneObject class are not supported.");
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

    public void destroy(SceneObject o) {
        objects.remove(o);
        o.onDisable();
        o.onDetach();
    }
}
