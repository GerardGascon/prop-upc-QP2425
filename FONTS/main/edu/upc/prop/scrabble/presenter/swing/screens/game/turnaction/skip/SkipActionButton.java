package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.skip;

import javax.swing.*;
import java.awt.*;
/**
 * Classe per gestionar el botó de Skip (Saltar) en una partida.
 * @author Gina Escofet González
 */
public class SkipActionButton extends JButton {
    /***
     * Construeix un botó personalitzat per l'acció de "Skip" (Saltar).
     * @param text El text que es mostrarà al botó.
     */
    public SkipActionButton(String text) {
        super(text);
        setFocusPainted(false); // Removes the focus ring when the button is clicked
        setContentAreaFilled(false); // Disables the default background filling
        setFont(new Font("SansSerif", Font.BOLD, 13));
        setForeground(Color.BLACK); // Text color
        setBackground(new Color(0xaa, 0xaa, 0xaa));
        setBorder(null);

        disableKeyboardInput();
    }
    /**
     * Desactiva les tecles espai i enter per evitar que activin el botó per teclat.
     */
    private void disableKeyboardInput() {
        InputMap inputMap = getInputMap(JComponent.WHEN_FOCUSED);
        inputMap.put(KeyStroke.getKeyStroke("SPACE"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released SPACE"), "none");

        inputMap.put(KeyStroke.getKeyStroke("ENTER"), "none");
        inputMap.put(KeyStroke.getKeyStroke("released ENTER"), "none");
    }
    /**
     * Pinta el botó amb esquemes de color i cantonades arrodonides personalitzades.
     * Utilitza efectes de sombrejat segons l'estat del botó (clicat, sobrevolat, normal).
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
     * Indica que el panell no és opac per permetre transparències o dibuixos sota seu.
     * @return fals sempre.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
