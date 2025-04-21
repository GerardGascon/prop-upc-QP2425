package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.pieces.*;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;
import org.junit.Test;
import scrabble.stubs.PiecesReaderStub;

import static org.junit.Assert.*;

public class TestPiecesConverterFactory {
    @Test
    public void generatePiecesConverterWithEnglishDictionary() {
        IFileReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.English);

        assertEquals(EnglishPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void generatePiecesConverterWithCatalanDictionary() {
        IFileReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Catalan);

        assertEquals(CatalanPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void generatePiecesConverterWithSpanishDictionary() {
        IFileReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Spanish);

        assertEquals(SpanishPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void newPiecesConverterParsingWithCorrectLanguageScore() {
        IFileReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Catalan);

        assertArrayEquals(new Piece[]{new Piece("A", 5)}, piecesConverter.run("A"));
    }

    @Test
    public void newPiecesConverterParsingWithMultipleScores() {
        IFileReader piecesReader = new PiecesReaderStub("A 5 5\nB 3 2");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.English);

        assertArrayEquals(new Piece[]{new Piece("A", 5), new Piece("B", 2)}, piecesConverter.run("AB"));
    }
}
