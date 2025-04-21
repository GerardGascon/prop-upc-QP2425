package scrabble.pieces;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.*;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPiecesInHandVerifier {
    @Test
    public void getPieceABInHandAB() {
        String word = "AB";
        Player player = new Player("example", false);
        player.addPiece(new Piece("A", 0));
        player.addPiece(new Piece("B", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new EnglishPiecesConverter());
        Piece [] pieces = sut.run(word);

        assertEquals(new Piece("A", 0), pieces[0]);
        assertEquals(new Piece("B", 0), pieces[1]);
    }

    @Test
    public void getPieceABInHandABCD() {
        String word = "AB";
        Player player = new Player("example", false);
        player.addPiece(new Piece("A", 0));
        player.addPiece(new Piece("B", 0));
        player.addPiece(new Piece("C", 0));
        player.addPiece(new Piece("D", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new EnglishPiecesConverter());
        Piece[] pieces = sut.run(word);

        assertEquals(new Piece("A", 0), pieces[0]);
        assertEquals(new Piece("B", 0), pieces[1]);
    }

    @Test
    public void getPieceNYInHandABNY() {
        String word = "NY";
        Player player = new Player("example", false);
        player.addPiece(new Piece("A", 0));
        player.addPiece(new Piece("B", 0));
        player.addPiece(new Piece("NY", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new CatalanPiecesConverter());
        Piece[] pieces = sut.run(word);

         assertEquals(new Piece("NY", 0), pieces[0]);
    }

    @Test
    public void getPieceCHInHandABCH() {
        String word = "CH";
        Player player = new Player("example", false);
        player.addPiece(new Piece("A", 0));
        player.addPiece(new Piece("B", 0));
        player.addPiece(new Piece("CH", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new SpanishPiecesConverter());
        Piece[] pieces = sut.run(word);

        assertEquals(new Piece("CH", 0), pieces[0]);
    }

    @Test
    public void getPieceLgeminadaInHandABLgeminada() {
        String word = "L·L";
        Player player = new Player("example", false);
        player.addPiece(new Piece("A", 0));
        player.addPiece(new Piece("B", 0));
        player.addPiece(new Piece("L·L", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new CatalanPiecesConverter());
        Piece[] pieces = sut.run(word);

        assertEquals(new Piece("L·L", 0), pieces[0]);
    }

    @Test
    public void getPieceRRInHandABRR() {
        String word = "RR";
        Player player = new Player("example", false);
        player.addPiece(new Piece("A", 0));
        player.addPiece(new Piece("B", 0));
        player.addPiece(new Piece("RR", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new SpanishPiecesConverter());
        Piece[] pieces = sut.run(word);

        assertEquals(new Piece("RR", 0), pieces[0]);
    }

    @Test
    public void getPieceNYCHRRInHandABNYCHRR() {
        String word = "CHRR";
        Player player = new Player("example", false);
        player.addPiece(new Piece("A", 0));
        player.addPiece(new Piece("B", 0));
        player.addPiece(new Piece("CH", 0));
        player.addPiece(new Piece("RR", 0));

        PiecesInHandVerifier sut = new PiecesInHandVerifier(player, new SpanishPiecesConverter());
        Piece[] pieces = sut.run(word);

        assertEquals(new Piece("CH", 0), pieces[0]);
        assertEquals(new Piece("RR", 0), pieces[1]);
    }
}
