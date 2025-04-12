package edu.upc.prop.scrabble.presenter.scenes;

public abstract class SceneObject {
    private boolean enabled;
    private Scene scene;

    void setScene(Scene scene) {
        this.scene = scene;
    }

    public void disable() {
        enabled = false;
        onDisable();
    }

    public void enable() {
        enabled = true;
        onEnable();
    }

    public boolean isEnabled() {
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

    protected <T extends SceneObject> T instantiate(T o) {
        return scene.instantiate(o);
    }

    protected void destroy(SceneObject o) {
        scene.destroy(o);
    }
}
