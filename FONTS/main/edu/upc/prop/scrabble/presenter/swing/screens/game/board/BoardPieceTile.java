package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class BoardPieceTile extends BoardTile {
    public BoardPieceTile() {
        super();
        setBackground(Color.white);
    }

    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        drawLetter(g);
        drawScore(g);
    }

    private void drawLetter(Graphics2D g) {
        String letter = "L·L";
        g.setColor(Color.BLACK);

        AffineTransform at = new AffineTransform();
//        at.scale(1, 1);
        at.scale(0.7, 1);

        Font font = new Font("SansSerif", Font.BOLD, (int)(getHeight() * 0.7)).deriveFont(at);
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(letter);
        int textHeight = metrics.getAscent();

        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - metrics.getDescent();
        g.drawString(letter, x, y);
    }

    private void drawScore(Graphics2D g) {
        String score = Integer.toString(0);
        g.setColor(new Color(0xf5, 0x2e, 0x2e));

        Font font = new Font("SansSerif", Font.BOLD, (int)(getHeight() * 0.25));
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(score);

        int x = getWidth() - textWidth;
        int y = getHeight() - metrics.getDescent();
        g.drawString(score, x, y);
    }
}
