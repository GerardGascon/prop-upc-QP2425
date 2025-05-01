package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;
import edu.upc.prop.scrabble.utils.Vector2;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.RoundRectangle2D;

public class MenuButton extends JButton {
    private final String text;

    public MenuButton(String text) {
        super();
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        this.text = text;
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

        drawTile(g2, bgColor, getCornerRadius());

        super.paintComponent(g);
        g2.dispose();
    }

    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        drawLetter(g);
    }

    private void drawLetter(Graphics2D g) {
        g.setColor(Color.BLACK);

        Font font = new Font("SansSerif", Font.BOLD, (int)(getHeight() * 0.7));
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int textHeight = metrics.getAscent();

        int x = getWidth() / 20;
        int y = (getHeight() + textHeight) / 2 - metrics.getDescent();
        g.drawString(text, x, y);
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getCornerRadius(), getCornerRadius());
        return shape.contains(x, y);
    }

    private int getCornerRadius() {
        return getHeight() * 20 / 100 * 2;
    }
}
