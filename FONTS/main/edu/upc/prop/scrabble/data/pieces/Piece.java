package edu.upc.prop.scrabble.data.pieces;

import java.util.Objects;

public final class Piece {
    private String letter;
    private final int value;
    private boolean isBlank;

    public Piece(String letter, int value) {
        this.letter = letter;
        this.value = value;
        this.isBlank = false;
    }

    public Piece(String letter, int value, boolean isBlank) {
        this.letter = letter;
        this.value = value;
        this.isBlank = isBlank;
    }

    public String letter() {
        return letter;
    }

    public boolean isBlank() {
        return isBlank;
    }

    public void setLetter(String letter) {
        this.letter = letter;
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Piece) obj;
        return Objects.equals(this.letter, that.letter) &&
                this.value == that.value;
    }
}
