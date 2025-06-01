package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Pair;

/**
 * Classe responsable de generar objectes {@code Piece} a partir del contingut d'un fitxer de fitxes.
 * <p>
 * La classe {@code PieceGenerator} analitza el contingut d'un fitxer de fitxes (una cadena de text)
 * per crear instàncies de {@code Piece}. Cada {@code Piece} representa una lletra, el seu valor i el nombre
 * d’aparicions d’aquesta lletra. El resultat es retorna com un array de {@code Pair<Piece, Integer>},
 * on {@code Piece} representa la lletra i les seves propietats, i l’enter representa el nombre d’unitats d’aquesta fitxa.
 * </p>
 *
 * @author Gerard Gascón
 */
public class PieceGenerator {
    /**
     * Crea una instància del generador de fitxes.
     */
    public PieceGenerator() {

    }

    /**
     * Converteix el contingut d’un fitxer de fitxes en un array d’objectes `Piece` i les seves quantitats.
     * <p>
     * El fitxer es processa línia per línia, on cada línia ha de contenir la informació sobre el caràcter,
     * la quantitat i el valor de la fitxa. El resultat és un array de `Pair&lt;Piece, Integer&gt;`,
     * on cada parella conté un objecte `Piece` i el nombre d’aparicions d’aquesta fitxa.
     * </p>
     *
     * @param pieces El contingut del fitxer de fitxes com una cadena de text.
     * @return Un array de parelles on cada parella conté una `Piece` i la seva quantitat.
     * @see Pair
     * @see Piece
     */
    public Pair<Piece, Integer>[] run(String pieces) {
        String[] pieceArray = pieces.lines().toArray(String[]::new);
        Pair<Piece, Integer>[] result = new Pair[pieceArray.length];

        for (int i = 0; i < pieceArray.length; i++) {
            result[i] = generatePiece(pieceArray[i]);
        }

        return result;
    }

    /**
     * Genera una parella de `Piece` i el seu nombre d’aparicions a partir d’una línia de text.
     *
     * @param piece La línia de text amb la informació de la fitxa.
     * @return Una parella amb la `Piece` creada i la seva quantitat.
     */
    private Pair<Piece, Integer> generatePiece(String piece) {
        String character = parseCharacter(piece);
        int value = parseValue(piece);
        int count = parseCount(piece);
        boolean isBlank = character.equals("#");

        return new Pair<>(new Piece(character, value, isBlank), count);
    }

    /**
     * Extreu la quantitat d’unitats d’una fitxa a partir de la línia.
     *
     * @param piece La línia de text amb la informació de la fitxa.
     * @return El nombre d’unitats d’aquesta fitxa.
     */
    private int parseCount(String piece) {
        String[] values = piece.split("\\s+");
        return Integer.parseInt(values[1]);
    }

    /**
     * Extreu el valor de la fitxa a partir de la línia.
     *
     * @param piece La línia de text amb la informació de la fitxa.
     * @return El valor de la fitxa.
     */
    private int parseValue(String piece) {
        String[] values = piece.split("\\s+");
        return Integer.parseInt(values[2]);
    }

    /**
     * Extreu el caràcter que representa la fitxa a partir de la línia.
     *
     * @param piece La línia de text amb la informació de la fitxa.
     * @return El caràcter de la fitxa.
     */
    private String parseCharacter(String piece) {
        String[] values = piece.split("\\s+");
        return values[0];
    }
}
