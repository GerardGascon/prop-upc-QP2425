package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 * Aquesta classe s'encarrega d'identificar i recuperar les peces que formen una nova paraula
 * al tauler de Scrabble després de col·locar-hi peces noves.
 * Gestiona els casos en què les peces noves estenen paraules ja existents o formen paraules noves.
 * <p>
 * Permet obtenir la paraula completa (com a peces) en una direcció determinada després de la jugada.
 *
 * @author Gerard Gascón
 */
public class WordGetter {
    /** Tauler on es col·loquen les peces i es formen les paraules */
    private final Board board;

    /**
     * Constructor que crea un WordGetter per un tauler determinat.
     *
     * @param board El tauler on es juguen les peces i es formen paraules
     */
    public WordGetter(Board board) {
        this.board = board;
    }

    /**
     * Recupera les peces que formen una nova paraula al tauler després de col·locar peces noves.
     * <p>
     * Aquest mètode és útil per quan es col·loquen peces que estenen una paraula existent o en formen de noves.
     * Revisa tant les peces noves com les posicions al tauler i retorna totes les peces que formen una paraula
     * contínua en la direcció indicada (horitzontal o vertical).
     *
     * @param newPieces Array de peces noves que s'estan col·locant
     * @param newPositions Array de posicions on es col·loquen aquestes peces
     * @param direction Direcció horitzontal o vertical on es forma la paraula
     * @return Array de peces que formen la paraula resultant al tauler
     * @see Piece
     * @see Direction
     */
    public Piece[] run(Piece[] newPieces, Vector2[] newPositions, Direction direction) {
        List<Piece> pieces = new ArrayList<>();
        List<Vector2> positions = new ArrayList<>();

        for (int i = 0; i < board.getSize(); i++) {
            int x = getXPosition(newPositions[0], direction, i);
            int y = getYPosition(newPositions[0], direction, i);

            if (!board.isCellEmpty(x, y)) {
                positions.add(new Vector2(x, y));
                pieces.add(board.getCellPiece(x, y));
                continue;
            }

            int positionInNewList = Arrays.asList(newPositions).indexOf(new Vector2(x, y));
            boolean containsPosition = positionInNewList != -1;
            if (containsPosition) {
                positions.add(newPositions[positionInNewList]);
                pieces.add(newPieces[positionInNewList]);
                continue;
            }

            if (new HashSet<>(positions).containsAll(Arrays.asList(newPositions)))
                return pieces.toArray(new Piece[0]);

            pieces.clear();
        }

        return pieces.toArray(new Piece[0]);
    }

    /**
     * Calcula la coordenada Y segons la direcció de la paraula i un offset.
     *
     * @param position Posició inicial de la paraula
     * @param direction Direcció horitzontal o vertical
     * @param offset Desplaçament per iterar per la paraula
     * @return Coordenada Y corresponent
     */
    private static int getYPosition(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? position.y : offset;
    }

    /**
     * Calcula la coordenada X segons la direcció de la paraula i un offset.
     *
     * @param position Posició inicial de la paraula
     * @param direction Direcció horitzontal o vertical
     * @param offset Desplaçament per iterar per la paraula
     * @return Coordenada X corresponent
     */
    private static int getXPosition(Vector2 position, Direction direction, int offset) {
        return direction == Direction.Horizontal ? offset : position.x;
    }
}
