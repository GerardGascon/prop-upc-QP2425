package edu.upc.prop.scrabble.presenter.swing.screens.game.utils;

import javax.swing.*;
import java.awt.*;

/**
 * Finestra emergent personalitzada per mostrar textos d'ajuda (tooltip) amb estil.
 * La finestra es crea transparent i amb cantonades arrodonides.
 *
 * @author Gerard Gascón
 */
public class Tooltip extends JWindow {
    /**
     * Crea un tooltip vinculat a un component propietari amb un text específic.
     *
     * @param owner component propietari sobre el qual es posicionarà el tooltip
     * @param text text a mostrar dins del tooltip
     */
    public Tooltip(Component owner, String text) {
        super(SwingUtilities.getWindowAncestor(owner));
        JPanel panel = createTooltipPanel();
        JLabel label = createTooltipLabel(text);
        panel.add(label, BorderLayout.CENTER);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);
        setBackground(new Color(0, 0, 0, 0));
        pack();
    }

    /**
     * Crea una etiqueta per al tooltip amb el text especificat.
     *
     * @param text text que mostrarà l'etiqueta
     * @return JLabel configurat amb el text i estil
     */
    private static JLabel createTooltipLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.DARK_GRAY);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setOpaque(false);
        return label;
    }

    /**
     * Crea un panell personalitzat amb cantonades arrodonides i fons blanc translúcid,
     * que s'utilitza com a fons del tooltip.
     *
     * @return JPanel configurat per a contenir el tooltip
     */
    private static JPanel createTooltipPanel() {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 16, 16);
                g2.dispose();
                super.paintComponent(g);
            }
        };
        panel.setOpaque(false);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        return panel;
    }

    /**
     * Mostra el tooltip a la posició (x, y) especificada a la pantalla.
     *
     * @param x posició horitzontal on mostrar el tooltip
     * @param y posició vertical on mostrar el tooltip
     */
    public void showAt(int x, int y) {
        setLocation(x, y);
    }
}
