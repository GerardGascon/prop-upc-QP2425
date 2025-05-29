package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.alphabet;

/**
 * Representa l'alfabet utilitzat en el joc per a la llengua catalana.
 * <p>
 * Aquesta classe concreta hereta d’{@link Alphabet} i inicialitza
 * el conjunt de lletres vàlides segons l'alfabet català, incloent-hi
 * lletres específiques com la "Ç", "L·L" i "NY".
 * </p>
 *
 * @author Gerard Gascón
 */
public class CatalanAlphabet extends Alphabet {

    /**
     * Crea una nova instància de l'alfabet català inicialitzant
     * totes les lletres vàlides que poden aparèixer en una peça del joc.
     */
    public CatalanAlphabet() {
        alphabet.add("A");
        alphabet.add("B");
        alphabet.add("C");
        alphabet.add("Ç");
        alphabet.add("D");
        alphabet.add("E");
        alphabet.add("F");
        alphabet.add("G");
        alphabet.add("H");
        alphabet.add("I");
        alphabet.add("J");
        alphabet.add("K");
        alphabet.add("L");
        alphabet.add("L·L");
        alphabet.add("M");
        alphabet.add("N");
        alphabet.add("NY");
        alphabet.add("O");
        alphabet.add("P");
        alphabet.add("Q");
        alphabet.add("R");
        alphabet.add("S");
        alphabet.add("T");
        alphabet.add("U");
        alphabet.add("V");
        alphabet.add("W");
        alphabet.add("X");
        alphabet.add("Y");
        alphabet.add("Z");
    }
}
