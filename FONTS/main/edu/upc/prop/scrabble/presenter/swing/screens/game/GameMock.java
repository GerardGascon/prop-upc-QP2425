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

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
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

    public GameMock() {
        setLayout(null);
        setBackground(new Color(0x50, 0x84, 0x6e));

        Player p = new Player("Player", true);
        p.addPiece(new Piece("A", 1));
        p.addPiece(new Piece("B", 2));
        p.addPiece(new Piece("C", 3));
        p.addPiece(new Piece("D", 4));
        p.addPiece(new Piece("#", 0, true));
        handPanel = new HandView(p);
        add(handPanel);

        boardPanel = new BoardView(board.getSize(), handPanel, this);
        add(boardPanel);

        // Create and add the side panel as a proper component
        sidePanel = new SidePanel();
        add(sidePanel);

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
}