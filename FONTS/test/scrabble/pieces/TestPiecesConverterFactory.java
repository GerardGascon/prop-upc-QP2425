package scrabble.pieces;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.pieces.*;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;
import org.junit.Test;
import scrabble.stubs.PiecesReaderStub;

import static org.junit.Assert.*;

public class TestPiecesConverterFactory {
    @Test
    public void generatePiecesConverterWithEnglishDictionary() {
        PiecesReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.English);

        assertEquals(EnglishPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void generatePiecesConverterWithCatalanDictionary() {
        PiecesReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Catalan);

        assertEquals(CatalanPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void generatePiecesConverterWithSpanishDictionary() {
        PiecesReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Spanish);

        assertEquals(SpanishPiecesConverter.class, piecesConverter.getClass());
    }

    @Test
    public void newPiecesConverterParsingWithCorrectLanguageScore(){
        PiecesReader piecesReader = new PiecesReaderStub("A 5 5");
        PieceGenerator pieceGenerator = new PieceGenerator();
        PiecesConverterFactory sut = new PiecesConverterFactory(piecesReader, pieceGenerator);

        PiecesConverter piecesConverter = sut.run(Language.Catalan);
    }
}
