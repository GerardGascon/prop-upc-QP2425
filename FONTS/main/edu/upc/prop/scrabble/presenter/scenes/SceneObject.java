package edu.upc.prop.scrabble.presenter.scenes;

/**
 * Abstract base class representing an object within a scene.
 * <p>
 * This class provides basic functionality for enabling, disabling, and processing scene objects.
 * It also handles the integration with the scene management system, allowing scene objects
 * to be instantiated and destroyed as needed.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class SceneObject {
    private boolean enabled;
    private Scene scene;

    /**
     * Sets the scene that this object belongs to.
     *
     * @param scene The scene to associate with this object
     */
    void setScene(Scene scene) {
        this.scene = scene;
    }

    /**
     * Disables the object and performs any additional logic when the object is disabled.
     * This method is called when the object is disabled from the scene.
     *
     * @see #onDisable()
     */
    public final void disable() {
        enabled = false;
        onDisable();
    }

    /**
     * Enables the object and performs any additional logic when the object is enabled.
     * This method is called when the object is enabled in the scene.
     *
     * @see #onEnable()
     */
    public final void enable() {
        enabled = true;
        onEnable();
    }

    /**
     * Checks if the object is enabled.
     *
     * @return <b>true</b> if the object is enabled, <b>false</b> otherwise
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * The method that is called to process the object during the scene update.
     * This can be overridden by subclasses to implement specific behavior.
     *
     * @param delta Time difference from the last call (in seconds)
     */
    public void onProcess(float delta) {
    }

    /**
     * Called when the object is detached from the scene.
     * Subclasses can override this method to handle the detachment process.
     */
    public void onDetach() {
    }

    /**
     * Called when the object is enabled. Can be overridden by subclasses to implement
     * custom behavior when the object is enabled in the scene.
     */
    protected void onEnable() {
    }

    /**
     * Called when the object is disabled. Can be overridden by subclasses to implement
     * custom behavior when the object is disabled in the scene.
     */
    protected void onDisable() {
    }

    /**
     * Instantiates a new scene object within the current scene.
     *
     * @param <T> The type of scene object to instantiate
     * @param o The class of the scene object to instantiate
     * @return The newly instantiated scene object
     * @see Scene#instantiate(Class)
     */
    protected <T extends SceneObject> T instantiate(Class<T> o) {
        return scene.instantiate(o);
    }

    /**
     * Destroys a scene object and removes it from the current scene.
     *
     * @param o The scene object to destroy
     * @see Scene#destroy(SceneObject)
     */
    protected final void destroy(SceneObject o) {
        scene.destroy(o);
    }
}
