package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Pair;

/***
 * Inicialitza i omple una bossa amb peces de Scrabble específiques de cada idioma.
 * @author Gina Escofet González
 */
public class BagFiller {
    /**
     * Generador de fitxes
     */
    private final PieceGenerator pieceGenerator;

    /***
     * Construeix un BagFiller amb el generador de peces especificat.
     * @param pieceGenerator El generador de peces que crea peces específiques de cada idioma.
     * @throws IllegalArgumentException si el generador és nul.
     */
    public BagFiller(PieceGenerator pieceGenerator){
        if (pieceGenerator == null) {
            throw new IllegalArgumentException("Generator cannot be null");
        }
        this.pieceGenerator = pieceGenerator;
    }

    /***
     * Omple una nova bossa amb peces segons la configuració de l'idioma especificat.
     * @param piecesTxt La cadena de configuració que defineix la distribució de les peces.
     * @return Una bossa completament plena.
     * @throws IllegalArgumentException si languageConfig és nul.
     * @throws IllegalStateException si la generació de peces falla.
     */
    public Bag run(String piecesTxt) {
        if (piecesTxt == null) {
            throw new IllegalArgumentException("Pieces cannot be null");
        }
        Bag bag = new Bag();
        Pair<Piece, Integer>[] pieces;
        try {
            pieces = pieceGenerator.run(piecesTxt);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to generate game pieces", e);
        }
        for (Pair<Piece, Integer> piece : pieces) {
            int quantity = piece.second();
            for (int j = 0; j < quantity; j++) {
                bag.add(piece.first());
            }
        }
        return bag;
    }

}
