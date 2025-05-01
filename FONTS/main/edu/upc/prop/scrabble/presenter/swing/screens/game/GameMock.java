package edu.upc.prop.scrabble.presenter.swing.screens.game;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.domain.board.PremiumTileTypeFiller;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMock extends JPanel {
    private final int HAND_PIECES_COUNT = 7;
    private final int HAND_PIECES_SPACING = 10;

    private final float SIDE_PANEL_WIDTH_PERCENTAGE = 0.25f;
    private final int NUM_USER_SECTIONS = 4;
    private final int USER_MARGIN = 10;
    private final float USER_SECTION_WIDTH_PERCENTAGE = 0.8f;

    private final Board board = new SuperBoard();
    private final float BOARD_VERTICAL_SIZE_PERCENTAGE = 0.8f;
    private final float BOARD_HORIZONTAL_OFFSET_PERCENTAGE = 0.4f;
    private final BoardView boardPanel;

    public GameMock() {
        setLayout(null);
        setBackground(new Color(0x50, 0x84, 0x6e));
        boardPanel = new BoardView(board.getSize(), null);
        add(boardPanel);

        createPauseButton();

        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, boardPanel);
        filler.run();
    }

    private void createPauseButton() {
        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "PAUSE_BUTTON");
        actionMap.put("PAUSE_BUTTON", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Escape key pressed!");
            }
        });
    }

    @Override
    public void doLayout() {
        super.doLayout();

        int boardSize = (int)(getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int)(getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 4;

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
            Font font = new Font("SansSerif", Font.BOLD, userSectionHeight / 9);
            FontMetrics metrics = g.getFontMetrics(font);
            g.setFont(font);
            g.drawString(name, sectionX + 20, sectionY + userSectionHeight / 2 + metrics.getHeight());

            String score = Integer.toString(i * 100);
            int textWidth = metrics.stringWidth(score);
            g.drawString(score, sectionX + userSectionWidth - 20 - textWidth, sectionY + userSectionHeight / 2 + metrics.getHeight());
        }
    }

    private void drawHandPieces(Graphics g) {
        int boardSize = (int)(getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int)(getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int boardVerticalMargin = (getHeight() - boardSize) / 2;

        int margin = (int)(boardVerticalMargin * 0.05f);

        int extraRowY = boardVerticalMargin + boardSize + margin;
        int handPieceSize = (int)(boardVerticalMargin * 0.9f);

        int totalExtraRowWidth = (HAND_PIECES_COUNT * handPieceSize) + ((HAND_PIECES_COUNT - 1) * HAND_PIECES_SPACING);

        int extraRowX = xOffset + (boardSize - totalExtraRowWidth) / 2;

        for (int i = 0; i < HAND_PIECES_COUNT; i++) {
            int xPos = extraRowX + (i * (handPieceSize + HAND_PIECES_SPACING));
            g.setColor(Color.WHITE);
            g.fillRoundRect(xPos, extraRowY, handPieceSize, handPieceSize, handPieceSize * 20 / 100 * 2, handPieceSize * 20 / 100 * 2);
        }
    }
}
