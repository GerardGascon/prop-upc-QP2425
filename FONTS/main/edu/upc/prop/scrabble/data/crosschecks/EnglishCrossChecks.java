package edu.upc.prop.scrabble.data.crosschecks;

/**
 * Implementació de CrossChecks per l'anglès.
 * Inclou les peçes especials del català: Ç, L·L i NY.
 *
 * @author Albert Usero
 * @author Felipe Martínez Lassalle
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

    /**
     * Obté el conjunt de peces vàlides per l'anglès.
     * @return Array de Strings amb totes les peces de l'anglès
     */
    @Override
    public String[] getLetters() {
        return letters;
    }

    /**
     * Obté el nombre total de peces diferents de l'anglès
     * @return Nombre total de peces de l'anglès
     */
    @Override
    public int getNumberOfLetters() {
        return 26;
    }

    /**
     * Determina si es pot col·locar una peça específica en una posició del tauler
     * per peces en anglès.
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
     * la seva posició als BitSets.
     * @param letter Peça a convertir
     * @return Índex numèric de la peça.
     */
    private int getNumLetter(String letter) {
        return letter.charAt(0) - 'A';
    }

    /**
     * Crea una còpia dels Englishcrosschecks actuals.
     * @return Nova instància de Englishcrosschecks amb la mateixa configuració que l'actual.
     */
    @Override
    protected CrossChecks copy() {
        return new EnglishCrossChecks(boardSize);
    }
}