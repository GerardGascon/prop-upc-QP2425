package edu.upc.prop.scrabble.data.crosschecks;

/**
 * Implementació de CrossChecks per l'anglès.
 * Inclou les peçes especials del català: Ç, L·L i NY.
 *
 * @author Albert Usero && Felipe Martínez
 */
public class EnglishCrossChecks extends CrossChecks {
    /**
     * Peces de l'anglès.
     * Faran referència a la seva posició als BitSets.
     */
    private final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"};

    /**
     * Crea una nova instància de CrossChecks per l'anglès.
     * @param boardSize Mida del tauler
     */
    public EnglishCrossChecks(int boardSize) {
        super(boardSize);
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 26;
    }

    @Override
    public Boolean ableToPlace(int x, int y, String letter) {
        return !getCrossCheck(x, y).get(getNumLetter(letter));
    }

    /**
     * Obté l'índex numèric corresponent a una peça que referència
     * la seva posició als BitSets.
     * @param letter Peça a convertir
     * @return Índex numèric de la peça.
     */
    private int getNumLetter(String letter) {
        return letter.charAt(0) - 'A';
    }

    @Override
    protected CrossChecks copy() {
        return new EnglishCrossChecks(boardSize);
    }
}