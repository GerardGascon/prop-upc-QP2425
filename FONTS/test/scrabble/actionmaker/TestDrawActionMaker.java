package scrabble.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.presenter.terminal.actionmaker.HandView;
import edu.upc.prop.scrabble.utils.IRand;
import edu.upc.prop.scrabble.utils.Rand;
import org.junit.Before;
import org.junit.Test;
import scrabble.stubs.GamePlayerStub;
import scrabble.stubs.RandStub;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestDrawActionMaker {
    Player player;
    Bag bag;
    DrawActionMaker sut;

    @Before
    public void setUp() {
        bag = new Bag();
        player = new Player("testPlayer", false);
        IRand rand = new RandStub(0);
        HandView display = new HandView();
        Turn turn = new Turn(new Endgame(new Player[]{player}), new IGamePlayer[]{new GamePlayerStub()});
        GameStepper stepper = new GameStepper(turn, new Leaderboard(), new Player[]{player});
        sut = new DrawActionMaker(bag, player, rand, display, stepper);
    }

    @Test
    public void successSwap() {
        Piece p1 = new Piece("a", 0);
        for (int i = 0; i < 3; ++i) {
            bag.add(p1);
        }
        Piece p2 = new Piece("b", 0);
        for (int i = 0; i < 3; ++i) {
            player.addPiece(p2);
        }
        Piece[] piecesToSwap = {player.getHand()[0]};
        sut.run(piecesToSwap);

        assertTrue(Arrays.asList(player.getHand()).contains(p1));
        assertEquals(3, bag.getSize());
    }

    @Test
    public void piecesToSwapNull() {
        Piece[] piecesToSwap = new Piece[0];
        sut.run(piecesToSwap);

        assertThrows(IllegalArgumentException.class, () -> sut.run(null));
    }


    @Test
    public void notEnoughBagPieces() {
        bag.add(new Piece("a", 0));
        Piece p2 = new Piece("b", 0);
        for (int i = 0; i < 2; ++i) {
            player.addPiece(p2);
        }
        Piece[] piecesToSwap = player.getHand();

        assertThrows(NotEnoughPiecesInBagException.class, () -> sut.run(piecesToSwap));
    }

    @Test
    public void multipleBagPieces() {
        Piece p1 = new Piece("a", 0);
        for (int i = 0; i < 4; ++i) {
            bag.add(p1);
        }
        Piece p2 = new Piece("b", 0);
        for (int i = 0; i < 2; ++i) {
            player.addPiece(p2);
        }
        Piece[] piecesToSwap = player.getHand();

        sut.run(piecesToSwap);

        assertEquals(2, player.getHand().length);
        assertTrue(Arrays.asList(player.getHand()).contains(p1));
        assertFalse(Arrays.asList(player.getHand()).contains(p2));
        assertEquals(4, bag.getSize());
    }
}
