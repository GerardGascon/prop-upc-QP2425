package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import javax.swing.*;
import java.awt.*;

public class BoardCoordinateTile extends JPanel {
    private final String letter;

    public BoardCoordinateTile(String coordinate) {
        setOpaque(false);
        setLayout(new BorderLayout());

        this.letter = coordinate;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawTile(g2, getBackground());

        super.paintComponent(g);
        g2.dispose();
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new Rectangle.Float(0, 0, getWidth(), getHeight());
        return shape.contains(x, y);
    }

    protected void drawTile(Graphics2D g, Color bg) {
        drawLetter(g);
    }

    private void drawLetter(Graphics2D g) {
        g.setColor(Color.BLACK);

        Font font = new Font("SansSerif", Font.BOLD, (int)(getHeight() * 0.7));
        g.setFont(font);

        FontMetrics metrics = g.getFontMetrics(font);
        int textWidth = metrics.stringWidth(letter);
        int textHeight = metrics.getAscent();

        int x = (getWidth() - textWidth) / 2;
        int y = (getHeight() + textHeight) / 2 - metrics.getDescent();
        g.drawString(letter, x, y);
    }
}
