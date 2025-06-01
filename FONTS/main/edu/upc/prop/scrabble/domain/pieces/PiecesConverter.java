
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

    /**
     * Converteix una cadena de text en un array de peces, interpretant
     * les combinacions especials (sobreescrites en subclasses) i assignant
     * puntuacions segons el diccionari de peces.
     *
     * @param word La paraula a convertir en fitxes.
     * @return Array de peces que representen la paraula, amb puntuacions i tipus (comodí o no).
     */
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
    /**
     * Busca la puntuació corresponent a una fitxa donada dins del diccionari de peces.
     *
     * @param piece La lletra o combinació de lletres de la fitxa (per exemple, "A", "CH").
     * @return La puntuació associada a la fitxa, o 0 si no es troba.
     */
    protected int findPieceScore(String piece) {
        return pieces.stream()
                .filter(p -> p.letter().equals(piece))
                .findFirst().map(Piece::value).orElse(0);
    }
    /**
     * Mètode que permet detectar fitxes especials (com combinacions de dues lletres)
     * en una paraula. Es pot sobreescriure a les subclasses per gestionar casos especials.
     *
     * @param c Caràcter actual a la paraula.
     * @param i Índex del caràcter dins de la paraula.
     * @param word Paraula completa que s'està processant.
     * @return Una instància de {@link Piece} si es detecta una fitxa especial; {@code null} en cas contrari.
     */
    protected Piece parseSpecialPiece(char c, int i, String word) {
        return null;
    }
}
