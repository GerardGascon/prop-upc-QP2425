package edu.upc.prop.scrabble.data;
import java.util.*;

public class Piece {
    //atributs d'una piece
    private final char letter;
    private final int value;

    // constructora d'una piece
    public Piece(char letter, int value) {
        this.letter = letter;
        this.value = value;
    }

    // funcions getter
    public char getLetter() {
        return letter;
    }

    public int getValue() {
        return value;
    }

    // funcions setter
    public void setLetter(char letter) {
        this.letter = letter;
    }

    public void setValue(int value) {
        this.value = value;
    }

    // Sortida
    public void printPiece(Piece piece) {
        System.out.println("Letter: " + piece.getLetter() + " and Value: " + piece.getValue());
    }
}
