package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPieceDrawer {
    @Test
    public void bagSizeUpdates() {
        Bag bag = new Bag();
        Piece piece = new Piece("a", 1);
        bag.addPiece(piece);
        PieceDrawer pieceDrawer = new PieceDrawer(bag);
        Piece[] drawns = pieceDrawer.run(1);

        assertTrue(bag.isEmpty());
        assertEquals(piece, drawns[0]);
    }
    @Test
    public void bagDrawnsNPieces() {
        Bag bag = new Bag();
        Piece piece = new Piece("a", 1);
        bag.addPiece(piece);
        piece = new Piece("b", 1);
        bag.addPiece(piece);
        piece = new Piece("c", 1);
        bag.addPiece(piece);
        PieceDrawer pieceDrawer = new PieceDrawer(bag);
        Piece[] drawns = pieceDrawer.run(2);

        assertFalse(bag.isEmpty());
        assertEquals(2, drawns.length);
    }
    @Test
    public void bagDrawnsNothing() {
        Bag bag = new Bag();
        PieceDrawer pieceDrawer = new PieceDrawer(bag);
        Piece[] drawns = pieceDrawer.run(2);

        assertTrue(bag.isEmpty());
        assertEquals(new Piece[0], drawns);
    }

    @Test
    public void bagDrawns2Pieces() {
        Bag bag = new Bag();
        Piece piece = new Piece("a", 1);
        bag.addPiece(piece);
        piece = new Piece("b", 1);
        bag.addPiece(piece);
        PieceDrawer pieceDrawer = new PieceDrawer(bag);
        Piece[] drawns = pieceDrawer.run(5);

        assertTrue(bag.isEmpty());
        assertEquals(2, drawns.length);
    }
}
