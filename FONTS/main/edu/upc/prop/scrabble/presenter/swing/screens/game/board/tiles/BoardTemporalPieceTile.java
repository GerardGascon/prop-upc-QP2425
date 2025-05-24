package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.geom.AffineTransform;

/**
 * Fitxa temporal amb lletra i punts del tauler.
 * <p>
 * Representa una peça que s'ha col·locat temporalment sobre el tauler,
 * mostrant la lletra i la seva puntuació amb transparència.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardTemporalPieceTile extends BoardTile {
    /**
     * Lletra representada per aquesta fitxa temporal.
     */
    private final String letter;

    /**
     * Punts associats a la lletra.
     */
    private final int points;

    /**
     * Crea una fitxa temporal amb lletra i punts a la posició (x, y).
     *
     * @param letter lletra de la fitxa
     * @param points punts de la fitxa
     * @param x coordenada x al tauler
     * @param y coordenada y al tauler
     * @param handView vista de la mà de fitxes
     * @param boardView vista del tauler
     * @param blankPieceSelector selector per fitxes en blanc
     */
    public BoardTemporalPieceTile(String letter, int points, int x, int y, IHandView handView, BoardView boardView, IBlankPieceSelector blankPieceSelector) {
        super(x, y, handView, boardView, blankPieceSelector);

        this.letter = letter;
        this.points = points;

        setBackground(new Color(0xff, 0xf9, 0xb5, 0x80));
    }

    /**
     * Retorna la lletra de la fitxa temporal.
     *
     * @return lletra de la fitxa
     */
    public String getLetter() {
        return letter;
    }

    /**
     * Retorna els punts de la fitxa temporal.
     *
     * @return punts de la fitxa
     */
    public int getPoints() {
        return points;
    }

    /**
     * Dibuixa la fitxa amb fons transparent, lletra i punts.
     *
     * @param g context gràfic 2D
     * @param bg color de fons (amb transparència)
     * @param radius radi de cantonades arrodonides
     */
    @Override
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        drawLetter(g);
        drawScore(g);
    }

    /**
     * Override per evitar cridar el mètode clic del pare.
     *
     * @param actionEvent esdeveniment d’acció
     */
    @Override
    protected void clicked(ActionEvent actionEvent) {
        // Used to avoid calling parent
    }

    /**
     * Dibuixa la lletra centrada a la fitxa temporal.
     * Aplica un escalat horitzontal si la lletra té més d’un caràcter.
     *
     * @param g context gràfic 2D
     */
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

    /**
     * Dibuixa la puntuació a la cantonada inferior dreta de la fitxa temporal.
     *
     * @param g context gràfic 2D
     */
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
