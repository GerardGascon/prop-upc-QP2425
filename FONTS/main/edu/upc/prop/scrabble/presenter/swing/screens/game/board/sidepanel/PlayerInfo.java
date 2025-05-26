package edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel;

import edu.upc.prop.scrabble.data.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Component gràfic que mostra la informació dels jugadors durant la partida.
 * <p>
 * Aquesta classe s'encarrega de renderitzar visualment:
 * <ul>
 *   <li>El nom de cada jugador</li>
 *   <li>La seva puntuació actual</li>
 *   <li>Un indicador de color que identifica a cada jugador</li>
 * </ul>
 * Els elements es mostren en targetes individuals amb disseny arrodonit.
 *
 * @author Albert Usero
 */
public class PlayerInfo extends JPanel {

    /**
     * Dibuixa la informació de tots els jugadors al panell.
     *
     * @param g Context gràfic on es dibuixarà la informació
     * @param panelHeight Alçada total del panell contenidor
     * @param rectangleWidth Amplada disponible per a les targetes
     * @param players Llista de jugadors amb la seva informació
     */
    public void drawPlayerInfo(Graphics g, int panelHeight, int rectangleWidth, Player[] players) {
        // Marges adaptatius basats en la mida de la pantalla
        int USER_MARGIN = (int) (10 * (getWidth() / 1920.0));
        panelHeight -= USER_MARGIN * 2;
        int NUM_USER_SECTIONS = players.length;
        float USER_SECTION_WIDTH_PERCENTAGE = 0.8f;

        for (int i = 0; i < NUM_USER_SECTIONS; i++) {
            // Càlcul de dimensions per a cada targeta de jugador
            int userSectionWidth = (int)(rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
            int userSectionHeight = panelHeight / NUM_USER_SECTIONS - USER_MARGIN * 2;
            int sectionY = i * (panelHeight / NUM_USER_SECTIONS) + USER_MARGIN * 2;
            int sectionX = (rectangleWidth - userSectionWidth) / 2;

            // Dibuix de la targeta base
            g.setColor(Color.WHITE);
            g.fillRoundRect(sectionX, sectionY, userSectionWidth, userSectionHeight, 48, 48);

            // Dibuix de la barra de color identificativa
            setPlayerColor(g, i);
            int colorRectHeight = userSectionHeight / 2 - 20;
            g.fillRoundRect(sectionX + 20, sectionY + 20, userSectionWidth - 40, colorRectHeight, 28, 28);

            // Configuració de la font per al text
            g.setColor(Color.BLACK);
            Font font = new Font("SansSerif", Font.BOLD, userSectionHeight / 4);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);

            // Renderització del nom del jugador
            String name = players[i].getName();
            int nameWidth = metrics.stringWidth(name);
            int nameX = sectionX + ((userSectionWidth) - nameWidth) / 2;
            int nameY = sectionY + ((colorRectHeight * 725)/1000) + (metrics.getAscent() / 2);
            g.drawString(name, nameX, nameY);

            // Renderització de la puntuació
            String score = String.valueOf(players[i].getScore());
            int scoreWidth = metrics.stringWidth(score);
            int scoreX = sectionX + (userSectionWidth - scoreWidth) / 2;
            int scoreY = sectionY + (userSectionHeight * 190)/300 + colorRectHeight / 2;
            g.drawString(score, scoreX, scoreY);
        }
    }

    /**
     * Assigna el color identificatiu per a cada jugador.
     * <p>
     * Els colors segueixen el mateix patró que en PlayerHighlight per consistència:
     * <ul>
     *   <li>Jugador 0: Vermell (#f52e2e)</li>
     *   <li>Jugador 1: Blau (#5463ff)</li>
     *   <li>Jugador 2: Groc (#ffc717)</li>
     *   <li>Jugador 3: Verd (#43e81f)</li>
     * </ul>
     *
     * @param g Context gràfic on s'aplicarà el color
     * @param selectedPlayer Índex del jugador (0-3)
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