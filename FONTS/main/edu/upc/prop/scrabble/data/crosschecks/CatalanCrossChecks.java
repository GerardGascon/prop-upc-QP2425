package edu.upc.prop.scrabble.data.crosschecks;

/**
 * Implementació de CrossChecks pel català.
 * Inclou les peçes especials del català: Ç, L·L i NY.
 *
 * @author Albert Usero
 * @author Felipe Martínez Lassalle
 */
public class CatalanCrossChecks extends CrossChecks {
    /**
     * Peces de l'anglès + posició 27 per la Ç, 28 per la L·L i 29 per NY.
     * Faran referència a la seva posició als BitSets.
     */
    private final String[] letters = new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ç", "L·L", "NY"};
    /**
     * Crea una nova instància de CrossChecks pel català.
     * @param boardSize Mida del tauler
     */
    public CatalanCrossChecks(int boardSize) {
        super(boardSize);
    }

    @Override
    public String[] getLetters() {
        return letters;
    }

    @Override
    public int getNumberOfLetters() {
        return 29;
    }

    @Override
    public Boolean ableToPlace(int x, int y, String letter) {
        return !getCrossCheck(x, y).get(getNumLetter(letter));
    }

    /**
     * Obté l'índex numèric corresponent a una peça que referència
     * la seva posició als BitSets. Contempla les peces del català.
     * @param letter Peça a convertir
     * @return Índex numèric de la peça.
     */
    private int getNumLetter(String letter) {
        if (letter.length() == 1) {
            if (letter.charAt(0) == 'Ç')
                return 27;
            return letter.charAt(0) - 'A';
        }

        if (letter.charAt(0) == 'L')
            return 28;
        return 29;
    }

    @Override
    protected CrossChecks copy() {
        return new CatalanCrossChecks(boardSize);
    }
}