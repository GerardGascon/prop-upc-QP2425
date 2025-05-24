package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;

import java.awt.*;

/**
 * Casella de Doble Lletra del tauler de joc.
 * <p>
 * Aquesta casella proporciona una bonificació de puntuació per doble lletra
 * quan s'hi col·loca una fitxa. Es representa amb un color blau clar i mostra
 * un missatge emergent explicatiu.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardDoubleLetterTile extends BoardTile {

    /**
     * Crea una nova instància de la casella de doble lletra.
     *
     * @param x coordenada horitzontal al tauler
     * @param y coordenada vertical al tauler
     * @param handView vista de la mà del jugador
     * @param boardView vista del tauler
     * @param blankPieceSelector selector de fitxes blanques
     */
    public BoardDoubleLetterTile(int x, int y, IHandView handView, BoardView boardView, IBlankPieceSelector blankPieceSelector) {
        super(x, y, handView, boardView, blankPieceSelector);
        setBackground(new Color(0x87, 0xce, 0xeb));
        createTooltip("Double Letter Score");
    }

    /**
     * Dibuixa la casella amb el color de fons corresponent.
     *
     * @param g context gràfic utilitzat per dibuixar
     * @param bg color de fons de la casella
     * @param radius radi de les cantonades arrodonides
     */
    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}
