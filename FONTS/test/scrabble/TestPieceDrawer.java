package scrabble;

import edu.upc.prop.scrabble.data.Bag;
import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.domain.PieceDrawer;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPieceDrawer {
    @Test
    public void bagSizeUpdates() {
        Bag bag = new Bag();
        Piece piece = new Piece("a", 1);
        bag.addPiece(piece);
        PieceDrawer pieceDrawer = new PieceDrawer(bag);
        Piece drawn = pieceDrawer.run();

        assertTrue(bag.isEmpty());
        assertEquals(piece, drawn);
    }

}
