package edu.upc.prop.scrabble.utils;

import java.util.Random;

/**
 * Rand is a concrete implementation of the IRand interface.
 * It provides methods to generate random numbers using the java.util.Random class.
 *
 * @see IRand
 * @see Random
 */
public class Rand implements IRand {
    private final Random random = new Random();

    /**
     * Returns a random integer. This method generates random integers across the entire range of integer values.
     *
     * @return a random integer.
     */
    @Override
    public int nextInt() {
        return random.nextInt();
    }

    /**
     * Returns a random integer from 0 (inclusive) to the specified bound (exclusive).
     *
     * @param bound the upper bound (exclusive) for the random number.
     * @return a random integer in the range [0, bound).
     */
    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    /**
     * Returns a random integer between the specified origin (inclusive) and bound (exclusive).
     *
     * @param origin the lower bound (inclusive) for the random number.
     * @param bound  the upper bound (exclusive) for the random number.
     * @return a random integer in the range [origin, bound).
     */
    @Override
    public int nextInt(int origin, int bound) {
        return random.nextInt(origin, bound);
    }
}
