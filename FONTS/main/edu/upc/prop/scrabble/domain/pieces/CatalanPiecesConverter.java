package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
/**
 * Classe que converteix una cadena de lletres en peces en català.
 * Gestiona casos com L·L i NY.
 * @author Gina Escofet González
 */
public class CatalanPiecesConverter extends PiecesConverter {
    /**
     * Crea una instància del convertidor de fitxes per a l'idioma català utilitzant el diccionari per defecte.
     * Aquest diccionari hauria d'incloure les fitxes bàsiques del joc, així com les combinacions especials catalanes.
     */
    public CatalanPiecesConverter() {
        super();
    }

    /**
     * Crea una instància del convertidor de fitxes per a l'idioma català amb un diccionari personalitzat.
     * Aquest diccionari permet definir la puntuació de les fitxes, incloent-hi combinacions especials com "NY" o "L·L".
     *
     * @param dictionary Array de fitxes {@link Piece} que defineixen les puntuacions i representacions disponibles.
     */
    public CatalanPiecesConverter(Piece[] dictionary) {
        super(dictionary);
    }

    /**
     * Analitza i converteix una combinació especial de lletres catalanes a una fitxa (`Piece`).
     * Aquest mètode tracta casos particulars com "L·L" i "NY", que són considerats fitxes especials en català.
     *
     * @param c Caràcter actual del mot original (pot estar en minúscula si és comodí).
     * @param i Índex actual dins de la paraula on es troba el caràcter.
     * @param word Paraula completa (possiblement conté combinacions especials com "L·L" o "NY").
     * @return Una instància de {@link Piece} si es detecta una combinació especial; altrament, retorna {@code null}.
     */
    @Override
    protected Piece parseSpecialPiece(char c, int i, String word) {
        boolean isBlank = Character.isLowerCase(c);
        String capsWord = word.toUpperCase();
        // CATALÀ -> L·L
        if (capsWord.charAt(i) == 'L' && i < (capsWord.length() - 2) && capsWord.charAt(i + 1) == '·'){
            String pieceLetter = capsWord.substring(i, i + 3);
            int score = getPieceScore(pieceLetter, isBlank);
            return new Piece(pieceLetter, score, isBlank);
        }
        // CATALÀ -> NY
        if (capsWord.charAt(i) == 'N' && i < (capsWord.length() - 1) && capsWord.charAt(i + 1) == 'Y'){
            String pieceLetter = capsWord.substring(i, i + 2);
            int score = getPieceScore(pieceLetter, isBlank);
            return new Piece(pieceLetter, score, isBlank);
        }
        return null;
    }

    /**
     * Calcula la puntuació d'una fitxa a partir del seu valor textual.
     * Si la fitxa és un comodí (lletra minúscula), retorna una puntuació de 0.
     * @param piece Representació textual de la fitxa (pot ser una lletra o una combinació especial com "NY" o "L·L").
     * @param isBlank Indica si la fitxa és un comodí (sense puntuació).
     * @return La puntuació associada a la fitxa segons el diccionari, o 0 si és un comodí.
     */
    private int getPieceScore(String piece, boolean isBlank) {
        if (isBlank)
            return 0;
        return findPieceScore(piece);
    }
}
