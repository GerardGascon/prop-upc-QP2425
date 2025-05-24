package edu.upc.prop.scrabble.presenter.swing.screens.game;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PremiumTileTypeFiller;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.PieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.draw.DrawAction;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.PauseButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.function.Consumer;

public class GameMock extends JPanel implements IBlankPieceSelector {
    private final float SIDE_PANEL_WIDTH_PERCENTAGE = 0.25f;
    private final int NUM_USER_SECTIONS = 4;
    private final int USER_MARGIN = 10;
    private final float USER_SECTION_WIDTH_PERCENTAGE = 0.8f;

    private final Board board = new SuperBoard();
    private final float BOARD_VERTICAL_SIZE_PERCENTAGE = 0.8f;
    private final float BOARD_HORIZONTAL_OFFSET_PERCENTAGE = 0.4f;
    private final BoardView boardPanel;
    private final HandView handPanel;
    private final SidePanel sidePanel;
    private JPanel overlay;



    // Coses per a Pause Button
    private boolean menuActive = false;
    private JPanel settingsPanel;
    private JPanel saveConfirmPanel;
    private final JButton continueButton = new JButton("Continuar");
    private final JButton exitButton = new JButton("Salir");
    private final JButton yesSaveButton = new JButton("Sí");
    private final JButton noSaveButton = new JButton("No");

    public GameMock() {
        setLayout(null);
        setBackground(new Color(0x50, 0x84, 0x6e));

        ArrayList<Player> playersList = new ArrayList<>();

        Player p = new Player("BOT", true);
        p.addPiece(new Piece("A", 1));
        p.addPiece(new Piece("B", 2));
        p.addPiece(new Piece("C", 3));
        p.addPiece(new Piece("D", 4));
        p.addPiece(new Piece("#", 0, true));
        handPanel = new HandView(false);
        handPanel.showPieces(p.getHand());
        add(handPanel);

        boardPanel = new BoardView(board.getSize(), handPanel, this);
        add(boardPanel);

        playersList.add(p);

        Player h = new Player("Adri", true);
        p.addPiece(new Piece("A", 1));
        p.addPiece(new Piece("B", 2));
        p.addPiece(new Piece("C", 3));
        p.addPiece(new Piece("D", 4));
        p.addPiece(new Piece("#", 0, true));
        handPanel.showPieces(p.getHand());
        add(handPanel);
        add(boardPanel);

        playersList.add(h);
        // Create and add the side panel as a proper component
        sidePanel = new SidePanel(playersList.toArray(Player[]::new));
        add(sidePanel);

        // En aquest moment exacte no tenim acces a getWidth i Heigth.
        // Invoke later s'asegura d'executarho mes tard, quan tinguem acces
        SwingUtilities.invokeLater(() -> {
            int width = getWidth();
            int height = getHeight();
            int xcoord = (int)(width * 0.3);
            int compWidth = (int)(width * 0.065);
            int compHeight = (int)(width * 0.035);
            createTurnActionButton("PUT", xcoord, (int)(height*0.6), compWidth, compHeight);

            createTurnActionButton("DRAW", xcoord, (int)(height*0.5), compWidth, compHeight);
            createTurnActionButton("SKIP", xcoord, (int)(height*0.4), compWidth, compHeight);

        });
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, boardPanel);

        // Create Pause button
        PauseButton pauseButton = new PauseButton(this);
        add(pauseButton);

        filler.run();
    }

    @Override
    public void doLayout() {
        super.doLayout();

        putSidePanel();
        putBoard();
        putHand();
    }

    private void putSidePanel() {
        int sidePanelWidth = (int)(getWidth() * SIDE_PANEL_WIDTH_PERCENTAGE);
        sidePanel.setBounds(0, 0, sidePanelWidth, getHeight());
    }

    private void putHand() {
        int boardSize = (int) (getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int) (getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int boardVerticalMargin = (getHeight() - boardSize) / 2;

        int margin = (int) (boardVerticalMargin * 0.05f);

        int extraRowY = boardVerticalMargin + boardSize + margin;
        int handPieceSize = (int) (boardVerticalMargin * 0.9f);

        int totalExtraRowWidth = (7 * handPieceSize) + (6 * HandView.HAND_PIECES_SPACING);

        int extraRowX = xOffset + (boardSize - totalExtraRowWidth) / 2;

        handPanel.setBounds(extraRowX, extraRowY, totalExtraRowWidth, handPieceSize);
        handPanel.setPieceSize(handPieceSize);
    }

    private void putBoard() {
        int boardSize = (int) (getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int) (getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 4;

        boardPanel.setBounds(xOffset, yOffset, boardSize, boardSize);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.dispose();
        // We don't need to call sidePanel.drawSidePanel(g) anymore because sidePanel is a proper component
    }

    @Override
    public void openSelectorPopUp(Consumer<String> selectPieceCallback) {
        if (overlay != null) {
            remove(overlay);
        }

        overlay = new PieceSelector(this, selectPieceCallback);
        add(overlay);
        setComponentZOrder(overlay, 0);
        revalidate();
        repaint();
    }

    private void drawSidePanel(Graphics g) {
        int rectangleWidth = (int) (getWidth() * SIDE_PANEL_WIDTH_PERCENTAGE);
        g.setColor(new Color(0x2e, 0x3a, 0x3c));
        g.fillRect(0, 0, rectangleWidth, getHeight());

        drawPlayerHighlight(g, getHeight(), rectangleWidth, 2);
        drawPlayerInfo(g, getHeight(), rectangleWidth);
    }

    private void drawPlayerHighlight(Graphics g, int panelHeight, int rectangleWidth, int selectedPlayer) {
        panelHeight -= USER_MARGIN * 2;

        int userSectionWidth = (int) (rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
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
            int userSectionWidth = (int) (rectangleWidth * USER_SECTION_WIDTH_PERCENTAGE);
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

    private void createTurnActionButton(String text, int x, int y, int width, int height) {
        JButton putButton = new JButton(text);
        putButton.setFocusPainted(false);
        putButton.setBackground(new Color(0xbc, 0xbc, 0xbc));
        putButton.setForeground(Color.BLACK);
        putButton.setBorder(BorderFactory.createEmptyBorder());
        putButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        putButton.setBounds(x, y, width, height);

        putButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(text + " button clicked!");
                // cridar a putActionMaker
            }
        });
        add(putButton);

    }
}