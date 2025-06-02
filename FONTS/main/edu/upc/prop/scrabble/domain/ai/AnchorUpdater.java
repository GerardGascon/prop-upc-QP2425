package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;

/**
 * Classe feta per actualitzar els anchors
 * @author Albert Usero
 * @author Felipe Martínez
 * @see Anchors
 */
public class AnchorUpdater {
    /**
     * Anchors a actualitzar
     * @see Anchors
     */
    private final Anchors anchors;
    /**
     * Board sobre la qual estan definits els anchors
     * @see Board
     */
    private final Board board;
    /**
     * Convertidor de peces
     * @see PiecesConverter
     */
    private final PiecesConverter piecesConverter;

    /**
     * Crea un nou actualitzador d'anchors. Afegeix l'anchor inicial
     * al centre del tauler.
     * @param anchors Anchors a actualitzar
     * @param board Tauler sobre el qual s'han creat els anchors
     * @param piecesConverter Convertidor de peces
     * @see Anchors
     * @see Board
     * @see PiecesConverter
     */
    public AnchorUpdater(Anchors anchors, Board board, PiecesConverter piecesConverter) {
        this.anchors = anchors;
        this.board = board;
        this.piecesConverter = piecesConverter;
        this.anchors.addAnchor(board.getSize() / 2, board.getSize() / 2);
    }

    /**
     * Actualitza els anchors segons el moviment realitzat
     * @param move Moviment realitzat sobre el qual s'han d'actualitzar el anchors
     * @see Movement
     * @see Anchors
     */
    public void run(Movement move) {
        int size = piecesConverter.run(move.word()).length;

        if (move.direction() == Direction.Vertical)
            updateVerticalAnchors(move.x(), move.y(), size);
        if (move.direction() == Direction.Horizontal)
            updateHorizontalAnchors(move.x(), move.y(), size);
    }

    /**
     * Actualitza els anchors per un moviment que s'ha efectuat en horitzontal
     * @param x Coordenada x del moviment
     * @param y Coordenada y del moviment
     * @param size Quantitat de peces utilitzades al moviment
     */
    private void updateHorizontalAnchors(int x, int y, int size) {
        if (board.isCellValid(x - 1, y) && board.isCellEmpty(x - 1, y) )
            anchors.addAnchor(x - 1, y); // Previous to first
        if (board.isCellValid(x + size, y) && board.isCellEmpty(x + size, y))
            anchors.addAnchor(x + size, y); // Next to last

        for (int i = x; i < x + size; i++) {
            if (board.isCellValid(i, y) && anchors.exists(i, y))
                anchors.removeAnchor(i, y);
            if (board.isCellValid(i, y + 1) && board.isCellEmpty(i, y + 1))
                anchors.addAnchor(i, y + 1);
            if (board.isCellValid(i, y - 1) && board.isCellEmpty(i, y - 1))
                anchors.addAnchor(i, y - 1);
        }
    }

    /**
     * Actualitza els anchors per un moviment que s'ha efectuat en vertical
     * @param x Coordenada x del moviment
     * @param y Coordenada y del moviment
     * @param size Quantitat de peces utilitzades al moviment
     */
    private void updateVerticalAnchors(int x, int y, int size) {
        if (board.isCellValid(x, y - 1) && board.isCellEmpty(x, y - 1))
            anchors.addAnchor(x, y - 1);
        if (board.isCellValid(x, y + size) && board.isCellEmpty(x, y + size))
            anchors.addAnchor(x, y + size);

        for (int i = y; i < y + size; i++) {
            if (board.isCellValid(x, i) && anchors.exists(x, i))
                anchors.removeAnchor(x, i);
            if (board.isCellValid(x + 1, i) && board.isCellEmpty(x + 1, i))
                anchors.addAnchor(x + 1, i);
            if (board.isCellValid(x - 1, i) && board.isCellEmpty(x - 1, i))
                anchors.addAnchor(x - 1, i);
        }
    }
}