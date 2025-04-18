package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
/***
 * Class that converts a string of letters into pieces in Spanish.
 * It handles cases like RR, LL, CH
 * @author Gina Escofet González
 */
public class SpanishPiecesConverter extends PiecesConverter {
    public SpanishPiecesConverter() {
        super();
    }

    public SpanishPiecesConverter(Piece[] dictionary) {
        super(dictionary);
    }

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

    private Piece generatePiece(String word, int i, boolean isBlank) {
        String pieceLetter = word.substring(i, i + 2);
        int score = getPieceScore(pieceLetter, isBlank);
        return new Piece(pieceLetter, score, isBlank);
    }

    private int getPieceScore(String piece, boolean isBlank) {
        if (isBlank)
            return 0;
        return findPieceScore(piece);
    }
}
