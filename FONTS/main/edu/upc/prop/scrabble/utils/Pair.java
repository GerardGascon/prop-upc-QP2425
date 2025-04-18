package edu.upc.prop.scrabble.utils;

/**
 * A simple generic pair class to hold two values of potentially different types.
 *
 * @param <X> The type of the first element in the pair.
 * @param <Y> The type of the second element in the pair.
 *
 * @author Gerard Gascón
 */
public record Pair<X, Y>(X first, Y second) {

    /**
     * Gets the first element of the pair.
     *
     * @return The first element of type X.
     */
    public X first() {
        return first;
    }

    /**
     * Gets the second element of the pair.
     *
     * @return The second element of type Y.
     */
    public Y second() {
        return second;
    }
}
