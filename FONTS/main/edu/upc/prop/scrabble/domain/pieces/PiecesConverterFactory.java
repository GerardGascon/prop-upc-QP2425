package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;

public class PiecesConverterFactory {
    private final PiecesReader piecesReader;
    private final PieceGenerator pieceGenerator;

    public PiecesConverterFactory(PiecesReader piecesReader, PieceGenerator pieceGenerator) {
        this.piecesReader = piecesReader;
        this.pieceGenerator = pieceGenerator;
    }

    public PiecesConverter run(Language language) {
        return switch (language) {
            case Language.Catalan -> new CatalanPiecesConverter();
            case Language.Spanish -> new SpanishPiecesConverter();
            case Language.English -> new EnglishPiecesConverter();
        };
    }
}
