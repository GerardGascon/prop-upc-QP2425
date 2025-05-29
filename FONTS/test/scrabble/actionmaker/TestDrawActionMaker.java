package scrabble.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.domain.exceptions.NotEnoughPiecesInBagException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.game.IEndScreen;
import edu.upc.prop.scrabble.domain.pieces.EnglishPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;
import edu.upc.prop.scrabble.utils.IRand;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import scrabble.stubs.EndScreenStub;
import scrabble.stubs.GamePlayerStub;
import scrabble.stubs.RandStub;

import java.util.Arrays;

import static org.junit.Assert.*;

public class TestDrawActionMaker {
    Player player;
    Bag bag;
    DrawActionMaker sut;

    @Mock
    private GameSaver gameSaver;

    @Before
    public void setUp() {
        bag = new Bag();
        player = new Player("testPlayer", false);
        IRand rand = new RandStub(0);
        IHandView display = null;
        Turn turn = new Turn(new Endgame(new Player[]{player}), new IGamePlayer[]{new GamePlayerStub()});
        IEndScreen endScreen = new EndScreenStub();
        GameStepper stepper = new GameStepper(turn, new Leaderboard(), new Player[]{player}, endScreen, gameSaver);
        PiecesReader piecesReader = new PiecesReader();
        PiecesConverter piecesConverter = new EnglishPiecesConverter();
        sut = new DrawActionMaker(bag, player, rand, display, stepper, piecesConverter);
    }

    @Test
    public void successSwap() {
        Piece p1 = new Piece("A", 0);
        for (int i = 0; i < 3; ++i) {
            bag.add(p1);
        }
        Piece p2 = new Piece("B", 0);
        for (int i = 0; i < 3; ++i) {
            player.addPiece(p2);
        }
        String[] word = {"B"};
        sut.run(word);

        assertTrue(Arrays.asList(player.getHand()).contains(p1));
        assertEquals(3, bag.getSize());
    }

    @Test
    public void notEnoughBagPieces() {
        bag.add(new Piece("A", 0));
        Piece p2 = new Piece("B", 0);
        for (int i = 0; i < 2; ++i) {
            player.addPiece(p2);
        }
        String[] word = {"B", "B"};

        assertThrows(NotEnoughPiecesInBagException.class, () -> sut.run(word));
    }

    @Test
    public void multipleBagPieces() {
        Piece p1 = new Piece("A", 0);
        for (int i = 0; i < 4; ++i) {
            bag.add(p1);
        }
        Piece p2 = new Piece("B", 0);
        for (int i = 0; i < 2; ++i) {
            player.addPiece(p2);
        }
        String[] word = {"B", "B"};

        sut.run(word);

        assertEquals(2, player.getHand().length);
        assertTrue(Arrays.asList(player.getHand()).contains(p1));
        assertFalse(Arrays.asList(player.getHand()).contains(p2));
        assertEquals(4, bag.getSize());
    }
}
