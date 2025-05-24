package edu.upc.prop.scrabble.presenter.scenes.exceptions;

import edu.upc.prop.scrabble.presenter.scenes.SceneObject;

/**
 * Excepció llançada quan un {@link SceneObject} no pot tenir un constructor amb paràmetres.
 * <p>
 * Aquesta excepció s'utilitza quan es tracta de crear una instància de {@code SceneObject}
 * amb un constructor que accepta paràmetres, fet que no està permès pel disseny o pel framework.
 * </p>
 *
 * @author Gerard Gascón
 * @see SceneObject
 */
public class SceneObjectWithParametrizedConstructorException extends RuntimeException {
    /**
     * Constructor de l'excepció amb un missatge personalitzat.
     *
     * @param message Missatge explicant la causa de l'excepció.
     */
    public SceneObjectWithParametrizedConstructorException(String message) {
        super(message);
    }
}
