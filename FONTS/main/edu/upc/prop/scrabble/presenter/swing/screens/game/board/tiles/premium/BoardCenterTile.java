package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;

import java.awt.*;

/**
 * Classe que representa la casella central del tauler de Scrabble.
 * <p>
 * Aquesta casella marca el punt inicial del joc i també és una casella de puntuació doble de paraula.
 * Es dibuixa amb un fons rosa i una estrella central.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardCenterTile extends BoardTile {

    /**
     * Constructor per a la casella central.
     *
     * @param x                  coordenada X de la casella
     * @param y                  coordenada Y de la casella
     * @param handView           vista de la mà del jugador
     * @param boardView          vista general del tauler
     * @param blankPieceSelector selector de fitxes en blanc
     */
    public BoardCenterTile(int x, int y, IHandView handView, BoardView boardView, IBlankPieceSelector blankPieceSelector) {
        super(x, y, handView, boardView, blankPieceSelector);
        setBackground(new Color(0xff, 0xc0, 0xcb));
        createTooltip("Start here (Double Word Score)");
    }

    /**
     * Dibuixa la casella amb un rectangle arrodonit i una estrella central.
     *
     * @param g      objecte gràfic
     * @param bg     color de fons
     * @param radius radi de les vores arrodonides
     */
    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        int[] xPoly = {0, getWidth() / 2 - getWidth() / 8, getWidth() / 2, getWidth() / 2 + getWidth() / 8, getWidth(), getWidth() / 2 + getWidth() / 8, getWidth() / 2, getWidth() / 2 - getWidth() / 8, 0};
        int[] yPoly = {getHeight() / 2, getHeight() / 2 - getHeight() / 8, 0, getHeight() / 2 - getHeight() / 8, getHeight() / 2, getHeight() / 2 + getHeight() / 8, getHeight(), getHeight() / 2 + getHeight() / 8, getHeight() / 2};

        for (int i = 0; i < xPoly.length; i++) {
            xPoly[i] = (int)(xPoly[i] / 1.5f) + (int)(getWidth() - getWidth() / 1.5f) / 2;
            yPoly[i] = (int)(yPoly[i] / 1.5f) + (int)(getHeight() - getHeight() / 1.5f) / 2;
        }

        g.setColor(new Color(0x80, 0x00, 0x00));
        g.fillPolygon(xPoly, yPoly, xPoly.length);
    }
}
