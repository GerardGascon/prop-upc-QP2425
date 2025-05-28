package edu.upc.prop.scrabble.presenter.scenes;

import edu.upc.prop.scrabble.presenter.scenes.exceptions.SceneObjectWithParametrizedConstructorException;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base per representar una escena dins del joc.
 * <p>
 * Aquesta classe proporciona la funcionalitat per gestionar i interactuar amb els objectes dins d'una escena,
 * incloent-hi el processament d'actualitzacions, la creació d'objectes i la gestió de la destrucció d'objectes.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class Scene {
    /**
     * Llista d'objectes que pertanyen a aquesta escena.
     */
    private final List<SceneObject> objects = new ArrayList<>();

    /**
     * Actualitza contínuament els objectes presents a l'escena.
     * <p>
     * Aquest mètode s'anomena repetidament per processar les actualitzacions dels objectes.
     * </p>
     *
     * @param delta Temps transcorregut des de l'última actualització (delta time)
     * @see SceneObject
     */
    void onProcess(float delta) {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.onProcess(delta);
        }
    }

    /**
     * Desenganxa (desconnecta) tots els objectes de l'escena actual.
     * <p>
     * Aquest mètode desactiva i desenganxa tots els objectes, eliminant-los efectivament de l'escena.
     * </p>
     *
     * @see SceneObject
     */
    protected void onDetach() {
        for (SceneObject o : objects) {
            if (o.isEnabled())
                o.disable();
            o.onDetach();
        }
    }

    /**
     * Instancia un nou objecte dins de l'escena.
     * <p>
     * Aquest mètode intenta crear una nova instància de la classe especificada i afegir-la a l'escena.
     * Si la classe té un constructor amb paràmetres, llença una excepció, ja que no es donen suport constructors parametritzats.
     * </p>
     *
     * @param o La classe de l'objecte a instanciar
     * @return L'objecte instanciat
     * @throws SceneObjectWithParametrizedConstructorException Si l'objecte té un constructor amb paràmetres
     * @throws RuntimeException Si hi ha algun error durant la creació de l'objecte
     * @see SceneObject
     */
    public <T extends SceneObject> T instantiate(Class<T> o) {
        try {
            Constructor<?> target = o.getConstructor();
            T object;
            try {
                object = o.cast(target.newInstance());
            } catch (IllegalArgumentException e) {
                throw new SceneObjectWithParametrizedConstructorException("Parametrized constructors on SceneObject class are not supported.");
            }

            objects.add(object);
            object.setScene(this);
            object.enable();

            return object;
        } catch (Exception e) {
            throw new RuntimeException("Failed to instantiate scene: " + o.getName(), e);
        }
    }

    /**
     * Destrueix un objecte de l'escena.
     * <p>
     * Aquest mètode elimina l'objecte especificat de l'escena, el desactiva i el desenganxa.
     * </p>
     *
     * @param o Instància de l'objecte a destruir
     * @throws NullPointerException Si l'objecte a destruir és null
     * @see SceneObject
     */
    public void destroy(SceneObject o) {
        if (o == null)
            throw new NullPointerException("Object cannot be null");
        objects.remove(o);
        o.disable();
        o.onDetach();
    }
}
