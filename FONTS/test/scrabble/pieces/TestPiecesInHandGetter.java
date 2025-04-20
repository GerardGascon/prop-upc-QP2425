package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.IPiecePrinter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import org.junit.Test;
import scrabble.stubs.RandStub;

import static org.junit.Assert.*;

public class TestPiecesInHandGetter {
    @Test
    public void get1Piece() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.add(p1);

        Player player = new Player("nom", false);
        for (int i = 0; i < 7; i++) {
            player.addPiece(new Piece("b", 10));
        }
        Piece[] word = new Piece[]{new Piece("b", 1)};
        RandStub rand = new RandStub(0);
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player, rand);

        Piece[] newPieces = sut.run(word);

        assertEquals("b", newPieces[0].letter());
        assertEquals(10, newPieces[0].value());
    }

    @Test
    public void get1BlankPiece() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.add(p1);

        Player player = new Player("nom", false);
        for (int i = 0; i < 6; i++) {
            player.addPiece(new Piece("b", 10));
        }
        player.addPiece(new Piece("b", 0, true));
        Piece[] word = new Piece[]{new Piece("b", 1, true)};
        RandStub rand = new RandStub(0);
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player, rand);

        Piece[] newPieces = sut.run(word);

        assertEquals("b", newPieces[0].letter());
        assertEquals(0, newPieces[0].value());
        assertTrue(newPieces[0].isBlank());
    }

    @Test(expected = PlayerDoesNotHavePieceException.class)
    public void get1BlankPieceThrowsExceptionIfPlayerDoesNotHavePiece() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.add(p1);

        Player player = new Player("nom", false);
        for (int i = 0; i < 7; i++) {
            player.addPiece(new Piece("b", 10));
        }
        Piece[] word = new Piece[]{new Piece("b", 1, true)};
        RandStub rand = new RandStub(0);
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player, rand);

        sut.run(word);
    }

    @Test
    public void rollbackWhenOnePieceNotFound() {
        Bag bag = new Bag();
        Player player = new Player("nom", false);
        player.addPiece(new Piece("a", 10));
        player.addPiece(new Piece("b", 10));
        Piece[] word = new Piece[]{new Piece("a", 10), new Piece("b", 10, true)};
        RandStub rand = new RandStub(0);
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player, rand);

        assertThrows(PlayerDoesNotHavePieceException.class, () -> sut.run(word));
        assertEquals(new Piece("a", 10), player.getHand()[0]);
    }

    @Test
    public void get1PieceRemovesOnePieceFromBag() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.add(p1);

        Player player = new Player("nom", false);
        for (int i = 0; i < 7; i++) {
            player.addPiece(new Piece("b", 1));
        }
        Piece[] word = new Piece[]{new Piece("b", 1)};
        RandStub rand = new RandStub(0);
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player, rand);

        sut.run(word);

        assertTrue(bag.isEmpty());
        assertEquals("a", player.getHand()[6].letter());
    }

    @Test
    public void get2Pieces() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 1);
        bag.add(p1);
        bag.add(p1);

        Player player = new Player("nom", false);
        for (int i = 0; i < 7; i++) {
            player.addPiece(new Piece("b", 1));
        }

        Piece[] word = new Piece[]{new Piece("b", 1), new Piece("b", 1)};
        RandStub rand = new RandStub(0);
        PiecesInHandGetter sut = new PiecesInHandGetter(bag, player, rand);
        Piece[] newPieces = sut.run(word);

        assertEquals("b", newPieces[0].letter());
        assertEquals("b", newPieces[1].letter());
    }
}