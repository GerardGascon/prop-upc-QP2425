package scrabble.turns;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestEndgame {

    @Test
    public void TwoPlayersEndgameDueToSkipLimitReached() {
        Player[] players = new Player[2];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 6;

        assertTrue(endgame.run(skipCounter));
    }

    @Test
    public void TwoPlayersNoEndgameDueToSkipLimitReached() {
        Player[] players = new Player[2];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 5;

        assertFalse(endgame.run(skipCounter));
    }

    @Test
    public void TwoPlayersEndgameDueToSomeoneWithEmptyHand() {
        Player[] players = new Player[2];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);

        Endgame endgame = new Endgame(players);

        int skipCounter = 4;

        assertTrue(endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersNoEndgame() {
        Player[] players = new Player[3];
        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));
        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));
        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));
        Endgame endgame = new Endgame(players);

        int skipCounter = 4;

        assertFalse(endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersEndgameDueToSkipLimitReached() {
        Player[] players = new Player[3];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 9;

        assertTrue(endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersNoEndgameDueToSkipLimitReached() {
        Player[] players = new Player[3];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 8;

        assertFalse(endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersEndgameDueToSomeoneWithEmptyHand() {
        Player[] players = new Player[3];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);

        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 2;

        assertTrue(endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersNoEndgame() {
        Player[] players = new Player[4];
        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));
        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));
        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));
        players[3] = new Player("Marcel", false);
        players[3].addPiece(new Piece("a",2));
        Endgame endgame = new Endgame(players);

        int skipCounter = 4;

        assertFalse(endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersEndgameDueToSkipLimitReached() {
        Player[] players = new Player[4];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));

        players[3] = new Player("Marcel", false);
        players[3].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 12;

        assertTrue(endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersNoEndgameDueToSkipLimitReached() {
        Player[] players = new Player[4];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].addPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));

        players[3] = new Player("Marcel", false);
        players[3].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 11;

        assertFalse(endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersEndgameDueToSomeoneWithEmptyHand() {
        Player[] players = new Player[4];

        players[0] = new Player("Adri", false);
        players[0].addPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);

        players[2] = new Player("Kurt", false);
        players[2].addPiece(new Piece("a",2));

        players[3] = new Player("Marcel", false);
        players[3].addPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 2;

        assertTrue(endgame.run(skipCounter));
    }
}
