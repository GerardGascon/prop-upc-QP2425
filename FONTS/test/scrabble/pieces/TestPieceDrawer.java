package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.utils.IRand;
import org.junit.Test;
import scrabble.stubs.RandStub;

import static org.junit.Assert.*;

public class TestPieceDrawer {
    @Test
    public void swap1piece() {
        Bag bag = new Bag();
        bag.add(new Piece("a", 1));

        Player player = new Player("nom", false);
        player.addPiece(new Piece("b", 1));

        IRand rand = new RandStub(0);
        PieceDrawer sut = new PieceDrawer(bag, player, rand);
        sut.run(new Piece[] { new Piece("b", 1) });

        assertEquals("a", player.getHand()[0].letter());
        assertEquals("b", bag.draw(0).letter());
    }

    @Test
    public void swap2pieces() {
        Bag bag = new Bag();
        bag.add(new Piece("a", 1));
        bag.add(new Piece("d", 1));

        Player player = new Player("nom", false);
        player.addPiece(new Piece("b", 1));
        player.addPiece(new Piece("c", 1));

        IRand rand = new RandStub(0);
        PieceDrawer sut = new PieceDrawer(bag, player, rand);
        sut.run(new Piece[] { new Piece("b", 1), new Piece("c", 1) });

        assertEquals("a", player.getHand()[0].letter());
        assertEquals("b", bag.draw(0).letter());

        assertEquals("d", player.getHand()[1].letter());
        assertEquals("c", bag.draw(0).letter());
    }

    @Test(expected = NotEnoughPiecesInBagException.class)
    public void throwExceptionIfNotEnoughPiecesInBag() {
        Bag bag = new Bag();
        bag.add(new Piece("a", 1));

        Player player = new Player("nom", false);
        player.addPiece(new Piece("b", 1));
        player.addPiece(new Piece("c", 1));

        IRand rand = new RandStub(0);
        PieceDrawer sut = new PieceDrawer(bag, player, rand);

        sut.run(new Piece[] { new Piece("b", 1), new Piece("c", 1) });
    }
}