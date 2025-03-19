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

        return points;
    }

    private int getPiecePoints(Vector2 position) {
        int letterMultiplier = getMultiplier(position);
        return board.getCellPiece(position.x, position.y).value() * letterMultiplier;
    }

    private int getMultiplier(Vector2 position) {
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
}
