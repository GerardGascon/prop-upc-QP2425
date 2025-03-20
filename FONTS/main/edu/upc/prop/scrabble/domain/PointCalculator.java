package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

public class PointCalculator {
    private final Board board;
    private final WordGetter wordGetter;

    public PointCalculator(Board board, WordGetter wordGetter) {
        this.board = board;
        this.wordGetter = wordGetter;
    }

    public int run(Vector2[] positions) {
        /*
         * 1. Calcular la puntuació de les lletres tenint en compte les caselles de multiplicació de punts per lletres
         * 2. Recollir la puntuació de paraules formades amb lletres ja col·locades anteriorment al tauler en conjunt
         *        amb les noves lletres sense tenir en compte multiplicadors
         * 3. Sumar aquesta puntuació
         * 4. Multiplicar això per les caselles de multiplicació de punts per paraules (la casella central és un
         *        multiplicador de paraula per 2)
         * 5. (Aquestes caselles prèmium no compten si han estat tapades anteriorment)
         * 6. Si juga 7 peces de cop, es considera bingo i suma 50 punts extres.
         */
        int points = getPiecePoints(positions);
        int multiplier = getWordMultiplier(positions);

        Direction direction = getWordDirection(positions);
        int presentWordsPoints = getPresentWordPoints(positions, direction);

        int bonus = getBonus(positions);

        return points * multiplier + bonus;
    }

    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }

    private int getPresentWordPoints(Vector2[] positions, Direction direction) {
        for (Vector2 position : positions) {
            Piece p = board.getCellPiece(position.x, position.y);

        }

        return 0;
    }

    private int getPiecePoints(Vector2[] positions) {
        int points = 0;
        for (Vector2 position : positions) {
            points += getPiecePoints(position, positions);
        }
        return points;
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

    private int getPiecePoints(Vector2 position, Vector2[] insertedPositions) {
        int letterMultiplier = getLetterMultiplier(position);
        int cellPoints = board.getCellPiece(position.x, position.y).value() * letterMultiplier;

        return cellPoints;
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
