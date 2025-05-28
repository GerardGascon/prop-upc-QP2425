package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

/**
 * Classe personalitzada de botó Swing que representa un botó de menú estilitzat.
 * Dibuixa el botó amb cantonades arrodonides i una lletra gran en estil Scrabble.
 * També desactiva la interacció amb el teclat per evitar activacions accidentals amb ESPAI o ENTER.
 *
 * @author Gerard Gascón
 */
public class MenuButton extends JButton {
    /**
     * Text que es mostra damunt del botó.
     */
    private final String text;

    /**
     * Boto amb el que s'intercanvia els panels.
     * Si es prem otherbutton mentre s'estan mostrant panels de JugarButton, aleshores
     * es tanca els panels de continueButton
     */
    protected MenuButton[] otherButtons;

    /**
     * Crea un nou botó de menú amb el text especificat.
     *
     * @param text el text que es mostrarà al botó.
     */
    public MenuButton(String text) {
        super();
        setOpaque(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorderPainted(false);

        this.text = text;

        disableKeyboardInput();
    }

    /**
     * Desactiva la interacció per teclat (ESPAI i ENTER) per evitar activacions no desitjades.
     */
    private void disableKeyboardInput() {
        InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released ENTER"), "none");
    }

    /**
     * Assigna el botó complementari amb el qual es coordina l'intercanvi de panells.
     * Normalment utilitzat per assegurar que només un menú està actiu alhora.
     *
     * @param otherButtons El botó amb què es coordina.
     */
    public void setOtherButtons(MenuButton[] otherButtons) {
        this.otherButtons = otherButtons;
    }

    /**
     * Pinta el component del botó amb efectes d'estil personalitzat, com ara colors segons l'estat
     * (normal, sobresortit, premut) i forma arrodonida.
     *
     * @param g l'objecte Graphics que s'utilitza per dibuixar el botó.
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
     * Dibuixa la forma del botó com una fitxa amb cantonades arrodonides i el text centrat verticalment.
     *
     * @param g      l'objecte Graphics2D utilitzat per al dibuix.
     * @param bg     el color de fons del botó.
     * @param radius el radi de les cantonades arrodonides.
     */
    protected void drawTile(Graphics2D g, Color bg, int radius) {
        g.setColor(bg);
        g.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
        drawLetter(g);
    }

    /**
     * Dibuixa el text del botó amb una font gran i negra, alineada verticalment al centre.
     *
     * @param g l'objecte Graphics2D utilitzat per al dibuix del text.
     */
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

    /**
     * Comprova si el punt especificat es troba dins de la forma arrodonida del botó.
     *
     * @param x la coordenada x del punt.
     * @param y la coordenada y del punt.
     * @return true si el punt està dins del botó, false altrament.
     */
    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), getCornerRadius(), getCornerRadius());
        return shape.contains(x, y);
    }

    /**
     * Calcula el radi de les cantonades arrodonides basant-se en l'altura del botó.
     *
     * @return el radi de les cantonades.
     */
    private int getCornerRadius() {
        return getHeight() * 20 / 100 * 2;
    }


    protected void Close() {
    }
}
