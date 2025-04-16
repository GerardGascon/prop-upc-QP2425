package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.HandFiller;
import org.junit.Test;
import scrabble.stubs.RandStub;

import static org.junit.Assert.assertEquals;

public class TestHandFiller {
    @Test(expected = AssertionError.class)
    public void tryFillWithEmptyBagThrowsException(){
        Bag bag = new Bag();
        Player player = new Player("test", false);
        HandFiller sut = new HandFiller(bag, new Player[] {player}, new RandStub(0));

        sut.run();
    }

    @Test(expected = AssertionError.class)
    public void tryFillWithNotEnoughPiecesInBagThrowsException(){
        Bag bag = new Bag();
        for (int i = 0; i < 6; i++)
            bag.add(new Piece("A", 1));

        Player player = new Player("test", false);
        HandFiller sut = new HandFiller(bag, new Player[] {player}, new RandStub(0));

        sut.run();
    }

    @Test
    public void fillHandFillsPlayerCorrectly(){
        Bag bag = new Bag();
        for (int i = 0; i < 7; i++)
            bag.add(new Piece("A", 1));

        Player player = new Player("test", false);
        HandFiller sut = new HandFiller(bag, new Player[] {player}, new RandStub(0));

        sut.run();

        assertEquals(7, player.getHand().length);
        assertEquals("A", player.getHand()[0].letter());
    }

    @Test
    public void fillHandFillsAllPlayersCorrectly(){
        Bag bag = new Bag();
        for (int i = 0; i < 7; i++)
            bag.add(new Piece("A", 1));
        for (int i = 0; i < 7; i++)
            bag.add(new Piece("B", 1));

        Player player1 = new Player("test", false);
        Player player2 = new Player("test", false);
        HandFiller sut = new HandFiller(bag, new Player[] {player1, player2}, new RandStub(0));

        sut.run();

        assertEquals(7, player1.getHand().length);
        assertEquals("A", player1.getHand()[0].letter());

        assertEquals(7, player2.getHand().length);
        assertEquals("B", player2.getHand()[0].letter());
    }
}
