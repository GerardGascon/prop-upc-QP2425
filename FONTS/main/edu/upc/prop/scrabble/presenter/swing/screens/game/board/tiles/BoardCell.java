package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import javax.swing.*;
import java.awt.*;

/**
 * Casella del tauler que conté una fitxa {@link BoardTile}.
 * <p>
 * Aquesta classe representa una cel·la gràfica dins del tauler del joc.
 * Pot contenir una fitxa estàtica o una fitxa temporal (en cas de moviment provisional).
 * </p>
 * La cel·la pot mostrar o substituir fitxes, i diferenciar entre fitxes temporals
 * i definitives segons el tipus d'objecte {@link BoardTile}.
 *
 * @author Gerard Gascón
 */
public class BoardCell extends JPanel {

    /**
     * Fitxa principal assignada a la cel·la.
     */
    private BoardTile tile;

    /**
     * Fitxa temporal que es mostra provisionalment a la cel·la.
     */
    private BoardTemporalPieceTile temporalPieceTile;

    /**
     * Crea una nova cel·la del tauler sense cap fitxa assignada.
     * Configura el layout i el fons com a transparent.
     */
    public BoardCell() {
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    /**
     * Assigna una nova fitxa a la cel·la.
     * Si es tracta d'una fitxa temporal, es gestiona com a tal.
     * Si ja hi havia una fitxa, s'elimina abans d'afegir la nova.
     *
     * @param tile la nova fitxa a afegir a la cel·la
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

    /**
     * Assigna una fitxa temporal a la cel·la substituint la fitxa principal.
     *
     * @param temporalPieceTile fitxa temporal a mostrar
     */
    private void setTemporalTile(BoardTemporalPieceTile temporalPieceTile) {
        this.temporalPieceTile = temporalPieceTile;
        remove(this.tile);
        add(this.temporalPieceTile);
        revalidate();
        repaint();
    }

    /**
     * Elimina la fitxa temporal (si existeix) i restaura la fitxa principal.
     */
    public void removeTemporalTile() {
        if (temporalPieceTile != null)
            remove(temporalPieceTile);
        temporalPieceTile = null;
        add(this.tile);
        revalidate();
        repaint();
    }

    /**
     * Retorna la fitxa actual de la cel·la.
     * Si hi ha una fitxa temporal, aquesta té prioritat sobre la principal.
     *
     * @return la fitxa actual de la cel·la, o {@code null} si la cel·la està buida
     */
    public BoardTile getTile() {
        if (temporalPieceTile != null)
            return temporalPieceTile;
        return tile;
    }
}
