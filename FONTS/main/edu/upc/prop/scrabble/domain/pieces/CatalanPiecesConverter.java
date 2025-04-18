package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
/***
 * Class that converts a string of letters into pieces in Catalan.
 * It handles cases like L·L and NY
 * @author Gina Escofet González
 */
public class CatalanPiecesConverter extends PiecesConverter {
    public CatalanPiecesConverter() {
        super();
    }

    public CatalanPiecesConverter(Piece[] dictionary) {
        super(dictionary);
    }

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

    private int getPieceScore(String piece, boolean isBlank) {
        if (isBlank)
            return 0;
        return findPieceScore(piece);
    }
}
