package scrabble.actionmaker;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.game.IEndScreen;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;
import org.junit.Before;
import org.junit.Test;
import scrabble.stubs.EndScreenStub;
import scrabble.stubs.GamePlayerStub;
import scrabble.stubs.GameSaverMock;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestSkipActionMaker {
    private SkipActionMaker skipActionMaker;
    private GamePlayerStub player1Stub;
    private GamePlayerStub player2Stub;

    private final GameSaver gameSaver = new GameSaverMock();

    @Before
    public void setUp() {
        Player player1 = new Player("testPlayer1", false);
        Player player2 = new Player("testPlayer2", false);
        player1.addPiece(new Piece("A", 1, false));
        player2.addPiece(new Piece("A", 1, false));
        player1Stub = new GamePlayerStub();
        player2Stub = new GamePlayerStub();

        Endgame endgame = new Endgame(new Player[]{player1, player2});
        Turn turn = new Turn(endgame, new IGamePlayer[]{player1Stub, player2Stub});
        IEndScreen endScreen = new EndScreenStub();
        GameStepper stepper = new GameStepper(turn, new Leaderboard(), new Player[]{player1, player2}, endScreen, gameSaver);
        skipActionMaker = new SkipActionMaker(stepper);
    }

    @Test
    public void skipRunTriggersNextTurn(){
        skipActionMaker.run();

        assertFalse(player1Stub.isActive());
        assertTrue(player2Stub.isActive());
    }
}
