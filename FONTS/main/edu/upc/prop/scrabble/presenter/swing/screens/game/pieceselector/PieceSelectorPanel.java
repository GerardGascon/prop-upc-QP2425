package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import javax.swing.*;
import java.awt.*;

/**
 * Panell personalitzat amb fons rodó i estil específic per al selector de peces.
 * Aquest panell no és opac i pinta un fons amb cantonades arrodonides i color personalitzat.
 *
 * @author Gerard Gascón
 */
class PieceSelectorPanel extends JPanel {

    /**
     * Crea un panell amb el LayoutManager indicat.
     *
     * @param layout LayoutManager que s'aplicarà al panell; pot ser null
     */
    public PieceSelectorPanel(LayoutManager layout) {
        super(layout);
    }

    /**
     * Pinta el panell amb un fons de cantonades arrodonides i color personalitzat.
     *
     * @param g Context gràfic per pintar
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.setColor(new Color(0x2e, 0x3a, 0x3c));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 58, 58);
        g2.dispose();
    }

    /**
     * Indica que aquest panell no és opac per permetre pintar fons personalitzats.
     *
     * @return false sempre
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
