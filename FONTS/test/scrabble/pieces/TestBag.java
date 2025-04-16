package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestBag {
    @Test
    public void bagDefaultsToEmpty() {
        Bag sut = new Bag();

        assertTrue(sut.isEmpty());
    }
    @Test
    public void bagAddsOnePiece() {
        Bag sut = new Bag();
        Piece piece = new Piece("a", 1);
        sut.add(piece);

        assertEquals(1, sut.getSize());
    }
    @Test
    public void bagGetsOnePiece() {
        Bag sut = new Bag();
        Piece piece = new Piece("a", 1);
        sut.add(piece);

        assertEquals(piece, sut.draw(0));
    }

}
