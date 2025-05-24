package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import javax.swing.*;
import java.awt.*;

/**
 * Botó personalitzat per al selector de peces amb estil propi i sense interacció per teclat.
 * Aplica un fons rodó amb colors que canvien segons l'estat (pressionat, passant el ratolí).
 *
 * @author Gerard Gascón
 */
class PieceSelectorButton extends JButton {

    /**
     * Crea un botó amb el text indicat i aplica l'estil personalitzat.
     *
     * @param text Text que es mostrarà al botó
     */
    public PieceSelectorButton(String text) {
        super(text);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setForeground(Color.WHITE);
        setBackground(new Color(0xf5, 0x2e, 0x2e));
        setBorder(null);

        disableKeyboardInput();
    }

    /**
     * Desactiva la interacció del botó amb les tecles Espai i Enter
     * per evitar activacions no desitjades via teclat.
     */
    private void disableKeyboardInput() {
        InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released ENTER"), "none");
    }

    /**
     * Pinta el botó amb un fons rodó i colors adaptats segons l'estat.
     *
     * @param g Context gràfic per pintar
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

        g2.setColor(bgColor);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 48, 48);

        super.paintComponent(g2);
        g2.dispose();
    }

    /**
     * Indica que el botó no és opac per permetre pintar fons personalitzat.
     *
     * @return false sempre
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
