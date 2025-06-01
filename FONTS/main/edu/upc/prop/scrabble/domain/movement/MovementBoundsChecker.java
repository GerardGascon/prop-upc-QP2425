package edu.upc.prop.scrabble.domain.movement;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;

/***
 * Classe per verificar si un moviment donat es troba dins dels límits d'un tauler.
 * Gestiona tant l'orientació de paraules vertical com horitzontal.
 * @author Gina Escofet González
 */
public class MovementBoundsChecker {
    /**
     * El tauler de la partida
     */
    private final Board board;
    /**
     * El conversor de fitxes
     */
    private final PiecesConverter piecesConverter;

    /**
     * Construeix una nova instància de MovementBoundsChecker.
     * @param board El tauler de joc en el qual es verificaran els límits.
     * @param piecesConverter El convertidor utilitzat per transformar una paraula en un array de peces.
     */
    public MovementBoundsChecker(Board board, PiecesConverter piecesConverter) {
        this.board = board;
        this.piecesConverter = piecesConverter;
    }

    /***
     * Verifica si un moviment donat es troba dins dels límits del tauler de joc.
     * @param movement El moviment a validar, que conté la paraula, la posició inicial (x, y),
     * i la direcció (vertical/horitzontal).
     * @return Cert si la paraula sencera encaixa dins dels límits del tauler en la direcció
     * especificada, Fals en cas contrari (incloent-hi entrada nul·la o paraula nul·la).
     */
    public boolean run(Movement movement) {
        if (movement == null || movement.word() == null) return false;
        Direction direction = movement.direction();
        if (direction == Direction.Vertical) {
            return VerifyVertical(movement);
        }
        else {
            return VerifyHorizontal(movement);
        }
    }

    /**
     * Verifica si es troba dins dels límits horitzontalment
     *
     * @param movement El moviment a efectuar
     * @return Cert si la paraula sencera cap horitzontalment
     */
    private boolean VerifyHorizontal(Movement movement) {
        int x = movement.x();
        int y = movement.y();
        Piece[] pieces = piecesConverter.run(movement.word());
        int n = pieces.length;
        return board.isCellValid(x, y) && board.isCellValid(x + n - 1, y);
    }

    /**
     * Verifica si es troba dins dels límits verticalment
     *
     * @param movement El moviment a efectuar
     * @return Cert si la paraula sencera cap verticalment
     */
    private boolean VerifyVertical(Movement movement) {
        int x = movement.x();
        int y = movement.y();
        Piece[] pieces = piecesConverter.run(movement.word());
        int n = pieces.length;
        return board.isCellValid(x, y) && board.isCellValid(x, y + n - 1);
    }
}
