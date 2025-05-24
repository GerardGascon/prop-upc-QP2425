package edu.upc.prop.scrabble.data.crosschecks;

/**
 * Implementació de CrossChecks pel castellà.
 * Inclou les peçes especials del castellà: Ñ, RR, LL i CH.
 *
 * @author Albert Usero
 * @author Felipe Martínez Lassalle
 */
public class SpanishCrossChecks extends CrossChecks {
    /**
     * Peces de l'anglès + posició 27 per la Ñ, 28 per la RR, 29 per LL i 30 per CH.
     * Faran referència a la seva posició als BitSets.
     */
    private final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ñ", "RR", "LL", "CH"};
    /**
     * Crea una nova instància de CrossChecks pel castellà.
     * @param boardSize Mida del tauler
     */
    public SpanishCrossChecks(int boardSize) {
        super(boardSize);
    }

    /**
     * Obté el conjunt de peces vàlides pel castellà
     * @return Array de Strings amb totes les peces del castellà
     */
    @Override
    public String[] getLetters() {
        return letters;
    }

    /**
     * Obté el nombre total de peces diferents del castellà
     * @return Nombre total de peces del castellà
     */
    @Override
    public int getNumberOfLetters() {
        return 30;
    }

    /**
     * Determina si es pot col·locar una peça específica en una posició del tauler
     * per peces en castellà.
     * @param x Coordenada x de la posició a comprovar
     * @param y Coordenada y de la posició a comprovar
     * @param letter Peça a verificar
     * @return True si es pot col·locar la peça, False altrament
     */
    @Override
    public Boolean ableToPlace(int x, int y, String letter) {
        return !getCrossCheck(x, y).get(getNumLetter(letter));
    }
    /**
     * Obté l'índex numèric corresponent a una peça que referència
     * la seva posició als BitSets. Contempla les peces del castellà.
     * @param letter Peça a convertir
     * @return Índex numèric de la peça.
     */
    private int getNumLetter(String letter) {
        if (letter.length() == 1) {
            if (letter.charAt(0) == 'Ñ')
                return 27;
            return letter.charAt(0) - 'A';
        }
        if (letter.charAt(0) == 'R')
            return 28;
        if (letter.charAt(0) == 'L')
            return 29;
        return 30;
    }
    /**
     * Crea una còpia dels Spanishcrosschecks actuals.
     * @return Nova instància de Spanishcrosschecks amb la mateixa configuració que l'actual.
     */
    @Override
    protected CrossChecks copy() {
        return new SpanishCrossChecks(boardSize);
    }
}