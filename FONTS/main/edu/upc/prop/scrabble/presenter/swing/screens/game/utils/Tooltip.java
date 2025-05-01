package edu.upc.prop.scrabble.presenter.swing.screens.game.utils;

import javax.swing.*;
import java.awt.*;

public class Tooltip extends JWindow {
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

    private static JLabel createTooltipLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.DARK_GRAY);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setOpaque(false);
        return label;
    }

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

    public void showAt(int x, int y) {
        setLocation(x, y);
    }
}
