package edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel;

import javax.swing.*;
import java.awt.*;

public class PlayerHighlight extends JPanel {

    public void drawPlayerHighlight(Graphics g, int panelHeight, int rectangleWidth, int selectedPlayer) {
        int USER_MARGIN = 10;
        panelHeight -= USER_MARGIN * 2;
        float USER_SECTION_WIDTH_PERCENTAGE = 0.8f;
        int NUM_USER_SECTIONS = 4; //playersinfo.lenght()

        int userSectionWidth = (int)(rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);

        int userSectionHeight = panelHeight / NUM_USER_SECTIONS - USER_MARGIN * 2;

        int sectionY = selectedPlayer * (panelHeight / NUM_USER_SECTIONS) + USER_MARGIN * 2;

        int sectionX = (rectangleWidth - userSectionWidth) / 2;

        setPlayerColor(g, selectedPlayer);

        g.fillRoundRect(sectionX - 10, sectionY - 10, userSectionWidth + 20, userSectionHeight + 20, 58, 58);
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
