package edu.upc.prop.scrabble.utils;

/**
 * Una classe genèrica simple per emmagatzemar un parell de valors de tipus potencialment diferents.
 *
 * @param <X> El tipus del primer element del parell.
 * @param <Y> El tipus del segon element del parell.
 *
 * @author Gerard Gascón
 */
public record Pair<X, Y>(X first, Y second) {

    /**
     * Obté el primer element del parell.
     *
     * @return El primer element de tipus X.
     */
    public X first() {
        return first;
    }

    /**
     * Obté el segon element del parell.
     *
     * @return El segon element de tipus Y.
     */
    public Y second() {
        return second;
    }
}
