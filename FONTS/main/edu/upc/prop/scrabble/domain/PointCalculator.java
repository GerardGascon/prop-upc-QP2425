package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PointCalculator {
    private final Board board;

    public PointCalculator(Board board) {
        this.board = board;
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
        //TODO: Check here for existing words
        int multiplier = getWordMultiplier(positions);

        int bonus = getBonus(positions);

        return points * multiplier + bonus;
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

        Vector2[] adjacentTiles = getAdjacentPieces(position, insertedPositions);
        int adjacentPoints = getAdjacentPiecePoints(position, adjacentTiles);

        return cellPoints;
    }

    private Vector2[] getAdjacentPieces(Vector2 position, Vector2[] insertedPositions) {
        List<Vector2> adjacentTiles = new ArrayList<>();

        Vector2 pos1 = new Vector2(position.x - 1, position.y);
        if (!board.isCellEmpty(pos1.x, pos1.y) && !contains(pos1, insertedPositions))
            adjacentTiles.add(pos1);
        Vector2 pos2 = new Vector2(position.x + 1, position.y);
        if (!board.isCellEmpty(pos2.x, pos2.y) && !contains(pos2, insertedPositions))
            adjacentTiles.add(pos2);
        Vector2 pos3 = new Vector2(position.x, position.y - 1);
        if (!board.isCellEmpty(pos3.x, pos3.y) && !contains(pos3, insertedPositions))
            adjacentTiles.add(pos3);
        Vector2 pos4 = new Vector2(position.x, position.y + 1);
        if (!board.isCellEmpty(pos4.x, pos4.y) && !contains(pos4, insertedPositions))
            adjacentTiles.add(pos4);

        return adjacentTiles.toArray(new Vector2[0]);
    }

    private boolean contains(Vector2 position, Vector2[] insertedPositions) {
        return Arrays.asList(insertedPositions).contains(position);
    }

    private int getAdjacentPiecePoints(Vector2 position, Vector2[] insertedPositions) {
        for (Vector2 insertedPos : insertedPositions) {
            return getAdjacentPiecePoints(insertedPos, new Vector2(insertedPos.x - position.x, insertedPos.y - position.y));
        }

        return 0;
    }

    private int getAdjacentPiecePoints(Vector2 position, Vector2 direction) {
        return 0;
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
