package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.skip;

import javax.swing.*;
import java.awt.*;
/**
 * Classe per gestionar el disseny del botó de Skip (Saltar) en una partida.
 * @author Gina Escofet González
 */
public class SkipActionPanel extends JPanel {
    /***
     * Construeix un nou `SkipActionPanel` amb el gestor de disseny especificat.
     * @param layout El `LayoutManager` que s'utilitzarà per a aquest panell.
     */
    public SkipActionPanel (LayoutManager layout) {
        super(layout);
    }
    /**
     * Pinta el component amb un fons negre i cantonades arrodonides per a un estil personalitzat.
     * @param g L'objecte Graphics utilitzat per dibuixar el component.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.setColor(new Color(0x00, 0x00, 0x00));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 58, 58);
        g2.dispose();
    }
    /**
     * Indica que el panell no és opac per permetre transparències o dibuixos sota seu.
     * @return fals sempre.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
