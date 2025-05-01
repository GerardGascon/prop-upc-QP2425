package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;

import java.awt.*;

public class BoardQuadrupleWordTile extends BoardTile {
    public BoardQuadrupleWordTile(int x, int y, IHandView handView) {
        super(x, y, handView);
        setBackground(new Color(0x80, 0x00, 0x00));
    }

    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}
