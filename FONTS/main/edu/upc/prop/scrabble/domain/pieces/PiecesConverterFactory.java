package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.properties.Language;

public class PiecesConverterFactory {
    private final PieceGenerator pieceGenerator;

    public PiecesConverterFactory(PieceGenerator pieceGenerator) {
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
