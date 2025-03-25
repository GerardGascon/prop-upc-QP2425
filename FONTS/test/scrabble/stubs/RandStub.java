package scrabble.stubs;

import edu.upc.prop.scrabble.utils.IRand;

public record RandStub(int nextInt) implements IRand {
    @Override
    public int nextInt(int bound) {
        return nextInt;
    }

    @Override
    public int nextInt(int origin, int bound) {
        return nextInt;
    }
}
