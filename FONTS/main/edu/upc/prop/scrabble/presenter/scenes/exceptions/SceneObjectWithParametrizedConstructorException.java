package edu.upc.prop.scrabble.presenter.scenes.exceptions;

/**
 * Thrown to indicate that a SceneObject can't have a constructor
 * @author Gerard Gascón
 * @see edu.upc.prop.scrabble.presenter.scenes.SceneObject
 */
public class SceneObjectWithParametrizedConstructorException extends RuntimeException {
    public SceneObjectWithParametrizedConstructorException(String message) {
        super(message);
    }
}
