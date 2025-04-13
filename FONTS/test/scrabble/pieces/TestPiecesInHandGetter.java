package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import org.junit.Test;
import java.util.*;

import static org.junit.Assert.assertEquals;

public class TestPiecesInHandGetter {
    @Test
    public void get1Piece() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.addPiece(p1);

        Player player = new Player("nom", false);
        for (int i = 0; i < 7; i++) {
            player.AddPiece(new Piece("b", 1));
        }

        Piece[] word = new Piece[]{new Piece("b", 1)};
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player);
        Piece[] newPieces = sut.run(word);

        assertEquals(p1, newPieces[0]);
    }
    @Test
    public void get2Pieces() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.addPiece(p1);
        bag.addPiece(p1);

        Player player = new Player("nom", false);
        for (int i = 0; i < 7; i++) {
            player.AddPiece(new Piece("b", 1));
        }

        Piece[] word = new Piece[]{new Piece("b", 1), new Piece("b", 1)};
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player);
        Piece[] newPieces = sut.run(word);

        assertEquals(p1, newPieces[0]);
        assertEquals(p1, newPieces[1]);
    }
}
