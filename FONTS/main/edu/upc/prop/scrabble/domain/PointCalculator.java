package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.utils.Vector2;

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
        int points = 0;
        for (Vector2 position : positions) {
            points += getPiecePoints(position);
        }
        //TODO: Check here for existing words
        int multiplier = getWordMultiplier(positions);

        return points * multiplier;
    }

    private int getWordMultiplier(Vector2[] positions) {
        int multiplier = 1;
        for (Vector2 position : positions) {
            multiplier *= getWordMultiplier(position);
        }

        return multiplier;
    }

    private int getPiecePoints(Vector2 position) {
        int letterMultiplier = getLetterMultiplier(position);
        return board.getCellPiece(position.x, position.y).value() * letterMultiplier;
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
