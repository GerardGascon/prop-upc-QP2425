package edu.upc.prop.scrabble.presenter.terminal;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.PremiumTileType;

public class TileConverter {
    public static String convert(PremiumTileType tile) {
        return switch (tile) {
            case QuadrupleWord -> "4W";
            case TripleWord -> "3W";
            case DoubleWord -> "2W";
            case QuadrupleLetter -> "4L";
            case TripleLetter -> "3L";
            case DoubleLetter -> "2L";
        };
    }

    public static String convert(Piece piece) {
        return piece.letter();
    }
}
