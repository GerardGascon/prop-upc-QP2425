package edu.upc.prop.scrabble.presenter.swing.screens.game;

import javax.swing.*;
import java.awt.*;

public class GameMock extends JPanel {
    private final int HAND_PIECES_COUNT = 7;
    private final int HAND_PIECES_SPACING = 10;
    private final float HAND_PIECES_SIZE_PERCENTAGE = 0.08f;

    private final float SIDE_PANEL_WIDTH_PERCENTAGE = 0.25f;
    private final int NUM_USER_SECTIONS = 4;
    private final int USER_MARGIN = 10;
    private final float USER_SECTION_WIDTH_PERCENTAGE = 0.8f;

    private final int BOARD_SIZE = 21;
    private final float BOARD_VERTICAL_SIZE_PERCENTAGE = 0.8f;
    private final float BOARD_HORIZONTAL_OFFSET_PERCENTAGE = 0.4f;
    private JPanel boardPanel;

    public GameMock() {
        setLayout(null);
        setBackground(new Color(0x50, 0x84, 0x6e));
        createBoardGrid();
    }

    private void createBoardGrid() {
        boardPanel = new JPanel();
        boardPanel.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE, 2, 2));
        boardPanel.setBackground(new Color(0x50, 0x84, 0x6e));

        for (int i = 0; i < BOARD_SIZE * BOARD_SIZE; i++) {
            RoundedCornerButton cell = new RoundedCornerButton();
            cell.setBackground(new Color(0x56, 0xa8, 0x87));
            int row = i / BOARD_SIZE;
            int col = i % BOARD_SIZE;
            cell.addActionListener(_ -> System.out.println("Clicked cell: " + row + ", " + col));
            boardPanel.add(cell);
        }

        add(boardPanel);
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int boardSize = (int)(getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int)(getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 2;

        boardPanel.setBounds(xOffset, yOffset, boardSize, boardSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        super.paintComponent(g2);
        drawHandPieces(g2);
        drawSidePanel(g2);
    }

    private void drawSidePanel(Graphics g) {
        int rectangleWidth = (int)(getWidth() * SIDE_PANEL_WIDTH_PERCENTAGE);
        g.setColor(new Color(0x2e, 0x3a, 0x3c));
        g.fillRect(0, 0, rectangleWidth, getHeight());

        drawPlayerHighlight(g, getHeight(), rectangleWidth, 2);
        drawPlayerInfo(g, getHeight(), rectangleWidth);
    }

    private void drawPlayerHighlight(Graphics g, int panelHeight, int rectangleWidth, int selectedPlayer) {
        panelHeight -= USER_MARGIN * 2;

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
            case 3 -> g.setColor(new Color(0x1f, 0x9e, 0x40));
        }
    }

    private void drawPlayerInfo(Graphics g, int panelHeight, int rectangleWidth) {
        panelHeight -= USER_MARGIN * 2;
        for (int i = 0; i < NUM_USER_SECTIONS; i++) {
            int userSectionWidth = (int)(rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
            int userSectionHeight = panelHeight / NUM_USER_SECTIONS - USER_MARGIN * 2;

            int sectionY = i * (panelHeight / NUM_USER_SECTIONS) + USER_MARGIN * 2;

            int sectionX = (rectangleWidth - userSectionWidth) / 2;

            g.setColor(Color.WHITE);
            g.fillRoundRect(sectionX, sectionY, userSectionWidth, userSectionHeight, 48, 48);

            setPlayerColor(g, i);
            g.fillRoundRect(sectionX + 20, sectionY + 20, userSectionWidth - 40, userSectionHeight / 2 - 20, 28, 28);

            String name = "Player " + (i + 1);
            g.setColor(Color.BLACK);
            Font font = new Font("SansSerif", Font.BOLD, 50);
            g.setFont(font);
            g.drawString(name, sectionX + 20, sectionY + userSectionHeight / 2 + 50);

            FontMetrics metrics = g.getFontMetrics(font);
            String score = Integer.toString(i * 100);
            int textWidth = metrics.stringWidth(score);
            g.drawString(score, sectionX + userSectionWidth - 20 - textWidth, sectionY + userSectionHeight / 2 + 50);
        }
    }

    private void drawHandPieces(Graphics g) {
        int boardSize = (int)(getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int)(getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 2;

        int extraRowY = yOffset + boardSize + 20;
        int extraSquareSize = (int)(getHeight() * HAND_PIECES_SIZE_PERCENTAGE);

        int totalExtraRowWidth = (HAND_PIECES_COUNT * extraSquareSize) + ((HAND_PIECES_COUNT - 1) * HAND_PIECES_SPACING);

        int extraRowX = xOffset + (boardSize - totalExtraRowWidth) / 2;

        for (int i = 0; i < HAND_PIECES_COUNT; i++) {
            int xPos = extraRowX + (i * (extraSquareSize + HAND_PIECES_SPACING));
            g.setColor(Color.WHITE);
            g.fillRoundRect(xPos, extraRowY, extraSquareSize, extraSquareSize, 16, 16);
        }
    }
}
