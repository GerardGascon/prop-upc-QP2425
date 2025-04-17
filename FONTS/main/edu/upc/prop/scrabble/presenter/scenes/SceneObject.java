package edu.upc.prop.scrabble.presenter.scenes;

public abstract class SceneObject {
    private boolean enabled;
    private Scene scene;

    void setScene(Scene scene) {
        this.scene = scene;
    }

    public final void disable() {
        enabled = false;
        onDisable();
    }

    public final void enable() {
        enabled = true;
        onEnable();
    }

    public final boolean isEnabled() {
        return enabled;
    }

    public void onProcess(float delta) {
    }

    public void onDetach() {
    }

    protected void onEnable() {
    }

    protected void onDisable() {
    }

    protected <T extends SceneObject> T instantiate(Class<T> o) {
        return scene.instantiate(o);
    }

    protected final void destroy(SceneObject o) {
        scene.destroy(o);
    }
}
