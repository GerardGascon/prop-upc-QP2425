package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IHandView;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class BoardTemporalPieceTile extends BoardTile {
    private final String letter;
    private final int points;

    public BoardTemporalPieceTile(String letter, int points, int x, int y, IHandView handView, BoardView boardView) {
        super(x, y, handView, boardView);

        this.letter = letter;
        this.points = points;

        setBackground(new Color(0xff, 0xf9, 0xb5, 0x80));
    }

    public String getLetter() {
        return letter;
    }

    public int getPoints() {
        return points;
    }

    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        drawLetter(g);
        drawScore(g);
    }

    private void drawLetter(Graphics2D g) {
        g.setColor(Color.BLACK);

        AffineTransform at = new AffineTransform();
        if (letter.length() > 1)
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
        String score = Integer.toString(points);
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
