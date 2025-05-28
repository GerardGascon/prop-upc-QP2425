package edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel;

import edu.upc.prop.scrabble.data.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Component gràfic que s'encarrega de resaltar visualment el jugador actual.
 * <p>
 * Aquesta classe dibuixa un marc rodó de color al voltant del jugador que té el torn,
 * utilitzant colors diferents per a cada jugador per facilitar la identificació visual.
 * </p>
 *
 * @author Albert Usero
 */
public class PlayerHighlight extends JPanel {

    /**
     * Dibuixa el resaltat del jugador actual al panell.
     *
     * @param g Context gràfic on es dibuixarà el resaltat
     * @param panelHeight Alçada total del panell contenidor
     * @param rectangleWidth Amplada disponible per al resaltat
     * @param selectedPlayer Índex del jugador seleccionat (0-3)
     * @param numberOfPlayers Nombre total de jugadors participants
     */
    public void drawPlayerHighlight(Graphics g, int panelHeight, int rectangleWidth,
                                    int selectedPlayer, int numberOfPlayers) {
        //lo mismo que player info para alinear
        int USER_MARGIN = (int) (rectangleWidth * 0.01f);
        panelHeight -= USER_MARGIN * 15;
        int NUM_USER_SECTIONS = numberOfPlayers;
        float USER_SECTION_WIDTH_PERCENTAGE = 0.85f;

        int userSectionWidth = (int)(rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
        int userSectionHeight = (int) (((float) panelHeight / NUM_USER_SECTIONS - USER_MARGIN * 2) * 0.95f);
        int sectionY = selectedPlayer * (panelHeight / NUM_USER_SECTIONS) + USER_MARGIN * 2;
        int sectionX = (rectangleWidth - userSectionWidth) / 2;

        setPlayerColor(g, selectedPlayer);
        //margen
        int highlightMargin = (int) (panelHeight * 0.01f);

        g.fillRoundRect(
                sectionX - highlightMargin,
                (int) (sectionY + panelHeight * 0.024f) - highlightMargin,
                userSectionWidth + highlightMargin * 2,
                (int) (userSectionHeight + panelHeight * 0.0148f) + highlightMargin * 2,
                (int) (panelHeight * 0.07111),
                (int) (panelHeight * 0.07111)
        );
    }

    /**
     * Assigna el color del resaltat segons el jugador seleccionat.
     * <p>
     * Colors assignats:
     * <ul>
     *   <li>Jugador 0: Vermell (#f52e2e)</li>
     *   <li>Jugador 1: Blau (#5463ff)</li>
     *   <li>Jugador 2: Groc (#ffc717)</li>
     *   <li>Jugador 3: Verd (#43e81f)</li>
     * </ul>
     *
     * @param g Context gràfic on s'aplicarà el color
     * @param selectedPlayer Índex del jugador seleccionat (0-3)
     */
    private void setPlayerColor(Graphics g, int selectedPlayer) {
        switch (selectedPlayer) {
            case 0 -> g.setColor(new Color(0xf5, 0x2e, 0x2e));  // Vermell
            case 1 -> g.setColor(new Color(0x54, 0x63, 0xff));  // Blau
            case 2 -> g.setColor(new Color(0xff, 0xc7, 0x17));  // Groc
            case 3 -> g.setColor(new Color(67, 232, 31));       // Verd
        }
    }
}