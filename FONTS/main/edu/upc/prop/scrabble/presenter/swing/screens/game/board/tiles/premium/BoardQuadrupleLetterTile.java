package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;

import java.awt.*;

public class BoardQuadrupleLetterTile extends BoardTile {
    public BoardQuadrupleLetterTile(int x, int y, IHandView handView, BoardView boardView) {
        super(x, y, handView, boardView);
        setBackground(new Color(30, 50, 152));
        createTooltip("Quadruple Letter Score");
    }

    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }
}
