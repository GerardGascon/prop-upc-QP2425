package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.draw;

import javax.swing.*;
import java.awt.*;
/**
 * Classe per gestionar el disseny del botó de Draw (Robar) en una partida.
 * @author Gina Escofet González
 */
public class DrawActionPanel extends JPanel {
    /**
     * Construeix un nou `DrawActionPanel` amb el gestor de disseny especificat.
     * @param layout El `LayoutManager` que s'utilitzarà per a aquest panell.
     */
    public DrawActionPanel (LayoutManager layout) {
        super(layout);
    }

    /**
     * S'encarrega de pintar el component amb un fons arrodonit de color negre.
     * També aplica suavitzat per millorar la qualitat visual dels gràfics.
     *
     * @param g L'objecte gràfic proporcionat pel sistema Swing per pintar.
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
     * Indica que el panell no és opac.
     * Això permet que el fons del panell sigui transparent i no tapi altres components.
     * @return fals, per permetre transparència visual.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
