package scrabble.scenes;

import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import org.junit.Test;
import scrabble.stubs.SceneObjectStub;
import scrabble.stubs.SceneStub;

import static org.junit.Assert.*;

public class TestSceneObject {
    @Test
    public void createSceneObjectCallsConstructor() {
        Scene scene = new SceneStub();

        SceneObjectStub sut = scene.instantiate(SceneObjectStub.class);

        assertTrue(sut.isConstructorCalled());
    }

    @Test
    public void createSceneObjectIsEnabledByDefault() {
        Scene scene = new SceneStub();

        SceneObjectStub sut = scene.instantiate(SceneObjectStub.class);

        assertTrue(sut.isEnabled());
    }

    @Test
    public void disableObjectDisablesIt() {
        Scene scene = new SceneStub();

        SceneObjectStub sut = scene.instantiate(SceneObjectStub.class);
        sut.disable();

        assertFalse(sut.isEnabled());
    }

    @Test
    public void sceneProcessCallsObjectProcessMethod() {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.load(SceneStub.class);
        sceneManager.process(2);
        Scene scene = sceneManager.getActiveScene();

        SceneObjectStub sut = scene.instantiate(SceneObjectStub.class);
        sceneManager.process(2);

        assertTrue(sut.isOnProcessCalled());
        assertEquals(2.0f, sut.getOnProcessDelta(), 0.0001f);
    }

    @Test
    public void sceneProcessDoesNotCallObjectProcessMethodWhenDisabled() {
        SceneManager sceneManager = SceneManager.getInstance();
        sceneManager.load(SceneStub.class);
        sceneManager.process(2);
        Scene scene = sceneManager.getActiveScene();

        SceneObjectStub sut = scene.instantiate(SceneObjectStub.class);
        sut.disable();
        sceneManager.process(2);

        assertFalse(sut.isOnProcessCalled());
    }

    @Test
    public void destroyObjectCallsDetachMethod() {
        Scene scene = new SceneStub();

        SceneObjectStub sut = scene.instantiate(SceneObjectStub.class);
        scene.destroy(sut);

        assertTrue(sut.isOnDetachCalled());
    }
}
