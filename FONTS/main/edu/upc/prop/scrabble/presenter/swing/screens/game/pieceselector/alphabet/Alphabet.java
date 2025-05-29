package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.alphabet;

import java.util.HashSet;

/**
 * Classe abstracta que representa un alfabet de lletres vàlides per al joc.
 * <p>
 * Aquesta classe proporciona la funcionalitat bàsica per validar si una cadena
 * representa una peça vàlida segons l'alfabet definit. Les subclasses hauran d'omplir
 * l'estructura {@code alphabet} amb les lletres vàlides corresponents.
 * </p>
 *
 * @author Gerard Gascón
 */
public abstract class Alphabet {

    /**
     * Conjunt de cadenes que representen les peces vàlides dins de l'alfabet.
     */
    protected final HashSet<String> alphabet = new HashSet<>();

    /**
     * Comprova si una peça és vàlida dins de l'alfabet.
     *
     * @param piece la cadena que representa la peça a validar
     * @return {@code true} si la peça és vàlida segons l'alfabet, {@code false} altrament
     */
    public boolean isValid(String piece) {
        return alphabet.contains(piece.trim());
    }
}
