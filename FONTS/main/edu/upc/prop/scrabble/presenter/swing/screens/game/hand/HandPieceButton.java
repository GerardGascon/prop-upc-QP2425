package edu.upc.prop.scrabble.presenter.swing.screens.game.hand;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;


/**
 * Botó personalitzat que representa una peça de lletra del jugador a la seva mà.
 * Mostra la lletra i la seva puntuació, amb estil gràfic personalitzat.
 * @author Gina Escofet González
 */
public class HandPieceButton extends JButton {
    /**
     * Lletra que representa la peça (pot ser una lletra simple o buida en cas de peça blanca).
     */
    private final String letter;
    /**
     * Puntuació associada a la peça. Normalment va de 0 a 10 segons el valor de la lletra.
     */
    private final int points;
    /**
     * Constructor del botó de peça de mà.
     * @param letter Lletra que representa la peça
     * @param points Puntuació de la lletra
     */
    public HandPieceButton(String letter, int points) {
        super();
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        disableKeyboardInput();

        this.letter = letter;
        this.points = points;
    }
    /**
     * Inhabilita l'entrada per teclat com l'espai o enter per evitar
     * selecció accidental del botó via teclat.
     */
    private void disableKeyboardInput() {
        InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released ENTER"), "none");
    }
    /**
     * Calcula el radi de les cantonades arrodonides basat en l'alçada del botó.
     * @return Radi en píxels
     */
    private int getCornerRadius() {
        return getHeight() * 20 / 100 * 2;
    }
    /**
     * Personalitza el renderitzat del botó per mostrar la peça estilitzada.
     */
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

    /**
     * Retorna la lletra associada al botó.
     */
    public String getLetter() {
        return letter;
    }
    /**
     * Retorna la puntuació de la lletra.
     */
    public int getPoints() {
        return points;
    }
    /**
     * Dibuixa el rectangle arrodonit del botó i el seu contingut.
     *
     * @param g Gràfics
     * @param bg Color de fons
     * @param radius Radi de les cantonades
     */
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        drawLetter(g);
        drawScore(g);
    }
    /**
     * Dibuixa la lletra centrada al botó. Fa un escalat si la lletra és múltiple (ex: "QU").
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
     * Dibuixa la puntuació en petit a la cantonada inferior dreta.
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
