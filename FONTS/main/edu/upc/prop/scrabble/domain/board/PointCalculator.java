package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointCalculator {
    private final Board board;
    private final WordGetter wordGetter;

    public PointCalculator(Board board, WordGetter wordGetter) {
        this.board = board;
        this.wordGetter = wordGetter;
    }

    public int run(Vector2[] positions, Piece[] pieces) {
        Direction direction = getWordDirection(positions);

        int points = getPiecePoints(positions, pieces);
        int multiplier = getWordMultiplier(positions);
        int alreadyPresentWordPiecesPoints = getAlreadyPresentWordPiecesPoints(positions, pieces, direction);
        int newWordPoints = (points + alreadyPresentWordPiecesPoints) * multiplier;

        int presentWordsPoints = getPresentWordPoints(positions, pieces, direction);

        int bonus = getBonus(positions);

        return newWordPoints + presentWordsPoints + bonus;
    }

    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }

    private int getPresentWordPoints(Vector2[] positions, Piece[] pieces, Direction direction) {
        int points = 0;
        for (int i = 0; i < positions.length; i++) {
            Piece[] presentPieces = getPresentPieces(positions[i], pieces[i], direction);
            if (presentPieces.length <= 1)
                continue;

            points += getPresentPoints(positions[i], presentPieces);
        }

        return points;
    }

    private Piece[] getPresentPieces(Vector2 position, Piece piece, Direction direction) {
        Direction directionToCheck = direction == Direction.Horizontal ? Direction.Vertical : Direction.Horizontal;

        return wordGetter.run(
                new Piece[]{piece}, new Vector2[]{position}, directionToCheck
        );
    }

    private int getPresentPoints(Vector2 position, Piece[] pieces) {
        int wordPoints = getPiecePoints(pieces);
        int multiplier = getWordMultiplier(position);
        return wordPoints * multiplier;
    }

    private int getAlreadyPresentWordPiecesPoints(Vector2[] positions, Piece[] pieces, Direction direction) {
        List<Piece> presentPieces = new ArrayList<>(Arrays.stream(wordGetter.run(pieces, positions, direction)).toList());

        for (Piece piece : pieces)
            presentPieces.remove(piece);

        return getPiecePoints(presentPieces.toArray(new Piece[0]));
    }

    private int getPiecePoints(Vector2[] positions, Piece[] pieces) {
        int points = 0;
        for (int i = 0; i < positions.length; i++) {
            Vector2 position = positions[i];
            Piece piece = pieces[i];
            points += getPiecePoints(position, piece);
        }
        return points;
    }

    private int getPiecePoints(Piece[] pieces) {
        return Arrays.stream(pieces).mapToInt(Piece::value).sum();
    }

    private static int getBonus(Vector2[] positions) {
        if (positions.length == 7)
            return 50; // Bingo
        return 0;
    }

    private int getWordMultiplier(Vector2[] positions) {
        int multiplier = 1;
        for (Vector2 position : positions) {
            multiplier *= getWordMultiplier(position);
        }

        return multiplier;
    }

    private int getPiecePoints(Vector2 position, Piece piece) {
        int letterMultiplier = getLetterMultiplier(position);
        return piece.value() * letterMultiplier;
    }

    private int getLetterMultiplier(Vector2 position) {
        PremiumTileType tileType = board.getPremiumTileType(position.x, position.y);
        if (tileType == null)
            return 1;

        return switch (tileType) {
            case QuadrupleLetter -> 4;
            case TripleLetter -> 3;
            case DoubleLetter -> 2;
            default -> 1;
        };
    }

    private int getWordMultiplier(Vector2 position) {
        if (board.isCenter(position.x, position.y))
            return 2;

        PremiumTileType tileType = board.getPremiumTileType(position.x, position.y);
        if (tileType == null)
            return 1;

        return switch (tileType) {
            case QuadrupleWord -> 4;
            case TripleWord -> 3;
            case DoubleWord -> 2;
            default -> 1;
        };
    }
}
