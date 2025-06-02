package edu.upc.prop.scrabble.presenter.scenes;

import edu.upc.prop.scrabble.utils.Pair;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

/**
 * Classe utilitzada per gestionar totes les escenes presents al joc i per canviar entre elles.
 * <p>
 * El SceneManager és responsable de carregar i gestionar diferents escenes durant el joc.
 * Gestiona les transicions entre escenes, la destrucció de les escenes en fer el canvi i assegura
 * que es passen les dependències correctes quan es carrega una nova escena.
 * </p>
 *
 * @author Gerard Gascón
 */
public class SceneManager {
    /**
     * Indica si ja hi ha una escena carregada.
     */
    private boolean sceneLoaded = false;

    /**
     * Escena actual activa.
     */
    private Scene scene;

    /**
     * Escena pendent de carregar (classe i dependències).
     */
    private Pair<Class<? extends Scene>, Object[]> sceneToLoad;

    /**
     * Instància singleton de SceneManager.
     */
    private static SceneManager instance;

    /**
     * Obté o crea la instància única de SceneManager.
     * <p>
     * Aquest mètode assegura que només existeixi una instància de SceneManager alhora.
     * </p>
     *
     * @return La instància de SceneManager
     */
    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    /**
     * Crea una instància del gestor d'escenes
     */
    private SceneManager() {
    }

    /**
     * Carrega una nova escena. Si n'hi ha una activa, la destrueix abans de carregar la nova.
     * <p>
     * Aquest mètode s'encarrega de fer la transició entre escenes. Si ja hi ha una escena activa,
     * la desenganxa i carrega la nova. Si no n'hi ha, simplement carrega la nova.
     * </p>
     *
     * @param sceneClass   La classe de la nova escena a carregar
     * @param dependencies Les dependències que necessita la nova escena
     * @see Scene
     */
    public void load(Class<? extends Scene> sceneClass, Object... dependencies) {
        if (!sceneLoaded) {
            sceneLoaded = true;
            loadScene(sceneClass, dependencies);
        } else {
            sceneToLoad = new Pair<>(sceneClass, dependencies);
        }
    }

    /**
     * Carrega la nova escena proporcionada, desenganxant l'escena actual si n'hi ha.
     *
     * @param sceneClass   La classe de la nova escena
     * @param dependencies Les dependències per passar al constructor de l'escena
     */
    private void loadScene(Class<? extends Scene> sceneClass, Object... dependencies) {
        try {
            if (scene != null)
                scene.onDetach();

            scene = instantiateScene(sceneClass, dependencies);
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate scene: " + sceneClass.getName(), e);
        }
    }

    /**
     * Instància l'escena amb el constructor que millor s'adapti a les dependències.
     *
     * @param sceneClass   Classe de l'escena a instanciar
     * @param dependencies Paràmetres per passar al constructor
     *
     * @throws InstantiationException si hi ha un problema mentre s'instancia l'escena
     * @throws IllegalAccessException si hi ha un problema mentre s'instancia l'escena
     * @throws InvocationTargetException si hi ha un problema mentre s'instancia l'escena
     *
     * @return La instància de l'escena creada
     */
    private Scene instantiateScene(Class<? extends Scene> sceneClass, Object... dependencies) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        Constructor<?> target = getSceneConstructor(sceneClass);
        return (Scene) target.newInstance(dependencies);
    }

    /**
     * Obté el constructor amb més paràmetres de la classe d'escena, que serà el que s'utilitzarà per instanciar.
     *
     * @param sceneClass La classe de l'escena
     * @return El constructor amb més paràmetres
     */
    private static Constructor<?> getSceneConstructor(Class<? extends Scene> sceneClass) {
        Constructor<?>[] constructors = sceneClass.getDeclaredConstructors();
        return Arrays.stream(constructors)
                .max(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new RuntimeException("No s'han trobat constructors"));
    }

    /**
     * Tanca el joc i destrueix l'escena activa.
     * <p>
     * Aquest mètode s'encarrega de netejar recursos i tancar el joc, desenganxant l'escena activa abans de tancar.
     * </p>
     */
    public void quit() {
        scene.onDetach();
        scene = null;
    }

    /**
     * Actualitza l'escena activa i els seus objectes.
     * <p>
     * Aquest mètode s'executa contínuament per actualitzar l'escena actual. Si hi ha una escena pendent de carregar,
     * la carrega. En cas contrari, processa l'escena activa.
     * </p>
     *
     * @param delta Temps transcorregut des de l'última crida
     * @see Scene
     * @see SceneObject
     */
    public void process(float delta) {
        if (sceneToLoad != null) {
            loadScene(sceneToLoad.first(), sceneToLoad.second());
            sceneToLoad = null;
            return;
        }
        scene.onProcess(delta);
    }

    /**
     * Comprova si hi ha una escena activa en execució.
     * <p>
     * Aquest mètode permet al joc o aplicació saber si hi ha una escena activa amb la qual interactuar.
     * </p>
     *
     * @return <b>true</b> si hi ha una escena activa, <b>false</b> si no n'hi ha cap
     */
    public boolean isRunning() {
        return scene != null;
    }

    /**
     * Obté l'escena activa actual.
     *
     * @return L'escena activa
     */
    public Scene getActiveScene() {
        return scene;
    }
}
