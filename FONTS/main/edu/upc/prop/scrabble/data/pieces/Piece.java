package edu.upc.prop.scrabble.data.pieces;

import java.util.Objects;

/**
 * Representa una fitxa del joc Scrabble amb una lletra, valor de puntuació i estat buit per fitxes escarrades.
 * @author Gina Escofet González
 */
public final class Piece {
    private String letter;
    private final int value;
    private final boolean isBlank;

    /**
     * Creadora d'una fitxa amb lletra i valor fixos.
     * @param letter La lletra que es mostra a la fitxa.
     * @param value El valor en punts de la fitxa.
     */
    public Piece(String letter, int value) {
        this.letter = letter;
        this.value = value;
        this.isBlank = false;
    }

    /**
     * Crea una fitxa escarràs amb lletra configurable.
     * @param letter La lletra que es mostra a la fitxa.
     * @param value El valor en punts de la fitxa.
     * @param isBlank Indica si és una fitxa buida.
     */
    public Piece(String letter, int value, boolean isBlank) {
        this.letter = letter;
        this.value = value;
        this.isBlank = isBlank;
    }

    /**
     * Obté la lletra que mostra la fitxa.
     * @return La lletra de la fitxa actual.
     */
    public String letter() {
        return letter;
    }

    /**
     * Comprova si és una fitxa buida.
     * @return Cert si és una fitxa buida, fals altrament.
     */
    public boolean isBlank() {
        return isBlank;
    }

    /**
     * Assigna una lletra a una fitxa buida.
     * @param letter La nova lletra per a la fitxa buida.
     */
    public void setLetter(String letter) {
        this.letter = letter;
    }

    /**
     * Obté el valor en punts de la fitxa.
     * @return El valor de la fitxa actual.
     */
    public int value() {
        return value;
    }

    /**
     * Compara si aquesta peça és igual a una altra.
     * @param obj L'objecte a comparar.
     * @return Cert si són iguals, fals altrament.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Piece) obj;
        return Objects.equals(this.letter, that.letter) &&
                this.value == that.value && this.isBlank == that.isBlank;
    }

    /**
     * Retorna una representació en cadena de caràcters de la peça.
     * @return La lletra i el valor de la peça, amb indicació si és buida.
     */
    @Override
    public String toString() {
        if (isBlank)
            return "[Blank]: " + letter + " " + value;
        return letter + " " + value;
    }
}
