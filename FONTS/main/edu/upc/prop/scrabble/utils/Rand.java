package edu.upc.prop.scrabble.utils;

import java.util.Random;

public class Rand implements IRand {
    private final Random random = new Random();

    @Override
    public int nextInt() {
        return random.nextInt();
    }

    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    @Override
    public int nextInt(int origin, int bound) {
        return random.nextInt(origin, bound);
    }
}
