package edu.upc.prop.scrabble.presenter.scenes;

public abstract class Scene {
    private boolean freeRequested;

    boolean isFreeRequested() {
        return freeRequested;
    }

    public void onProcess(float delta) {
    }

    public void onDetach() {
    }

    public final void free() {
        freeRequested = true;
    }
}
