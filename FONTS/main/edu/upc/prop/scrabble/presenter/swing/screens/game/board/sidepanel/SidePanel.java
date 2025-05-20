package edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel;

import edu.upc.prop.scrabble.data.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class SidePanel extends JPanel {
    private final PlayerHighlight playerHighlight = new PlayerHighlight();
    private final PlayerInfo playerInfo = new PlayerInfo();
    private int currentPlayer = 0; //ir cambianod esto para cambiar el player;
    private final ArrayList<Player> players;
    public SidePanel(ArrayList<Player> players) {
        setOpaque(false);
        this.players = players;
    }

    public void setCurrentPlayer(int player) {
        if (player >= 0 && player < 4) {
            this.currentPlayer = player;
            repaint();//despues de cambiarlo volverlo a pintar
            //para que se vea el cambio
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(new Color(0x2e, 0x3a, 0x3c));
        g2.fillRect(0, 0, getWidth(), getHeight());
        playerHighlight.drawPlayerHighlight(g2, getHeight(), getWidth(), currentPlayer, players.size());
        playerInfo.drawPlayerInfo(g2, getHeight(), getWidth(), players);
        g2.dispose();
    }
}