package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public abstract class BoardTile extends JButton {
    protected int cornerRadius = 16;

    public BoardTile() {
        super();
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bgColor;
        if (model.isArmed())
            bgColor = getBackground().darker();
        else if (model.isRollover())
            bgColor = getBackground().brighter();
        else
            bgColor = getBackground();

        drawTile(g2, bgColor, cornerRadius);

        super.paintComponent(g);
        g2.dispose();
    }

    protected abstract void drawTile(Graphics2D g, Color bg, int radius);

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        return shape.contains(x, y);
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }
}
