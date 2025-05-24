package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

/**
 * La classe WordPlacer és responsable de col·locar paraules (peces) al tauler i actualitzar la puntuació.
 * També notifica a la vista per refrescar el tauler després de col·locar la paraula.
 *
 * @author Gerard Gascón
 */
public class WordPlacer {
    /** El tauler de joc on es col·loquen les peces */
    private final Board board;
    /** La vista que s'actualitza després de cada jugada */
    private final IBoard view;
    /** Calculador de punts per a la jugada */
    private final PointCalculator pointCalculator;
    /** Jugador que fa la jugada */
    private final Player player;

    /**
     * Constructor que inicialitza el WordPlacer amb el jugador, tauler, vista i calculador de punts.
     *
     * @param player          Jugador que realitza la jugada
     * @param board           Tauler on es col·loquen les peces
     * @param view            Vista que s'actualitzarà després de la jugada
     * @param pointCalculator Calculador de punts per a la jugada
     */
    public WordPlacer(Player player, Board board, IBoard view, PointCalculator pointCalculator) {
        this.player = player;
        this.board = board;
        this.view = view;
        this.pointCalculator = pointCalculator;
    }

    /**
     * Col·loca les peces al tauler segons la posició inicial i la direcció.
     * Calcula la puntuació resultant i l'afegeix al jugador.
     * Actualitza la vista per reflectir el canvi al tauler.
     *
     * @param pieces    Array de peces noves a col·locar
     * @param x         Posició X d'inici de la paraula
     * @param y         Posició Y d'inici de la paraula
     * @param direction Direcció horitzontal o vertical per col·locar la paraula
     */
    public void run(Piece[] pieces, int x, int y, Direction direction) {
        Vector2[] positions;
        if (direction == Direction.Vertical)
            positions = placeWordVertical(pieces, x, y);
        else
            positions = placeWordHorizontal(pieces, x, y);

        int points = pointCalculator.run(positions, pieces);
        player.addScore(points);

        view.updateBoard();
    }

    /**
     * Col·loca les peces verticalment a partir de la posició (x, y).
     * Ignora les cel·les ja ocupades.
     *
     * @param pieces Peces a col·locar
     * @param x      Posició X fixa (columnes)
     * @param y      Posició Y inicial (files)
     * @return Array amb les posicions on s'han col·locat les peces
     */
    private Vector2[] placeWordVertical(Piece[] pieces, int x, int y) {
        Vector2[] positions = new Vector2[pieces.length];

        for (int i = y, j = 0; i < board.getSize(); i++) {
            Vector2 pos = new Vector2(x, i);
            if (board.getCellPiece(pos.x, pos.y) != null)
                continue;
            board.placePiece(pieces[j], pos.x, pos.y);
            view.updateCell(pieces[j].letter(), pieces[j].value(), pos.x, pos.y);
            positions[j] = pos;
            j++;
            if (j == pieces.length)
                break;
        }
        return positions;
    }

    /**
     * Col·loca les peces horitzontalment a partir de la posició (x, y).
     * Ignora les cel·les ja ocupades.
     *
     * @param pieces Peces a col·locar
     * @param x      Posició X inicial (columnes)
     * @param y      Posició Y fixa (files)
     * @return Array amb les posicions on s'han col·locat les peces
     */
    private Vector2[] placeWordHorizontal(Piece[] pieces, int x, int y) {
        Vector2[] positions = new Vector2[pieces.length];

        for (int i = x, j = 0; i < board.getSize(); i++) {
            Vector2 pos = new Vector2(i, y);
            if (board.getCellPiece(pos.x, pos.y) != null)
                continue;
            board.placePiece(pieces[j], pos.x, pos.y);
            view.updateCell(pieces[j].letter(), pieces[j].value(), pos.x, pos.y);
            positions[j] = pos;
            j++;
            if (j == pieces.length)
                break;
        }
        return positions;
    }
}
