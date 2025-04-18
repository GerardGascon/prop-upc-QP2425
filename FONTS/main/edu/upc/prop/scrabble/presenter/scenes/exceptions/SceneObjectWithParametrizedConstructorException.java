package edu.upc.prop.scrabble.presenter.scenes.exceptions;

import edu.upc.prop.scrabble.presenter.scenes.SceneObject;

/**
 * Exception thrown to indicate that a SceneObject can't have a constructor with parameters.
 * <p>
 * This exception is typically used when attempting to create a SceneObject instance with a constructor
 * that takes parameters, which is not supported by the design or framework.
 * </p>
 *
 * @author Gerard Gascón
 * @see SceneObject
 */
public class SceneObjectWithParametrizedConstructorException extends RuntimeException {
    public SceneObjectWithParametrizedConstructorException(String message) {
        super(message);
    }
}
