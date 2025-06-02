package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Classe responsable de calcular els punts associats a un moviment al tauler de Scrabble.
 * Considera factors com el valor de cada peça, multiplicadors de paraula, bonificacions,
 * i la interacció amb peces ja col·locades al tauler.
 *
 * @author Gerard Gascón
 */
public class PointCalculator {
    /** El tauler actual del joc */
    private final Board board;
    /** Component per obtenir paraules a partir de peces i posicions */
    private final WordGetter wordGetter;

    /**
     * Constructor que crea una instància de PointCalculator amb el tauler proporcionat.
     *
     * @param board El tauler on es juga la partida
     */
    public PointCalculator(Board board) {
        this.board = board;
        this.wordGetter = new WordGetter(board);
    }

    /**
     * Calcula el total de punts d'un moviment tenint en compte les posicions i les peces col·locades.
     * Es té en compte el valor de les peces, multiplicadors de paraula i bonificacions addicionals.
     *
     * @param positions Les posicions on es col·loquen les noves peces
     * @param pieces Les peces que es volen col·locar
     * @return El total de punts que es guanyarien amb aquest moviment
     * @see Piece
     */
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

    /**
     * Determina la direcció de la paraula a partir de les posicions donades.
     * Retorna Vertical, Horitzontal o null si només hi ha una peça.
     *
     * @param positions Les posicions de les peces
     * @return La direcció de la paraula
     */
    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }

    /**
     * Calcula els punts de les paraules que es formen creuant la paraula principal.
     *
     * @param positions Posicions de les peces noves
     * @param pieces Peces col·locades
     * @param direction Direcció de la paraula principal
     * @return Punts totals de les paraules creuades
     */
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

    /**
     * Obté les peces presents en la direcció perpendicular per formar paraules creuades.
     *
     * @param position Posició de la peça nova
     * @param piece Peça nova
     * @param direction Direcció de la paraula principal
     * @return Les peces que formen la paraula creuada
     */
    private Piece[] getPresentPieces(Vector2 position, Piece piece, Direction direction) {
        Direction directionToCheck = direction == Direction.Horizontal ? Direction.Vertical : Direction.Horizontal;

        return wordGetter.run(
                new Piece[]{piece}, new Vector2[]{position}, directionToCheck
        );
    }

    /**
     * Calcula els punts d'una paraula formada per les peces indicades.
     *
     * @param position Posició de la paraula
     * @param pieces Peces de la paraula
     * @return Punts totals de la paraula
     */
    private int getPresentPoints(Vector2 position, Piece[] pieces) {
        int wordPoints = getPiecePoints(pieces);
        int multiplier = getWordMultiplier(position);
        return wordPoints * multiplier;
    }

    /**
     * Calcula els punts de les peces que ja estaven presents a la paraula abans d'afegir les noves peces.
     *
     * @param positions Posicions de les peces noves
     * @param pieces Peces noves
     * @param direction Direcció de la paraula
     * @return Punts de les peces ja presents
     */
    private int getAlreadyPresentWordPiecesPoints(Vector2[] positions, Piece[] pieces, Direction direction) {
        List<Piece> presentPieces = new ArrayList<>(Arrays.stream(wordGetter.run(pieces, positions, direction)).toList());

        for (Piece piece : pieces)
            presentPieces.remove(piece);

        return getPiecePoints(presentPieces.toArray(new Piece[0]));
    }

    /**
     * Calcula la suma de punts de les peces en les posicions indicades.
     *
     * @param positions Posicions de les peces
     * @param pieces Peces col·locades
     * @return Punts totals de les peces
     */
    private int getPiecePoints(Vector2[] positions, Piece[] pieces) {
        int points = 0;
        for (int i = 0; i < positions.length; i++) {
            Vector2 position = positions[i];
            Piece piece = pieces[i];
            points += getPiecePoints(position, piece);
        }
        return points;
    }

    /**
     * Calcula la suma dels valors de les peces indicades.
     *
     * @param pieces Peces de les quals es vol obtenir el valor
     * @return Suma dels punts de les peces
     */
    private int getPiecePoints(Piece[] pieces) {
        return Arrays.stream(pieces).mapToInt(Piece::value).sum();
    }

    /**
     * Calcula el bonus per fer un bingo (col·locar 7 peces en un sol torn).
     *
     * @param positions Posicions de les peces col·locades
     * @return 50 punts si són 7 peces, 0 en cas contrari
     */
    private static int getBonus(Vector2[] positions) {
        if (positions.length == 7)
            return 50; // Bingo
        return 0;
    }

    /**
     * Calcula el multiplicador total de paraula a partir de les posicions.
     *
     * @param positions Posicions de les peces noves
     * @return Multiplicador resultant
     */
    private int getWordMultiplier(Vector2[] positions) {
        int multiplier = 1;
        for (Vector2 position : positions) {
            multiplier *= getWordMultiplier(position);
        }

        return multiplier;
    }

    /**
     * Calcula els punts d'una peça tenint en compte els multiplicadors de lletra.
     *
     * @param position Posició de la peça
     * @param piece Peça que es vol puntuar
     * @return Punts totals de la peça amb multiplicadors
     */
    private int getPiecePoints(Vector2 position, Piece piece) {
        int letterMultiplier = getLetterMultiplier(position);
        return piece.value() * letterMultiplier;
    }

    /**
     * Obté el multiplicador de lletra segons el tipus de casella premium.
     *
     * @param position Posició al tauler
     * @return Multiplicador de lletra (1 si no hi ha casella especial)
     */
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

    /**
     * Obté el multiplicador de paraula segons el tipus de casella premium.
     * La casella central també multiplica per 2.
     *
     * @param position Posició al tauler
     * @return Multiplicador de paraula (1 si no hi ha casella especial)
     */
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
