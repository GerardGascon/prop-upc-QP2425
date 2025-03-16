package edu.upc.prop.scrabble.data.board;

public class TileConverter {
    public static String convert(TileType tile) {
        return switch (tile) {
            case Blank -> ". ";
            case QuadrupleWord -> "4W";
            case TripleWord -> "3W";
            case DoubleWord -> "2W";
            case QuadrupleLetter -> "4L";
            case TripleLetter -> "3L";
            case DoubleLetter -> "2L";
        };
    }
}
