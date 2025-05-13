package edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel;

import javax.swing.*;
import java.awt.*;

public class PlayerInfo extends JPanel {

    public void drawPlayerInfo(Graphics g, int panelHeight, int rectangleWidth) {
        int USER_MARGIN = 10;
        panelHeight -= USER_MARGIN * 2;
        int NUM_USER_SECTIONS = 4; //playersinfo.length()
        float USER_SECTION_WIDTH_PERCENTAGE = 0.8f;

        for (int i = 0; i < NUM_USER_SECTIONS; i++) {
            int userSectionWidth = (int)(rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
            int userSectionHeight = panelHeight / NUM_USER_SECTIONS - USER_MARGIN * 2;

            int sectionY = i * (panelHeight / NUM_USER_SECTIONS) + USER_MARGIN * 2;
            int sectionX = (rectangleWidth - userSectionWidth) / 2;

            g.setColor(Color.WHITE);
            g.fillRoundRect(sectionX, sectionY, userSectionWidth, userSectionHeight, 48, 48);

            setPlayerColor(g, i);
            int colorRectHeight = userSectionHeight / 2 - 20;
            g.fillRoundRect(sectionX + 20, sectionY + 20, userSectionWidth - 40, colorRectHeight, 28, 28);

            g.setColor(Color.BLACK);
            Font font = new Font("SansSerif", Font.BOLD, userSectionHeight / 4);
            g.setFont(font);
            FontMetrics metrics = g.getFontMetrics(font);

            String name = "Player " + (i + 1); //playersinfo[i].name
            int nameWidth = metrics.stringWidth(name);
            int nameX = sectionX  + ((userSectionWidth) - nameWidth) / 2;
            int nameY = sectionY  + ((colorRectHeight* 725)/ 1000) + (metrics.getAscent() / 2);
            g.drawString(name, nameX, nameY);

            String score = Integer.toString(i * 100); //playersinfo.getScore()
            int scoreWidth = metrics.stringWidth(score);
            int scoreX = sectionX + (userSectionWidth - scoreWidth) / 2;
            int scoreY = sectionY + (userSectionHeight * 190)/300 + colorRectHeight / 2;
            g.drawString(score, scoreX, scoreY);
        }
    }

    private void setPlayerColor(Graphics g, int selectedPlayer) {
        switch (selectedPlayer) {
            case 0 -> g.setColor(new Color(0xf5, 0x2e, 0x2e));
            case 1 -> g.setColor(new Color(0x54, 0x63, 0xff));
            case 2 -> g.setColor(new Color(0xff, 0xc7, 0x17));
            case 3 -> g.setColor(new Color(67, 232, 31));
        }
    }
}