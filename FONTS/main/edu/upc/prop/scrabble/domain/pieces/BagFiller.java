package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Pair;

/***
 * Initializes and fills a bag with language-specific Scrabble pieces.
 * @author Gina Escofet González
 */
public class BagFiller {
    private final PieceGenerator pieceGenerator;

    /***
     * Constructs a BagFiller with the specified piece generator.
     * @param pieceGenerator The piece generator that creates language-specific pieces.
     * @throws IllegalArgumentException if generator is null
     */
    public BagFiller(PieceGenerator pieceGenerator){
        if (pieceGenerator == null) {
            throw new IllegalArgumentException("Generator cannot be null");
        }
        this.pieceGenerator = pieceGenerator;
    }

    /***
     * Fills a new bag with pieces according to the specified language configuration.
     * @param piecesTxt The configuration string defining piece distribution
     * @return A fully filled bag
     * @throws IllegalArgumentException if languageConfig is null
     * @throws IllegalStateException if piece generation fails
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
