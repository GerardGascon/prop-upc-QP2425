package edu.upc.prop.scrabble.presenter.scenes;

public abstract class Scene {
    private SceneManager sceneManager;
    void setSceneStack(SceneManager manager) {
        sceneManager = manager;
    }

    public void onProcess(float delta) {
    }

    public void onDetach() {
    }

    public final void quit() {
        sceneManager.quit();
    }

    public final void load(Class<? extends Scene> sceneClass, Object... instances) {
        sceneManager.load(sceneClass, instances);
    }
}
