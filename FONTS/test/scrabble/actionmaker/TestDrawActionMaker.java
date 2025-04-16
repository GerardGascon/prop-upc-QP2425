package scrabble.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.IHandDisplay;
import edu.upc.prop.scrabble.presenter.terminal.actionmaker.HandView;
import edu.upc.prop.scrabble.utils.IRand;
import edu.upc.prop.scrabble.utils.Rand;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
}
