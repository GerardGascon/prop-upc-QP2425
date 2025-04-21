package edu.upc.prop.scrabble.domain.pieces;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;
import edu.upc.prop.scrabble.utils.Pair;

import java.util.Arrays;

public class PiecesConverterFactory {
    private final IFileReader piecesReader;
    private final PieceGenerator pieceGenerator;

    public PiecesConverterFactory(IFileReader piecesReader, PieceGenerator pieceGenerator) {
        this.piecesReader = piecesReader;
        this.pieceGenerator = pieceGenerator;
    }

    public PiecesConverter run(Language language) {
        String piecesFile = piecesReader.run(language);
        Piece[] pieces = Arrays.stream(pieceGenerator.run(piecesFile)).map(Pair::first).toArray(Piece[]::new);

        return switch (language) {
            case Language.Catalan -> new CatalanPiecesConverter(pieces);
            case Language.Spanish -> new SpanishPiecesConverter(pieces);
            case Language.English -> new EnglishPiecesConverter(pieces);
        };
    }
}
