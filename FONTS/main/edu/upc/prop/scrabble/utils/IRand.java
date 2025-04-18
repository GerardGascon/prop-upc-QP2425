package edu.upc.prop.scrabble.utils;

/**
 * Interface defining methods for generating random integers.
 * This is useful for randomizing elements in the game, such as tile selection or placement.
 *
 * @author Gerard Gascón
 */
public interface IRand {
    /**
     * Generates a random integer.
     *
     * @return A random integer.
     */
    int nextInt();

    /**
     * Generates a random integer within the specified range.
     *
     * @param bound The upper bound (exclusive) for the random number.
     * @return A random integer between 0 (inclusive) and the given bound (exclusive).
     */
    int nextInt(int bound);

    /**
     * Generates a random integer within the specified range (inclusive of origin, exclusive of bound).
     *
     * @param origin The lower bound (inclusive) for the random number.
     * @param bound  The upper bound (exclusive) for the random number.
     * @return A random integer between origin (inclusive) and bound (exclusive).
     */
    int nextInt(int origin, int bound);
}
