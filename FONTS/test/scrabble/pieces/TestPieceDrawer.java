package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

public class TestPieceDrawer {
    @Test
    public void emptyBag() {
        Bag bag = new Bag();
        PieceDrawer sut = new PieceDrawer(bag, null);
        Piece[] pieces = sut.run(new Piece[0]);
        assertEquals(0, pieces.length);
    }

    @Test
    public void swap1piece() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.addPiece(p1);

        Player player = new Player("nom", false);
        Piece p2 = new Piece("b", 1);
        player.AddPiece(p2);

        PieceDrawer sut = new PieceDrawer(bag, player);
        Piece[] pieces = sut.run(new Piece[] { p2 });

        assertEquals(1, pieces.length);
        assertEquals(p1, pieces[0]);
        Vector<Piece> hand = player.getHand();
        assertEquals(p1, hand.get(0));
        assertEquals(p2, bag.getPiece(0));
    }

    @Test
    public void swap2pieces() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.addPiece(p1);
        Piece p2 = new Piece("b", 1);
        bag.addPiece(p2);

        Player player = new Player("nom", false);
        Piece p3 = new Piece("c", 1);
        player.AddPiece(p3);
        Piece p4 = new Piece("d", 1);
        player.AddPiece(p4);

        PieceDrawer sut = new PieceDrawer(bag, player);
        Piece[] pieces = sut.run(new Piece[] { p3, p4 });

        assertEquals(2, pieces.length);
        Vector<Piece> hand = player.getHand();
        assertTrue(hand.contains(p1));
        assertTrue(hand.contains(p2));

        assertEquals(2, bag.getSize());
        boolean foundP3 = false;
        boolean foundP4 = false;
        assertEquals(2, bag.getSize());
        int sz = bag.getSize();
        for (int i = 0; i < sz; i++) {
            Piece p = bag.getPiece(0);
            if (p.equals(p3)) foundP3 = true;
            else if (p.equals(p4)) foundP4 = true;
        }
        assertTrue(foundP3);
        assertTrue(foundP4);
    }

    @Test
    public void maxHandSize() {
        Bag bag = new Bag();
        for (int i = 0; i < 5; ++i) {
            Piece p = new Piece("a", 1);
            bag.addPiece(p);
        }
        Player player = new Player("nom", false);
        Vector<Piece> hand = player.getHand();
        Piece[] p = new Piece[5];
        for (int i = 0; i < 5; ++i) {
            p[i] = new Piece("b", 1);
            hand.addElement(p[i]);
        }

        PieceDrawer sut = new PieceDrawer(bag, player);
        Piece[] pieces = sut.run(p);

        assertEquals(5, pieces.length);
    }
}