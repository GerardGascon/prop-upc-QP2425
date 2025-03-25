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
    public void getPieceABInHandAB() {
        String word = "AB";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("A", 0), pieces.get(0));
        assertEquals(new Piece("B", 0), pieces.get(1));
    }

    @Test
    public void getPieceABInHandABCD() {
        String word = "AB";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("C", 0));
        player.AddPiece(new Piece("D", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("A", 0), pieces.get(0));
        assertEquals(new Piece("B", 0), pieces.get(1));
    }

    @Test
    public void getPieceNYInHandABNY() {
        String word = "NY";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("NY", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

         assertEquals(new Piece("NY", 0), pieces.get(0));
    }

    @Test
    public void getPieceCHInHandABCH() {
        String word = "CH";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("CH", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("CH", 0), pieces.get(0));
    }

    @Test
    public void getPieceLgeminadaInHandABLgeminada() {
        String word = "L·L";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("L·L", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("L·L", 0), pieces.get(0));
    }

    @Test
    public void getPieceRRInHandABRR() {
        String word = "RR";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("RR", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("RR", 0), pieces.get(0));
    }

    @Test
    public void getPieceNYCHRRLgeminadaInHandABNYCHRRLgeminada() {
        String word = "NYCHRRL·L";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("NY", 0));
        player.AddPiece(new Piece("CH", 0));
        player.AddPiece(new Piece("RR", 0));
        player.AddPiece(new Piece("L·L", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player);
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("NY", 0), pieces.get(0));
        assertEquals(new Piece("CH", 0), pieces.get(1));
        assertEquals(new Piece("RR", 0), pieces.get(2));
        assertEquals(new Piece("L·L", 0), pieces.get(3));
    }
}
