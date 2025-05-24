package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

import java.awt.*;

/**
 * Fitxa buida del tauler.
 * <p>
 * Representa una cel·la del tauler sense cap premi ni peça especial,
 * simplement un espai disponible per posar una fitxa.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardEmptyTile extends BoardTile {
    /**
     * Crea una fitxa buida a la posició (x,y).
     *
     * @param x coordenada x al tauler
     * @param y coordenada y al tauler
     * @param handView vista de la mà de fitxes
     * @param boardView vista del tauler
     * @param blankPieceSelector selector per fitxes en blanc
     */
    public BoardEmptyTile(int x, int y, IHandView handView, BoardView boardView, IBlankPieceSelector blankPieceSelector) {
        super(x, y, handView, boardView, blankPieceSelector);
        setBackground(new Color(0x56, 0xa8, 0x87));
    }

    /**
     * Dibuixa la fitxa buida com un rectangle arrodonit amb el color de fons.
     *
     * @param g context gràfic 2D
     * @param bg color de fons
     * @param radius radi de les cantonades arrodonides
     */
    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}
