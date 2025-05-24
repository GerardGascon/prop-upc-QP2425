package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import javax.swing.*;
import java.awt.*;

/**
 * Fitxa que mostra una coordenada al tauler (lletra o número).
 * <p>
 * Aquesta classe representa una cel·la que conté una lletra de
 * coordenada i que es dibuixa centrada dins la cel·la.
 * </p>
 *
 * @author Gerard Gascón
 */
public class BoardCoordinateTile extends JPanel {
    /**
     * Lletra o símbol que representa la coordenada.
     */
    private final String letter;

    /**
     * Crea una nova fitxa de coordenada amb la lletra especificada.
     *
     * @param coordinate La lletra o símbol de la coordenada
     */
    public BoardCoordinateTile(String coordinate) {
        setOpaque(false);
        setLayout(new BorderLayout());

        this.letter = coordinate;
    }

    /**
     * Pinta el component amb antialiasing per a millorar la qualitat
     * i dibuixa la lletra de la coordenada.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        drawTile(g2, getBackground());

        super.paintComponent(g);
        g2.dispose();
    }

    /**
     * Comprova si un punt està dins la forma rectangular de la fitxa.
     *
     * @param x coordenada x del punt
     * @param y coordenada y del punt
     * @return true si el punt està dins la fitxa, false altrament
     */
    @Override
    public boolean contains(int x, int y) {
        Shape shape = new Rectangle.Float(0, 0, getWidth(), getHeight());
        return shape.contains(x, y);
    }

    /**
     * Dibuixa la fitxa. Per defecte, només dibuixa la lletra.
     *
     * @param g el context gràfic
     * @param bg el color de fons (no s'utilitza aquí)
     */
    protected void drawTile(Graphics2D g, Color bg) {
        drawLetter(g);
    }

    /**
     * Dibuixa la lletra de la coordenada centrada dins la fitxa.
     *
     * @param g el context gràfic
     */
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
