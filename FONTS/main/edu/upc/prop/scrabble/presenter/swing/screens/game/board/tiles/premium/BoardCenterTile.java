package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.utils.Tooltip;

import javax.swing.*;
import javax.swing.plaf.basic.BasicToolTipUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

public class BoardCenterTile extends BoardTile {
    public BoardCenterTile(int x, int y, IHandView handView) {
        super(x, y, handView);
        setBackground(new Color(0xff, 0xc0, 0xcb));
        createTooltip("Start here (Double Word Score)");
    }

    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        int[] xPoly = {0, getWidth() / 2 - getWidth() / 8, getWidth() / 2, getWidth() / 2 + getWidth() / 8, getWidth(), getWidth() / 2 + getWidth() / 8, getWidth() / 2, getWidth() / 2 - getWidth() / 8, 0};
        int[] yPoly = {getHeight() / 2, getHeight() / 2 - getHeight() / 8, 0, getHeight() / 2 - getHeight() / 8, getHeight() / 2, getHeight() / 2 + getHeight() / 8, getHeight(), getHeight() / 2 + getHeight() / 8, getHeight() / 2};

        for (int i = 0; i < xPoly.length; i++) {
            xPoly[i] = (int)(xPoly[i] / 1.5f) + (int)(getWidth() - getWidth() / 1.5f) / 2;
            yPoly[i] = (int)(yPoly[i] / 1.5f) + (int)(getHeight() - getHeight() / 1.5f) / 2;
        }

        g.setColor(new Color(0x80, 0x00, 0x00));
        g.fillPolygon(xPoly, yPoly, xPoly.length);
    }
}
