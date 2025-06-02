package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.persistence.runtime.data.PersistentDictionary;
import org.junit.Test;

import static org.junit.Assert.*;

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

    @Test
    public void bagThrowsErrorIfTryGettingWhenEmpty() {
        Bag sut = new Bag();

        assertThrows(IllegalStateException.class, () -> sut.draw(0));
    }

    @Test
    public void bagThrowsErrorIfTryAddingNullPiece() {
        Bag sut = new Bag();

        assertThrows(IllegalArgumentException.class, () -> sut.add(null));
    }

    @Test
    public void testEncodeAndDecode() {
        // Arrange: Create the original bag
        Bag originalBag = new Bag();
        Piece piece = new Piece("A", 0);
        for (int i = 0; i < 2; ++i) {
            originalBag.add(piece);
        }

        PersistentDictionary encodedData = originalBag.encode();

        Bag decodedBag = new Bag();
        decodedBag.decode(encodedData);

        // sut decode
        assertEquals(originalBag.getSize(), decodedBag.getSize());
        for (int i = 0; i < originalBag.getSize(); i++) {
            Piece originalPiece = originalBag.draw(i);
            Piece decodedPiece = decodedBag.draw(i);
            assertEquals(originalPiece.toString(), decodedPiece.toString());
        }
    }
}
