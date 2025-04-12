package edu.upc.prop.scrabble.presenter.scenes;

import java.util.ArrayList;
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

    public <T extends SceneObject> T instantiate(T o) {
        objects.add(o);
        o.setScene(this);
        o.enable();
        return o;
    }

    public void destroy(SceneObject o) {
        objects.remove(o);
        o.onDisable();
        o.onDetach();
    }
}
