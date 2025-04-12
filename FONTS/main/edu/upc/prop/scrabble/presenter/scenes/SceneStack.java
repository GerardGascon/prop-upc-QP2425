package edu.upc.prop.scrabble.presenter.scenes;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.List;

public class SceneStack {
    private final List<Scene> scenes = new ArrayList<>();
    private final Map<Class<?>, Object> dependencyInstances = new HashMap<>();

    public void replace(Class<? extends Scene> sceneClass) {
        clear();
        push(sceneClass);
    }

    public void push(Class<? extends Scene> sceneClass) {
        try {
            scenes.add(instantiateScene(sceneClass));
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate scene: " + sceneClass.getName(), e);
        }
    }

    private Scene instantiateScene(Class<? extends Scene> sceneClass) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> target = getSceneConstructor(sceneClass);
        Object[] dependencies = gatherSceneDependencies(target);
        return (Scene)target.newInstance(dependencies);
    }

    private static Constructor<?> getSceneConstructor(Class<? extends Scene> sceneClass) {
        Constructor<?>[] constructors = sceneClass.getDeclaredConstructors();
        return Arrays.stream(constructors)
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new RuntimeException("No constructors found"));
    }

    private Object[] gatherSceneDependencies(Constructor<?> target) {
        Class<?>[] paramTypes = target.getParameterTypes();
        return Arrays.stream(paramTypes)
                .map(dependencyInstances::get)
                .toArray();
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

    public <T> void register(T instance) {
        dependencyInstances.put(instance.getClass(), instance);
    }

    public <T> T resolve(Class<T> clazz) {
        Object instance = dependencyInstances.get(clazz);
        if (instance == null) {
            throw new IllegalStateException("No dependency supplier found for class " + clazz.getName());
        }
        return clazz.cast(instance);
    }
}
