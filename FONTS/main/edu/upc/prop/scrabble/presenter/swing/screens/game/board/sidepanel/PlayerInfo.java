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

    // Constants de marge i proporcions
    private static final float MARGIN_PERCENTAGE = 0.01f;
    private static final int MARGIN_MULTIPLIER = 15;
    private static final float USER_SECTION_WIDTH_PERCENTAGE = 0.85f;
    private static final float USER_SECTION_HEIGHT_PERCENTAGE = 0.95f;

    // Constants de posicionament vertical
    private static final float CARD_VERTICAL_OFFSET = 0.024f;
    private static final float CARD_HEIGHT_ADJUSTMENT = 0.0148f;

    // Constants de corners arrodonits
    private static final float CARD_CORNER_RADIUS = 0.07111f;

    // Constants de la barra de color
    private static final float COLOR_BAR_MARGIN = 0.02963f;
    private static final float COLOR_BAR_HEIGHT_DIVISOR = 2.0f;
    private static final float COLOR_BAR_HEIGHT_REDUCTION = 0.02963f;
    private static final float COLOR_BAR_CORNER_RADIUS = 0.04148f;

    // Constants de text
    private static final int MAX_NAME_LENGTH = 10;
    private static final float NAME_FONT_DIVISOR = 4.0f;
    private static final float NAME_FONT_SCALE_FACTOR = 9.0f;
    private static final float SCORE_FONT_DIVISOR = 2.5f;

    // Constants de posicionament del nom
    private static final float NAME_Y_MULTIPLIER_4_PLAYERS = 0.8f;
    private static final float NAME_Y_MULTIPLIER_3_PLAYERS = 0.75f;
    private static final float NAME_Y_MULTIPLIER_DEFAULT = 0.65f;

    // Constants de posicionament de la puntuació
    private static final float SCORE_Y_POSITION = 0.7f;
    private static final float SCORE_Y_OFFSET_DIVISOR = 2.0f;

    // Colors dels jugadors
    private static final Color PLAYER_COLOR_RED = new Color(0xf5, 0x2e, 0x2e);
    private static final Color PLAYER_COLOR_BLUE = new Color(0x54, 0x63, 0xff);
    private static final Color PLAYER_COLOR_YELLOW = new Color(0xff, 0xc7, 0x17);
    private static final Color PLAYER_COLOR_GREEN = new Color(67, 232, 31);

    /**
     * Dibuixa la informació de tots els jugadors al panell.
     *
     * @param g Context gràfic on es dibuixarà la informació
     * @param panelHeight Alçada total del panell contenidor
     * @param rectangleWidth Amplada disponible per a les targetes
     * @param players Llista de jugadors amb la seva informació
     */
    public void drawPlayerInfo(Graphics g, int panelHeight, int rectangleWidth, Player[] players) {

        int userMargin = (int) (rectangleWidth * MARGIN_PERCENTAGE);
        panelHeight -= userMargin * MARGIN_MULTIPLIER;
        int numUserSections = players.length;

        for (int i = 0; i < numUserSections; i++) {
            // Càlcul de dimensions per a cada targeta de jugador
            int userSectionWidth = (int)(rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
            int userSectionHeight = (int) (((float) panelHeight / numUserSections - userMargin * 2) * USER_SECTION_HEIGHT_PERCENTAGE);
            int sectionY = i * (panelHeight / numUserSections) + userMargin * 2;
            int sectionX = (rectangleWidth - userSectionWidth) / 2;

            // Dibuix de la targeta base
            drawPlayerCard(g, panelHeight, sectionX, sectionY, userSectionWidth, userSectionHeight);

            // Dibuix de la barra de color identificativa
            drawColorBar(g, panelHeight, sectionX, sectionY, userSectionWidth, userSectionHeight, i);

            // Renderització del nom i puntuació del jugador
            drawPlayerName(g, players[i].getName(), sectionX, sectionY, userSectionWidth, userSectionHeight, numUserSections);
            drawPlayerScore(g, players[i].getScore(), sectionX, sectionY, userSectionWidth, userSectionHeight);
        }
    }

    /**
     * Dibuixa la targeta base del jugador.
     */
    private void drawPlayerCard(Graphics g, int panelHeight, int sectionX, int sectionY,
                                int userSectionWidth, int userSectionHeight) {
        g.setColor(Color.WHITE);
        int cardY = (int) (sectionY + panelHeight * CARD_VERTICAL_OFFSET);
        int cardHeight = (int) (userSectionHeight + panelHeight * CARD_HEIGHT_ADJUSTMENT);
        int cornerRadius = (int) (panelHeight * CARD_CORNER_RADIUS);

        g.fillRoundRect(sectionX, cardY, userSectionWidth, cardHeight, cornerRadius, cornerRadius);
    }

    /**
     * Dibuixa la barra de color identificativa del jugador.
     */
    private void drawColorBar(Graphics g, int panelHeight, int sectionX, int sectionY,
                              int userSectionWidth, int userSectionHeight, int playerIndex) {
        setPlayerColor(g, playerIndex);

        int colorBarMargin = (int) (panelHeight * COLOR_BAR_MARGIN);
        int colorRectHeight = (int) ((double) userSectionHeight / COLOR_BAR_HEIGHT_DIVISOR - panelHeight * COLOR_BAR_HEIGHT_REDUCTION);
        int colorBarX = sectionX + colorBarMargin;
        int colorBarY = sectionY + colorBarMargin;
        int colorBarWidth = userSectionWidth - colorBarMargin * 2;
        int colorBarCornerRadius = (int) (panelHeight * COLOR_BAR_CORNER_RADIUS);

        g.fillRoundRect(colorBarX, colorBarY, colorBarWidth, colorRectHeight, colorBarCornerRadius, colorBarCornerRadius);
    }

    /**
     * Dibuixa el nom del jugador.
     */
    private void drawPlayerName(Graphics g, String name, int sectionX, int sectionY,
                                int userSectionWidth, int userSectionHeight, int numUserSections) {
        g.setColor(Color.BLACK);

        // Configuració de la font per al nom
        Font nameFont = calculateNameFont(name, userSectionHeight, numUserSections);
        g.setFont(nameFont);
        FontMetrics metrics = g.getFontMetrics(nameFont);

        // Posicionament del nom
        int nameWidth = metrics.stringWidth(name);
        int nameX = sectionX + (userSectionWidth - nameWidth) / 2;
        int nameY = calculateNameY(sectionY, userSectionHeight, numUserSections);

        g.drawString(name, nameX, nameY);
    }

    /**
     * Dibuixa la puntuació del jugador.
     */
    private void drawPlayerScore(Graphics g, int score, int sectionX, int sectionY,
                                 int userSectionWidth, int userSectionHeight) {
        // Configuració de la font per a la puntuació
        Font scoreFont = new Font("SansSerif", Font.BOLD, (int) (userSectionHeight / SCORE_FONT_DIVISOR));
        g.setFont(scoreFont);
        FontMetrics metrics = g.getFontMetrics(scoreFont);

        // Posicionament de la puntuació
        String scoreText = String.valueOf(score);
        int scoreWidth = metrics.stringWidth(scoreText);
        int scoreX = sectionX + (userSectionWidth - scoreWidth) / 2;
        int colorRectHeight = (int) ((double) userSectionHeight / COLOR_BAR_HEIGHT_DIVISOR);
        int scoreY = (int) (sectionY + (userSectionHeight * SCORE_Y_POSITION) + (double) colorRectHeight / SCORE_Y_OFFSET_DIVISOR);

        g.drawString(scoreText, scoreX, scoreY);
    }

    /**
     * Calcula la font apropiada per al nom del jugador.
     */
    private Font calculateNameFont(String name, int userSectionHeight, int numUserSections) {
        float nameFontDivisor = NAME_FONT_DIVISOR;


        if (numUserSections == 3) {
            nameFontDivisor = 5.f;
        }

        if (numUserSections == 2) {
            nameFontDivisor = 8.f;
        }

        int baseFontSize = (int) (userSectionHeight / nameFontDivisor);

        if (name.length() > MAX_NAME_LENGTH) {
            int adjustedFontSize = (int) (baseFontSize * (NAME_FONT_SCALE_FACTOR / name.length()));
            return new Font("SansSerif", Font.BOLD, adjustedFontSize);
        }

        return new Font("SansSerif", Font.BOLD, baseFontSize);
    }

    /**
     * Calcula la posició Y per al nom del jugador segons el nombre de jugadors.
     */
    private int calculateNameY(int sectionY, int userSectionHeight, int numUserSections) {
        int colorRectHeight = (int) ((double) userSectionHeight / COLOR_BAR_HEIGHT_DIVISOR);

        if (numUserSections == 4) {
            return (int) (sectionY + (colorRectHeight * NAME_Y_MULTIPLIER_4_PLAYERS));
        } else if (numUserSections == 3) {
            return (int) (sectionY + (colorRectHeight * NAME_Y_MULTIPLIER_3_PLAYERS));
        } else {
            return (int) (sectionY + (colorRectHeight * NAME_Y_MULTIPLIER_DEFAULT));
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
            case 0 -> g.setColor(PLAYER_COLOR_RED);
            case 1 -> g.setColor(PLAYER_COLOR_BLUE);
            case 2 -> g.setColor(PLAYER_COLOR_YELLOW);
            case 3 -> g.setColor(PLAYER_COLOR_GREEN);
        }
    }
}