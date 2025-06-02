package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium;

import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;

import java.awt.*;

/**
 * Casella de Paraula Quàdruple del tauler de joc.
 * <p>
 * Aquesta casella aplica una bonificació de puntuació per paraula quàdruple
 * quan una paraula la travessa. Es representa amb un color vermell fosc i
 * mostra una descripció emergent informativa.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardQuadrupleWordTile extends BoardTile {

    /**
     * Crea una nova instància de la casella de paraula quàdruple.
     *
     * @param x coordenada horitzontal al tauler
     * @param y coordenada vertical al tauler
     * @param handView vista de la mà del jugador
     * @param boardView vista del tauler
     * @param blankPieceSelector selector de fitxes blanques
     */
    public BoardQuadrupleWordTile(int x, int y, IHandView handView, BoardView boardView, IBlankPieceSelector blankPieceSelector) {
        super(x, y, handView, boardView, blankPieceSelector);
        setBackground(new Color(0x80, 0x00, 0x00));
        createTooltip("Quadruple paraula");
    }

    /**
     * Dibuixa la casella amb el color de fons i forma arrodonida.
     *
     * @param g context gràfic per dibuixar
     * @param bg color de fons
     * @param radius radi de les cantonades
     */
    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}
