package edu.upc.prop.scrabble.presenter.swing.screens.game;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class BoardTile extends JButton {
    protected int cornerRadius = 16;

    public BoardTile() {
        super();
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setBackground(new Color(0x56, 0xa8, 0x87));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        Color bgColor = getModel().isArmed() ? getBackground().darker() : getBackground();

        drawTile(g2, bgColor, cornerRadius);

        super.paintComponent(g);
        g2.dispose();
    }

    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
    }

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
