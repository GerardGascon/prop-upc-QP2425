package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.CatalanPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.domain.pieces.SpanishPiecesConverter;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class TestPiecesInHandVerifier {
    @Test
    public void getPieceABInHandAB() {
        String word = "AB";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new PiecesConverter());
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

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new PiecesConverter());
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

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new CatalanPiecesConverter());
        ArrayList<Piece> pieces = sut.run(word);

         assertEquals(new Piece("NY", 0), pieces.getFirst());
    }

    @Test
    public void getPieceCHInHandABCH() {
        String word = "CH";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("CH", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new SpanishPiecesConverter());
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("CH", 0), pieces.getFirst());
    }

    @Test
    public void getPieceLgeminadaInHandABLgeminada() {
        String word = "L·L";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("L·L", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new CatalanPiecesConverter());
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("L·L", 0), pieces.getFirst());
    }

    @Test
    public void getPieceRRInHandABRR() {
        String word = "RR";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("RR", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new SpanishPiecesConverter());
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("RR", 0), pieces.getFirst());
    }

    @Test
    public void getPieceNYCHRRLgeminadaInHandABNYCHRRLgeminada() {
        String word = "CHRR";
        Player player = new Player("example", false);
        player.AddPiece(new Piece("A", 0));
        player.AddPiece(new Piece("B", 0));
        player.AddPiece(new Piece("CH", 0));
        player.AddPiece(new Piece("RR", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new SpanishPiecesConverter());
        ArrayList<Piece> pieces = sut.run(word);

        assertEquals(new Piece("CH", 0), pieces.get(0));
        assertEquals(new Piece("RR", 0), pieces.get(1));
    }
}
