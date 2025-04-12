package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBag {
    @Test
    public void bagDefaultsToEmpty() {
        Bag bag = new Bag();

        assertTrue(bag.is_Empty());
    }
    @Test
    public void bagAddsOnePiece() {
        Bag bag = new Bag();
        Piece piece = new Piece("a", 1);
        bag.addPiece(piece);

        assertEquals(1, bag.getSize());
    }
    @Test
    public void bagGetsOnePiece() {
        Bag bag = new Bag();
        Piece piece = new Piece("a", 1);
        bag.addPiece(piece);

        assertEquals(piece, bag.getPiece(0));
    }

}
