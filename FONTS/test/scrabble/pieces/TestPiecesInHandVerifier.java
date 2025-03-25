package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.PiecesInHandVerifier;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

import static org.junit.Assert.assertEquals;

public class TestPiecesInHandVerifier {
    @Test
    public void getPieceAInHand() {
        String word = "AB";
        Player player = new Player("asdfj", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("A", 0), pieces.get(0));
        assertEquals(new Piece("B", 0), pieces.get(1));
    }
}
