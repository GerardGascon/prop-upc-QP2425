package edu.upc.prop.scrabble.presenter.swing.screens.game;

import javax.swing.*;
import java.awt.*;

public class GameMock extends JPanel {
    private final int BOARD_SIZE = 21;
    private final int BOARD_SPACING = 3;
    private final float BOARD_VERTICAL_SIZE_PERCENTAGE = 0.8f;
    private final float BOARD_HORIZONTAL_OFFSET_PERCENTAGE = 0.4f;

    private final int HAND_PIECES_COUNT = 7;
    private final int HAND_PIECES_SIZE_SCALE = 2;
    private final int HAND_PIECES_SPACING = 10;

    private final float SIDE_PANEL_WIDTH_PERCENTAGE = 0.25f;
    private final int NUM_USER_SECTIONS = 4;
    private final float USER_SECTION_WIDTH_PERCENTAGE = 0.8f;
    private final float USER_SECTION_HEIGHT_PERCENTAGE = 0.2f;
    private final float USER_PICTURE_SIZE_PERCENTAGE = 0.4f;

    public GameMock() {
        setBackground(new Color(0x50, 0x84, 0x6e));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        drawBaseGrid(g);
        drawHandPieces(g);
        drawSidePanel(g);
    }

    private void drawSidePanel(Graphics g) {
        int rectangleWidth = (int)(getWidth() * SIDE_PANEL_WIDTH_PERCENTAGE);
        g.setColor(new Color(0x2e, 0x3a, 0x3c));
        g.fillRect(0, 0, rectangleWidth, getHeight());

        drawPlayerInfo(g, getHeight(), rectangleWidth);
    }

    private void drawPlayerInfo(Graphics g, int panelHeight, int rectangleWidth) {
        for (int i = 0; i < NUM_USER_SECTIONS; i++) {
            int userSectionWidth = (int)(rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
            int userSectionHeight = (int)(panelHeight * USER_SECTION_HEIGHT_PERCENTAGE);

            int sectionY = i * (panelHeight / NUM_USER_SECTIONS);

            int sectionX = (rectangleWidth - userSectionWidth) / 2;

            g.setColor(Color.WHITE);
            g.fillRect(sectionX, sectionY, userSectionWidth, userSectionHeight);

            int userPictureSize = (int)(userSectionWidth * USER_PICTURE_SIZE_PERCENTAGE);

            int userPictureX = sectionX + (userSectionWidth - userPictureSize) / 2;
            int userPictureY = sectionY + 10;
            g.setColor(Color.GRAY);
            g.fillRect(userPictureX, userPictureY, userPictureSize, userPictureSize);

            String name = "User " + (i + 1);
            g.setColor(Color.BLACK);
            g.drawString(name, userPictureX + (userPictureSize - g.getFontMetrics().stringWidth(name)) / 2, userPictureY + userPictureSize + 20);

            String score = "Score: " + (i * 100);
            g.drawString(score, userPictureX + (userPictureSize - g.getFontMetrics().stringWidth(score)) / 2, userPictureY + userPictureSize + 40);
        }
    }

    private void drawHandPieces(Graphics g) {
        int boardSize = (int)(getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int)(getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 2;

        int extraRowY = yOffset + boardSize + 20; // Position below the board
        int extraSquareSize = (boardSize / BOARD_SIZE) * HAND_PIECES_SIZE_SCALE; // Make extra squares larger (double the size of board squares)

        int totalExtraRowWidth = (HAND_PIECES_COUNT * extraSquareSize) + ((HAND_PIECES_COUNT - 1) * HAND_PIECES_SPACING);

        int extraRowX = xOffset + (boardSize - totalExtraRowWidth) / 2;

        for (int i = 0; i < HAND_PIECES_COUNT; i++) {
            int xPos = extraRowX + (i * (extraSquareSize + HAND_PIECES_SPACING));
            g.setColor(Color.WHITE);
            g.fillRoundRect(xPos, extraRowY, extraSquareSize, extraSquareSize, 16, 16);
        }
    }

    private void drawBaseGrid(Graphics g) {
        int boardSize = (int)(getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int)(getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 2;

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                g.setColor(new Color(0x56, 0xa8, 0x87));
                g.fillRoundRect(xOffset + (i * (boardSize / BOARD_SIZE)) + BOARD_SPACING,
                        yOffset + (j * (boardSize / BOARD_SIZE)) + BOARD_SPACING,
                        boardSize / BOARD_SIZE - BOARD_SPACING * 2,
                        boardSize / BOARD_SIZE - BOARD_SPACING * 2,
                        16,
                        16);
            }
        }
    }
}
