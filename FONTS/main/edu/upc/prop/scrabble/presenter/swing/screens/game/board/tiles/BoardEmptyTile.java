package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;

import java.awt.*;

public class BoardEmptyTile extends BoardTile {
    public BoardEmptyTile(int x, int y, IHandView handView) {
        super(x, y, handView);
        setBackground(new Color(0x56, 0xa8, 0x87));
    }

    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}
