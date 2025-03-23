package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Pair;

public class BagFiller {
    private final PieceGenerator pg;

    public BagFiller(PieceGenerator generator){
        pg = generator;
    }

    public Bag run(String piecesTxt) {
        Bag bag = new Bag();
        Pair<Piece, Integer>[] pieces = pg.run(piecesTxt);
        for (Pair<Piece, Integer> piece : pieces) {
            int quantity = piece.second();
            for (int j = 0; j < quantity; j++) {
                bag.addPiece(piece.first());
            }
        }
        return bag;
    }

}
