package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.CatalanPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.EnglishPiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.SpanishPiecesConverter;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPiecesConverter {
    @Test
    public void getLPieceFromLCharacter() {
        String letter = "L";
        PiecesConverter sut = new EnglishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("L", 0), piece[0]);
    }

    @Test
    public void getRPieceFromRCharacter() {
        String letter = "R";
        PiecesConverter sut = new EnglishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("R", 0), piece[0]);
    }

    @Test
    public void getPiecesFromWord() {
        String letter = "LA";
        PiecesConverter sut = new EnglishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("L", 0), piece[0]);
        assertEquals(new Piece("A", 0), piece[1]);
    }

    @Test
    public void getLgeminadaPiecesFromLgeminadaCharacterCAT() {
        String letter = "L·L";
        PiecesConverter sut = new CatalanPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("L·L", 0), piece[0]);
    }

    @Test
    public void getNYPiecesFromNYCharacterCAT() {
        String letter = "NY";
        PiecesConverter sut = new CatalanPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("NY", 0), piece[0]);
    }

    @Test
    public void getRRPiecesFromRRCharacterCAST() {
        String letter = "RR";
        PiecesConverter sut = new SpanishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("RR", 0), piece[0]);
    }

    @Test
    public void getLLPiecesFromLLCharacterCAST() {
        String letter = "LL";
        PiecesConverter sut = new SpanishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("LL", 0), piece[0]);
    }

    @Test
    public void getCHPiecesFromCHCharacterCAST() {
        String letter = "CH";
        PiecesConverter sut = new SpanishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("CH", 0), piece[0]);
    }

    @Test
    public void getPieceAsBlankPieceWhenCharacterIsLowercase() {
        String letter = "a";
        PiecesConverter sut = new EnglishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("A", 0, true), piece[0]);
    }

    @Test
    public void getLgeminadaBlankPieceFromLgeminadaCharacterCAT() {
        String letter = "l·l";
        PiecesConverter sut = new CatalanPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("L·L", 0, true), piece[0]);
    }

    @Test
    public void getNYBlankPieceFromNYCharacterCAT() {
        String letter = "ny";
        PiecesConverter sut = new CatalanPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("NY", 0, true), piece[0]);
    }

    @Test
    public void getRRBlankPieceFromRRCharacterCAST() {
        String letter = "rr";
        PiecesConverter sut = new SpanishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("RR", 0, true), piece[0]);
    }

    @Test
    public void getLLBlankPieceFromLLCharacterCAST() {
        String letter = "ll";
        PiecesConverter sut = new SpanishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("LL", 0, true), piece[0]);
    }

    @Test
    public void getCHBlankPieceFromCHCharacterCAST() {
        String letter = "ch";
        PiecesConverter sut = new SpanishPiecesConverter();

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("CH", 0, true), piece[0]);
    }

    @Test
    public void getPieceWithDictionaryConvertsItWithProperScore() {
        String letter = "A";
        Piece[] dictionary = new Piece[] { new Piece("A", 5) };
        PiecesConverter sut = new EnglishPiecesConverter(dictionary);

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("A", 5, false), piece[0]);
    }

    @Test
    public void getSpecialCatalanPieceWithDictionaryConvertsItWithProperScore() {
        String letter = "NY";
        Piece[] dictionary = new Piece[] { new Piece("NY", 5) };
        PiecesConverter sut = new CatalanPiecesConverter(dictionary);

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("NY", 5, false), piece[0]);
    }

    @Test
    public void getSpecialSpanishPieceWithDictionaryConvertsItWithProperScore() {
        String letter = "CH";
        Piece[] dictionary = new Piece[] { new Piece("CH", 5) };
        PiecesConverter sut = new SpanishPiecesConverter(dictionary);

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("CH", 5, false), piece[0]);
    }

    @Test
    public void getSpecialSpanishBlankPieceWithDictionaryConvertsItWithNoScore() {
        String letter = "ch";
        Piece[] dictionary = new Piece[] { new Piece("CH", 5) };
        PiecesConverter sut = new SpanishPiecesConverter(dictionary);

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("CH", 0, true), piece[0]);
    }

    @Test
    public void getSpecialCatalanBlankPieceWithDictionaryConvertsItWithNoScore() {
        String letter = "ny";
        Piece[] dictionary = new Piece[] { new Piece("NY", 5) };
        PiecesConverter sut = new CatalanPiecesConverter(dictionary);

        Piece[] piece = sut.run(letter);

        assertEquals(new Piece("NY", 0, true), piece[0]);
    }
}
