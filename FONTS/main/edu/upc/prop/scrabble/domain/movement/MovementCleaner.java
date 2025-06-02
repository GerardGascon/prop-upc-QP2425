package edu.upc.prop.scrabble.domain.movement;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;

/***
 * Filtra les peces d'una nova col·locació de paraula que se superposen amb les peces existents al tauler.
 * Gestiona tant l'orientació vertical com l'horitzontal de la paraula.
 * @author Gina Escofet González
 */
public class MovementCleaner {
    /**
     * El tauler de la partida
     */
    private final Board board;
    /**
     * El conversor de fitxes
     */
    private final PiecesConverter piecesConverter;

    /***
     * Construeix un MovementCleaner amb el board i el piecesConverter.
     * @param board L'estat actual del tauler.
     * @param piecesConverter Converteix entre String a Piece[].
     * @throws IllegalArgumentException si algun dels paràmetres és nul.
     */
    public MovementCleaner(Board board, PiecesConverter piecesConverter) {
        if (board == null) {
            throw new IllegalArgumentException("Board cannot be null");
        }
        if (piecesConverter == null) {
            throw new IllegalArgumentException("PiecesConverter cannot be null");
        }
        this.board = board;
        this.piecesConverter = piecesConverter;
    }

    /***
     * Filtra i retorna només les noves peces necessàries per a la col·locació d'una paraula.
     * @param movement El moviment de paraula proposat que conté: la paraula a col·locar, les coordenades d'inici (x,y), i
     * la direcció de col·locació (horitzontal/vertical).
     * @return Matriu de peces necessàries per completar la col·locació de la paraula.
     * @throws IllegalArgumentException si el moviment és nul.
     */
    public Pair<Piece, Vector2>[] run(Movement movement) {
        if (movement == null) {
            throw new IllegalArgumentException("Movement cannot be null");
        }
        Direction direction = movement.direction();
        if (direction == Direction.Vertical) {
            return CleanVertical(movement);
        }
        else {
            return CleanHorizontal(movement);
        }
    }

    /**
     * Neteja un moviment horitzontal
     *
     * @param movement El moviment de paraula proposat que conté: la paraula a col·locar, les coordenades d'inici (x,y), i
     * la direcció de col·locació (horitzontal).
     * @return Matriu de peces necessàries per completar la col·locació de la paraula.
     */
    private Pair<Piece, Vector2>[] CleanHorizontal(Movement movement) {
        ArrayList<Pair<Piece, Vector2>> requiredPieces= new ArrayList<>();
        int x = movement.x();
        int y = movement.y();
        Piece[] allPieces = piecesConverter.run(movement.word());
        int n = allPieces.length;
        for (int i = 0; i < n; ++i) {
            if (board.isCellEmpty(x + i, y)) {
                requiredPieces.add(new Pair<>(allPieces[i], new Vector2(x + i, y)));
            }
        }
        return requiredPieces.toArray(Pair[]::new);

    }

    /**
     * Neteja un moviment vertical
     *
     * @param movement El moviment de paraula proposat que conté: la paraula a col·locar, les coordenades d'inici (x,y), i
     * la direcció de col·locació (vertical).
     * @return Matriu de peces necessàries per completar la col·locació de la paraula.
     */
    private Pair<Piece, Vector2>[] CleanVertical(Movement movement) {
        ArrayList<Pair<Piece, Vector2>> requiredPieces = new ArrayList<>();
        int x = movement.x();
        int y = movement.y();
        Piece[] allPieces = piecesConverter.run(movement.word());
        int n = allPieces.length;
        for (int i = 0; i < n; ++i) {
            if (board.isCellEmpty(x, y + i)) {
                requiredPieces.add(new Pair<>(allPieces[i], new Vector2(x, y + i)));
            }
        }
        return requiredPieces.toArray(Pair[]::new);
    }
}