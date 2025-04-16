package edu.upc.prop.scrabble.data.pieces;

import java.util.Objects;

/***
 * Represents a Scrabble game piece with a letter, point value, and blank status.
 * @author Gina Escofet González
 */
public final class Piece {
    private String letter;
    private final int value;
    private boolean isBlank;

    /***
     * Creates a regular piece with fixed letter and value.
     * @param letter The displayed letter.
     * @param value The point value of the piece.
     */
    public Piece(String letter, int value) {
        this.letter = letter;
        this.value = value;
        this.isBlank = false;
    }

    /***
     * Creates a piece with configurable letter.
     * @param letter The displayed letter.
     * @param value The point value of the piece.
     * @param isBlank Whether it is a blank tile.
     */
    public Piece(String letter, int value, boolean isBlank) {
        this.letter = letter;
        this.value = value;
        this.isBlank = isBlank;
    }

    /***
     * Gets the letter displayed of the piece.
     * @return The letter of the current piece.
     */
    public String letter() {
        return letter;
    }

    /***
     * Checks if it is a blank piece.
     * @return True if is a blank piece, False otherwise.
     */
    public boolean isBlank() {
        return isBlank;
    }

    /***
     * Set the given letter to a blank piece.
     * @param letter The new letter for the blank piece.
     */
    public void setLetter(String letter) {
        this.letter = letter;
    }

    /***
     * Get the point value of the piece.
     * @return The value of the current piece.
     */
    public int value() {
        return value;
    }

    /***
     * Compares this piece to another one for equality.
     * Two pieces are equal if they share the same letter and value.
     * @param obj The object to compare with.
     * @return True if both are equal pieces, False otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Piece) obj;
        return Objects.equals(this.letter, that.letter) &&
                this.value == that.value;
    }
}
