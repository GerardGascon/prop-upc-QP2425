package scrabble.turns;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestEndgame {
    @Test
    public void TwoPlayersNoEndgame() {
        Player[] players = new Player[2];
        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));
        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));
        Endgame endgame = new Endgame(players);

        int skipCounter = 4;

        assertEquals(false,endgame.run(skipCounter));
    }

    @Test
    public void TwoPlayersEndgameDueToSkipLimitReached() {
        Player[] players = new Player[2];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 6;

        assertEquals(true,endgame.run(skipCounter));
    }

    @Test
    public void TwoPlayersNoEndgameDueToSkipLimitReached() {
        Player[] players = new Player[2];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 5;

        assertEquals(false,endgame.run(skipCounter));
    }

    @Test
    public void TwoPlayersEndgameDueToSomeoneWithEmptyHand() {
        Player[] players = new Player[2];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);

        Endgame endgame = new Endgame(players);

        int skipCounter = 4;

        assertEquals(true,endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersNoEndgame() {
        Player[] players = new Player[3];
        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));
        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));
        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));
        Endgame endgame = new Endgame(players);

        int skipCounter = 4;

        assertEquals(false,endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersEndgameDueToSkipLimitReached() {
        Player[] players = new Player[3];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 9;

        assertEquals(true,endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersNoEndgameDueToSkipLimitReached() {
        Player[] players = new Player[3];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 8;

        assertEquals(false,endgame.run(skipCounter));
    }

    @Test
    public void ThreePlayersEndgameDueToSomeoneWithEmptyHand() {
        Player[] players = new Player[3];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);

        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 2;

        assertEquals(true,endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersNoEndgame() {
        Player[] players = new Player[4];
        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));
        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));
        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));
        players[3] = new Player("Marcel", false);
        players[3].AddPiece(new Piece("a",2));
        Endgame endgame = new Endgame(players);

        int skipCounter = 4;

        assertEquals(false,endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersEndgameDueToSkipLimitReached() {
        Player[] players = new Player[4];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));

        players[3] = new Player("Marcel", false);
        players[3].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 12;

        assertEquals(true,endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersNoEndgameDueToSkipLimitReached() {
        Player[] players = new Player[4];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);
        players[1].AddPiece(new Piece("a",2));

        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));

        players[3] = new Player("Marcel", false);
        players[3].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 11;

        assertEquals(false,endgame.run(skipCounter));
    }

    @Test
    public void FourPlayersEndgameDueToSomeoneWithEmptyHand() {
        Player[] players = new Player[4];

        players[0] = new Player("Adri", false);
        players[0].AddPiece(new Piece("a",2));

        players[1] = new Player("Juan", false);

        players[2] = new Player("Kurt", false);
        players[2].AddPiece(new Piece("a",2));

        players[3] = new Player("Marcel", false);
        players[3].AddPiece(new Piece("a",2));

        Endgame endgame = new Endgame(players);

        int skipCounter = 2;

        assertEquals(true,endgame.run(skipCounter));
    }
}
