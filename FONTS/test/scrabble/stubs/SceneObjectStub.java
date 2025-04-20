package scrabble.stubs;

import edu.upc.prop.scrabble.presenter.scenes.SceneObject;

public class SceneObjectStub extends SceneObject {
    private final boolean constructorCalled;
    private boolean onProcessCalled;
    private float onProcessDelta;
    private boolean onDetachCalled;

    public SceneObjectStub() {
        constructorCalled = true;
    }

    @Override
    public void onProcess(float delta) {
        super.onProcess(delta);
        onProcessCalled = true;
        onProcessDelta = delta;
    }

    @Override
    public void onDetach(){
        super.onDetach();
        onDetachCalled = true;
    }

    public boolean isConstructorCalled() {
        return constructorCalled;
    }

    public boolean isOnProcessCalled() {
        return onProcessCalled;
    }

    public float getOnProcessDelta() {
        return onProcessDelta;
    }

    public boolean isOnDetachCalled() {
        return onDetachCalled;
    }
}
