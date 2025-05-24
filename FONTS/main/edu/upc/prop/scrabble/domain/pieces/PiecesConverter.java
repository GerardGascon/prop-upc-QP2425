
package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

/***
 * Classe que converteix una cadena de lletres en peces.
 * @author Gina Escofet González
 */
public abstract class PiecesConverter {
    private final HashSet<Piece> pieces = new HashSet<>();

    /**
     * Constructor per defecte de PiecesConverter.
     */
    public PiecesConverter() {
    }

    /**
     * Construeix un PiecesConverter amb un conjunt inicial de peces.
     * @param pieces Un array de peces per inicialitzar el convertidor.
     */
    public PiecesConverter(Piece[] pieces) {
        this.pieces.addAll(Arrays.asList(pieces));
    }

    public Piece[] run(String word) {
        ArrayList<Piece> pieces = new ArrayList<>();
        int i = 0;
        while (i < word.length()) {
            char c = word.charAt(i);

            Piece specialPiece = parseSpecialPiece(c, i, word);
            if (specialPiece != null) {
                pieces.add(specialPiece);
                i += specialPiece.letter().length();
                continue;
            }

            String character = Character.toString(Character.toUpperCase(c));

            int score = findPieceScore(character);
            boolean isBlank = Character.isLowerCase(c);
            if (isBlank)
                score = 0;

            pieces.add(new Piece(character, score, isBlank));
            i++;
        }
        return pieces.toArray(new Piece[0]);
    }

    protected int findPieceScore(String piece) {
        return pieces.stream()
                .filter(p -> p.letter().equals(piece))
                .findFirst().map(Piece::value).orElse(0);
    }

    protected Piece parseSpecialPiece(char c, int i, String word) {
        return null;
    }
}
