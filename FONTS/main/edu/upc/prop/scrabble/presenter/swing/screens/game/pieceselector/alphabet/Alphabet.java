package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.alphabet;

import java.util.HashSet;

public abstract class Alphabet {
    protected final HashSet<String> alphabet = new HashSet<>();

    public boolean isValid(String piece) {
        return alphabet.contains(piece.trim());
    }
}
