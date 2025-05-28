package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import javax.swing.*;
import java.awt.*;

/**
 * Casella del tauler que conté una fitxa (BoardTile).
 * <p>
 * Aquesta classe gestiona una cel·la del tauler on es pot afegir o
 * substituir una fitxa visual del joc.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardCell extends JPanel {
    /**
     * Fitxa que conté la cel·la.
     */
    private BoardTile tile;
    private BoardTemporalPieceTile temporalPieceTile;

    /**
     * Crea una nova cel·la del tauler sense fitxa i amb fons transparent.
     */
    public BoardCell() {
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    /**
     * Assigna una fitxa a la cel·la. Si ja hi havia una fitxa, la substitueix.
     *
     * @param tile la fitxa que s'afegirà a la cel·la
     */
    public void setTile(BoardTile tile) {
        if (tile instanceof BoardTemporalPieceTile temporalPieceTile) {
            setTemporalTile(temporalPieceTile);
            return;
        }

        if (this.tile != null)
            remove(this.tile);

        this.tile = tile;
        add(tile);

        revalidate();
        repaint();
    }

    private void setTemporalTile(BoardTemporalPieceTile temporalPieceTile) {
        this.temporalPieceTile = temporalPieceTile;
        remove(this.tile);
        add(this.temporalPieceTile);
        revalidate();
        repaint();
    }

    public void removeTemporalTile() {
        if (temporalPieceTile != null)
            remove(temporalPieceTile);
        temporalPieceTile = null;
        add(this.tile);
        revalidate();
        repaint();
    }

    /**
     * Retorna la fitxa que conté la cel·la.
     *
     * @return la fitxa actual de la cel·la, o null si no en té
     */
    public BoardTile getTile() {
        if (temporalPieceTile != null)
            return temporalPieceTile;
        return tile;
    }
}
