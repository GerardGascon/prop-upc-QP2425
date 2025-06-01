package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

/**
 * Classe que converteix una cadena de lletres en peces en castellà.
 * Gestiona casos com RR, LL, CH.
 * @author Gina Escofet González
 */
public class SpanishPiecesConverter extends PiecesConverter {
    /**
     * Crea una instància del convertidor de fitxes per a l'idioma castellà amb el diccionari per defecte.
     * Aquest diccionari hauria d'incloure fitxes bàsiques i combinacions especials com "RR", "LL" i "CH".
     */
    public SpanishPiecesConverter() {
        super();
    }
    /**
     * Crea una instància del convertidor de fitxes per a l'idioma castellà amb un diccionari personalitzat.
     * Aquest diccionari permet definir les puntuacions específiques de les fitxes, incloses les especials.
     *
     * @param dictionary Array de {@link Piece} que defineixen les fitxes i la seva puntuació.
     */
    public SpanishPiecesConverter(Piece[] dictionary) {
        super(dictionary);
    }
    /**
     * Analitza la paraula per detectar combinacions especials en castellà que s'han de considerar
     * com una sola fitxa: "RR", "LL" o "CH". Si es detecta una d'aquestes combinacions, es crea
     * una nova {@link Piece} amb la puntuació corresponent.
     *
     * @param c Caràcter actual (pot ser minúscula si és un comodí).
     * @param i Índex del caràcter dins la paraula.
     * @param word Paraula completa que s'està convertint en fitxes.
     * @return Una instància de {@link Piece} si es detecta una combinació especial; altrament, {@code null}.
     */
    @Override
    protected Piece parseSpecialPiece(char c, int i, String word) {
        boolean isBlank = Character.isLowerCase(c);
        String capsWord = word.toUpperCase();
        //CASTELLÀ -> RR
        if (capsWord.charAt(i) == 'R' && i < (capsWord.length() - 1) && capsWord.charAt(i + 1) == 'R')
            return generatePiece(capsWord, i, isBlank);
        //CASTELLÀ -> LL
        if (capsWord.charAt(i) == 'L' && i < (capsWord.length() - 1) && capsWord.charAt(i + 1) == 'L')
            return generatePiece(capsWord, i, isBlank);
        //CASTELLÀ -> CH
        if (capsWord.charAt(i) == 'C' && i < (capsWord.length() - 1) && capsWord.charAt(i + 1) == 'H')
            return generatePiece(capsWord, i, isBlank);
        return null;
    }
    /**
     * Crea una nova fitxa {@link Piece} a partir d'una combinació de dos caràcters
     * detectada com a fitxa especial en castellà.
     *
     * @param word Paraula en majúscules que s'està analitzant.
     * @param i Índex on comença la combinació especial.
     * @param isBlank Indica si la fitxa és un comodí (sense puntuació).
     * @return Una nova instància de {@link Piece} amb puntuació adequada.
     */
    private Piece generatePiece(String word, int i, boolean isBlank) {
        String pieceLetter = word.substring(i, i + 2);
        int score = getPieceScore(pieceLetter, isBlank);
        return new Piece(pieceLetter, score, isBlank);
    }
    /**
     * Calcula la puntuació d'una fitxa donada.
     * Retorna 0 si la fitxa és un comodí.
     *
     * @param piece Text de la fitxa (ex: "RR", "LL", "CH").
     * @param isBlank Indica si la fitxa és un comodí (sense puntuació).
     * @return La puntuació assignada segons el diccionari, o 0 si és un comodí.
     */
    private int getPieceScore(String piece, boolean isBlank) {
        if (isBlank)
            return 0;
        return findPieceScore(piece);
    }
}
