package scrabble.pieces;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.pieces.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestPiecesConverterFactory {
    @Test
    public void generatePiecesConverterWithEnglishDictionary() {
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.English);

        assertEquals(EnglishPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void generatePiecesConverterWithCatalanDictionary() {
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Catalan);

        assertEquals(CatalanPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void generatePiecesConverterWithSpanishDictionary() {
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Spanish);

        assertEquals(SpanishPiecesConverter.class, piecesConverter.getClass());
    }
}
