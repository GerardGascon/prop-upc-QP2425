package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPiecesConverter {
    @Test
    public void getLPieceFromLCharacter() {
        String letter = "L";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("L", 0), piece[0]);
    }

    @Test
    public void getRPieceFromRCharacter() {
        String letter = "R";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("R", 0), piece[0]);
    }

    @Test
    public void getPiecesFromWord() {
        String letter = "LA";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("L", 0), piece[0]);
        assertEquals(new Piece("A", 0), piece[1]);
    }

    @Test
    public void getLgeminadaPiecesFromLgeminadaCharacterCAT() {
        String letter = "L·L";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("L·L", 0), piece[0]);
    }

    @Test
    public void getNYPiecesFromNYCharacterCAT() {
        String letter = "NY";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("NY", 0), piece[0]);
    }

    @Test
    public void getRRPiecesFromRRCharacterCAST() {
        String letter = "RR";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("RR", 0), piece[0]);
    }

    @Test
    public void getLLPiecesFromLLCharacterCAST() {
        String letter = "LL";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("LL", 0), piece[0]);
    }

    @Test
    public void getCHPiecesFromCHCharacterCAST() {
        String letter = "CH";
        PiecesConverter sut = new PiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("CH", 0), piece[0]);
    }

    @Test
    public void getCHNYPiecesFromCHNYCharacterCAST() {
        String letter = "CHNY";
        PiecesConverter sut = new PiecesConverter();
        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("CH", 0), piece[0]);
        assertEquals(new Piece("NY", 0), piece[1]);
    }
}
