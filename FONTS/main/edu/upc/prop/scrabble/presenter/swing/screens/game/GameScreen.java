package edu.upc.prop.scrabble.presenter.swing.screens.game;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.BlankPieceSelector;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private final float SIDE_PANEL_WIDTH_PERCENTAGE = 0.25f;
    private final float BOARD_VERTICAL_SIZE_PERCENTAGE = 0.8f;
    private final float BOARD_HORIZONTAL_OFFSET_PERCENTAGE = 0.4f;

    private BoardView boardView;
    private SidePanel sidePanel;
    private HandView handView;
    private BlankPieceSelector blankPieceSelector;

    public GameScreen() {
        setLayout(new BorderLayout());
        setBackground(new Color(0x50, 0x84, 0x6e));
    }

    public void addBoard(BoardView boardView) {
        add(boardView);
        this.boardView = boardView;
    }

    public void addSidePanel(SidePanel sidePanel) {
        add(sidePanel);
        this.sidePanel = sidePanel;
    }

    public void addHandView(HandView handView) {
        add(handView);
        this.handView = handView;
    }

    public void addBlankPieceSelector(BlankPieceSelector blankPieceSelector) {
        add(blankPieceSelector);
        setComponentZOrder(blankPieceSelector, 0);
        this.blankPieceSelector = blankPieceSelector;
    }

    @Override
    public void doLayout() {
        super.doLayout();

        putBoard();
        putSidePanel();
        putHandView();
        putBlankPieceSelector();
    }

    private void putBoard() {
        int boardSize = (int) (getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int) (getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int yOffset = (getHeight() - boardSize) / 4;

        boardView.setBounds(xOffset, yOffset, boardSize, boardSize);
    }

    private void putSidePanel() {
        int sidePanelWidth = (int)(getWidth() * SIDE_PANEL_WIDTH_PERCENTAGE);
        sidePanel.setBounds(0, 0, sidePanelWidth, getHeight());
    }

    private void putHandView() {
        int boardSize = (int) (getHeight() * BOARD_VERTICAL_SIZE_PERCENTAGE);
        int xOffset = (int) (getWidth() * BOARD_HORIZONTAL_OFFSET_PERCENTAGE);
        int boardVerticalMargin = (getHeight() - boardSize) / 2;

        int margin = (int) (boardVerticalMargin * 0.05f);

        int extraRowY = boardVerticalMargin + boardSize + margin;
        int handPieceSize = (int) (boardVerticalMargin * 0.9f);

        int totalExtraRowWidth = (7 * handPieceSize) + (7 * HandView.HAND_PIECES_SPACING);

        int extraRowX = xOffset + (boardSize - totalExtraRowWidth) / 2;

        handView.setBounds(extraRowX, extraRowY, totalExtraRowWidth, handPieceSize);
        handView.setPieceSize(handPieceSize);
    }

    private void putBlankPieceSelector() {
        blankPieceSelector.setBounds(0, 0, getWidth(), getHeight());
    }
}
