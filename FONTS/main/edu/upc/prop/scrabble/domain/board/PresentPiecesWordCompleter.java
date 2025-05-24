package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Aquesta classe s'encarrega d'identificar i completar les paraules que es formen amb les peces
 * noves col·locades al tauler de Scrabble, així com les paraules adjacents que es formen per la interacció
 * amb peces ja existents.
 * <p>
 * Permet obtenir totes les paraules resultants després d'una jugada.
 *
 * @author Gerard Gascón
 */
public class PresentPiecesWordCompleter {
    /** Instància que obté les peces que formen paraules al tauler */
    private final WordGetter wordGetter;

    /**
     * Constructor que crea un PresentPiecesWordCompleter a partir del tauler on es juga.
     *
     * @param board El tauler on es vol detectar i completar les paraules
     */
    public PresentPiecesWordCompleter(Board board) {
        this.wordGetter = new WordGetter(board);
    }

    /**
     * Executa la lògica per obtenir totes les paraules formades amb les peces col·locades
     * a les posicions indicades. Primer determina la direcció de la paraula (horitzontal o vertical)
     * i després obté les paraules que s'han format tant en aquesta direcció com en la perpendicular.
     *
     * @param positions Les posicions de les peces noves col·locades al tauler
     * @param pieces Les peces noves col·locades
     * @return Un array de Strings amb totes les paraules formades (horitzontals i verticals)
     * @see Piece
     */
    public String[] run(Vector2[] positions, Piece[] pieces) {
        Direction direction = getWordDirection(positions);
        return getPresentWords(positions, pieces, direction);
    }

    /**
     * Retorna totes les paraules presents en la direcció indicada, o en ambdues direccions
     * si la direcció és null (jugada amb una sola peça).
     *
     * @param positions Posicions de les peces col·locades
     * @param pieces Peces col·locades
     * @param direction Direcció de la paraula principal (horitzontal, vertical o null)
     * @return Array de paraules formades
     */
    private String[] getPresentWords(Vector2[] positions, Piece[] pieces, Direction direction) {
        List<String> words = new ArrayList<>();
        if (direction == null) {
            words.addAll(Arrays.stream(getPresentWords(positions, pieces, Direction.Horizontal)).toList());
            words.addAll(Arrays.stream(getPresentWords(positions, pieces, Direction.Vertical)).toList());
            return words.toArray(String[]::new);
        }

        if (direction == Direction.Horizontal)
            return getWords(positions, pieces, direction, words);
        if (direction == Direction.Vertical)
            return getWords(positions, pieces, direction, words);

        return words.toArray(String[]::new);
    }

    /**
     * Obté la paraula principal formada a la direcció donada, i completa amb les paraules adjacents.
     *
     * @param positions Posicions de les peces col·locades
     * @param pieces Peces col·locades
     * @param direction Direcció de la paraula principal
     * @param words Llista on afegir les paraules trobades
     * @return Array de paraules formades
     */
    private String[] getWords(Vector2[] positions, Piece[] pieces, Direction direction, List<String> words) {
        String word = getWord(positions, pieces, direction);
        if (word != null)
            words.add(word);
        if (pieces.length > 1)
            words.addAll(Arrays.stream(completeAdjacentWords(positions, pieces, direction)).toList());
        return words.toArray(String[]::new);
    }

    /**
     * Completa i obté les paraules adjacents formades per la interacció amb les peces ja presents
     * perpendicularment a la direcció de la paraula principal.
     *
     * @param positions Posicions de les peces col·locades
     * @param pieces Peces col·locades
     * @param direction Direcció de la paraula principal
     * @return Array de paraules adjacents formades
     */
    private String[] completeAdjacentWords(Vector2[] positions, Piece[] pieces, Direction direction) {
        List<String> words = new ArrayList<>();
        for (int i = 0; i < positions.length; i++) {
            Piece[] presentPieces = getPresentPieces(positions[i], pieces[i], direction);
            if (presentPieces.length <= 1)
                continue;

            StringBuilder result = new StringBuilder();
            for (Piece wordPiece : presentPieces)
                result.append(wordPiece.letter());
            words.add(result.toString());
        }

        return words.toArray(String[]::new);
    }

    /**
     * Obté les peces que formen una paraula en la direcció perpendicular a la direcció principal,
     * a partir de la peça i la seva posició.
     *
     * @param position Posició de la peça
     * @param piece Peça col·locada
     * @param direction Direcció principal de la paraula
     * @return Array de peces que formen la paraula perpendicular
     */
    private Piece[] getPresentPieces(Vector2 position, Piece piece, Direction direction) {
        Direction directionToCheck = direction == Direction.Horizontal ? Direction.Vertical : Direction.Horizontal;

        return wordGetter.run(
                new Piece[]{piece}, new Vector2[]{position}, directionToCheck
        );
    }

    /**
     * Obté la paraula completa que formen les peces col·locades a les posicions indicades,
     * en la direcció donada.
     *
     * @param positions Posicions de les peces col·locades
     * @param pieces Peces col·locades
     * @param direction Direcció de la paraula
     * @return La paraula formada, o null si només és una sola lletra (sense paraula)
     */
    private String getWord(Vector2[] positions, Piece[] pieces, Direction direction) {
        Piece[] wordPieces = wordGetter.run(pieces, positions, direction);
        if (wordPieces.length <= 1)
            return null;

        StringBuilder result = new StringBuilder();
        for (Piece wordPiece : wordPieces)
            result.append(wordPiece.letter());
        return result.toString();
    }

    /**
     * Determina la direcció de la paraula a partir de les posicions de les peces.
     * Si només hi ha una peça, retorna null.
     * Si la coordenada x coincideix, la direcció és vertical, sinó horitzontal.
     *
     * @param positions Posicions de les peces col·locades
     * @return Direcció de la paraula (Horizontal, Vertical o null)
     */
    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }
}
