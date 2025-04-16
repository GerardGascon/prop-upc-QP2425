package scrabble.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.presenter.terminal.actionmaker.HandView;
import edu.upc.prop.scrabble.utils.IRand;
import edu.upc.prop.scrabble.utils.Rand;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestDrawActionMaker {
    @Test
    public void successSwap() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 0);
        for (int i = 0; i < 3; ++i) {
            bag.add(p1);
        }
        Player player = new Player("testPlayer", false);
        Piece p2 = new Piece("b", 0);
        for (int i = 0; i < 3; ++i) {
            player.addPiece(p2);
        }

        Piece[] piecesToSwap = {player.getHand()[0]};
        IRand rand = new Rand();
        HandView display = new HandView();
        DrawActionMaker sut = new DrawActionMaker(bag, player, rand, display);
        sut.run(piecesToSwap);

        assertTrue(Arrays.asList(player.getHand()).contains(p1));
        assertEquals(3, bag.getSize());
    }

    @Test
    public void piecesToSwapNull() {
        Bag bag = new Bag();
        Player player = new Player("testPlayer", false);
        Piece[] piecesToSwap = new Piece[0];
        IRand rand = new Rand();
        HandView display = new HandView();
        DrawActionMaker sut = new DrawActionMaker(bag, player, rand, display);
        sut.run(piecesToSwap);
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> sut.run(null));
    }


    @Test
    public void notEnoughBagPieces() {
        Bag bag = new Bag();
        bag.add(new Piece("a", 0));
        Player player = new Player("TestPlayer", false);
        Piece p2 = new Piece("b", 0);
        for (int i = 0; i < 2; ++i) {
            player.addPiece(p2);
        }
        Piece[] piecesToSwap = player.getHand();
        IRand rand = new Rand();
        HandView handDisplay = new HandView();
        DrawActionMaker sut = new DrawActionMaker(bag, player, rand, handDisplay);

        NotEnoughPiecesInBagException exception = assertThrows(NotEnoughPiecesInBagException.class, () -> sut.run(piecesToSwap));
    }

    @Test
    public void multipleBagPieces() {
        Bag bag = new Bag();
        Piece p1 = new Piece("a", 0);
        for (int i = 0; i < 4; ++i) {
            bag.add(p1);
        }
        Player player = new Player("TestPlayer", false);
        Piece p2 = new Piece("b", 0);
        for (int i = 0; i < 2; ++i) {
            player.addPiece(p2);
        }
        Piece[] piecesToSwap = player.getHand();
        IRand rand = new Rand();
        HandView handDisplay = new HandView();
        DrawActionMaker drawActionMaker = new DrawActionMaker(bag, player, rand, handDisplay);

        drawActionMaker.run(piecesToSwap);

        assertEquals(2, player.getHand().length);
        assertTrue(Arrays.asList(player.getHand()).contains(p1));
        assertFalse(Arrays.asList(player.getHand()).contains(p2));
        assertEquals(4, bag.getSize());
    }
}
