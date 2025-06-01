package edu.upc.prop.scrabble.presenter.swing.screens.game.sidepanel;

import edu.upc.prop.scrabble.data.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Panell lateral que mostra informació dels jugadors durant la partida.
 * <p>
 * Aquest component gràfic s'encarrega de visualitzar:
 * <ul>
 *   <li>El jugador actual (resaltat)</li>
 *   <li>Les puntuacions i noms dels jugadors</li>
 * </ul>
 * Utilitza PlayerHighlight i PlayerInfo per renderitzar els elements.
 *
 * @author Albert Usero
 */
public class SidePanel extends JPanel {
    /**
     * Component gràfic que s'encarrega de resaltar visualment el jugador actual.
     */
    private final PlayerHighlight playerHighlight = new PlayerHighlight();
    /**
     * Component gràfic que mostra la informació dels jugadors.
     */
    private final PlayerInfo playerInfo = new PlayerInfo();
    /**
     * Índex del jugador actual (0-3).
     */
    private int currentPlayer = 0; //ir cambianod esto para cambiar el player;
    /**
     * Llista immutable dels jugadors participants.
     * <p>
     * S'inicialitza al constructor i no pot modificar-se posteriorment.
     * Cada element conté les dades d'un jugador (nom, puntuació, etc.).
     * </p>
     */
    private final Player[] players;
    /**
     * Constructor que inicialitza el panell amb la llista de jugadors.
     * @param players Llista de jugadors que participen en la partida.
     */
    public SidePanel(Player[] players) {
        setOpaque(false);
        this.players = players;
    }
    /**
     * Canvia el jugador actual i actualitza la visualització.
     *
     * @param player Índex del nou jugador actual (entre 0 i 3).
     *               Si el valor és invàlid, no es realitza cap canvi.
     */
    public void setCurrentPlayer(int player) {
        if (player >= 0 && player < 4) {
            this.currentPlayer = player;
            repaint();//despues de cambiarlo volverlo a pintar
            //para que se vea el cambio
        }
    }
    /**
     * Sobreescriptura del mètode de pintat per personalitzar l'aparença del panell.
     * <p>
     * S'encarrega de:
     * <ul>
     *   <li>Pintar el fons amb un color fosc.</li>
     *   <li>Renderitzar el resaltat del jugador actual.</li>
     *   <li>Mostrar la informació dels jugadors.</li>
     * </ul>
     *
     * @param g Context gràfic utilitzat per al pintat.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(new Color(0x2e, 0x3a, 0x3c));
        g2.fillRect(0, 0, getWidth(), getHeight());
        playerHighlight.drawPlayerHighlight(g2, getHeight(), getWidth(), currentPlayer, players.length);
        playerInfo.drawPlayerInfo(g2, getHeight(), getWidth(), players);
        g2.dispose();
    }
}