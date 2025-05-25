package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.place;

import javax.swing.*;
import java.awt.*;
/**
 * Classe per gestionar el disseny del botó de Place (Posar) en una partida.
 * @author Gina Escofet González
 */
public class PlaceActionPanel extends JPanel {
    /***
     * Construeix un nou `PlaceActionPanel` amb el gestor de disseny especificat.
     * @param layout El `LayoutManager` que s'utilitzarà per a aquest panell.
     */
    public PlaceActionPanel(LayoutManager layout) {
        super(layout);
    }
    /**
     * Personalitza el dibuix del component pintant un rectangle rodó negre
     * amb qualitat antialiasing per suavitzar les vores i el text.
     * @param g El context gràfic on es fa el dibuix.
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

