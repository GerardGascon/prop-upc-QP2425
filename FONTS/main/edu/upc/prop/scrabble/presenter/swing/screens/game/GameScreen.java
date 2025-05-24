package edu.upc.prop.scrabble.presenter.swing.screens.game;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel.SidePanel;

import javax.swing.*;
import java.awt.*;

public class GameScreen extends JPanel {
    private final float SIDE_PANEL_WIDTH_PERCENTAGE = 0.25f;
    private final float BOARD_VERTICAL_SIZE_PERCENTAGE = 0.8f;
    private final float BOARD_HORIZONTAL_OFFSET_PERCENTAGE = 0.4f;

    private BoardView boardView;
    private SidePanel sidePanel;

    public GameScreen() {
        setLayout(new BorderLayout());
    }

    public void addBoard(BoardView boardView) {
        add(boardView);
        this.boardView = boardView;
    }

    public void addSidePanel(SidePanel sidePanel) {
        add(sidePanel);
        this.sidePanel = sidePanel;
    }

    @Override
    public void doLayout() {
        super.doLayout();

        putBoard();
        putSidePanel();
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
}
