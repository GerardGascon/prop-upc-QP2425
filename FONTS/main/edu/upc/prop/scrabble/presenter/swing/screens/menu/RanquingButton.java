package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.presenter.swing.screens.menu.MenuButton;


import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

public class RanquingButton extends MenuButton {

    private JPanel parentPanel;
    private boolean ranquingActive = false;
    private JPanel ranquingPanel;
    private JComboBox<String> modeSelector;
    private JTextArea ranquingText;

    public RanquingButton(JPanel parent) {
        super("Rànquing");
        this.parentPanel = parent;
        addActionListener(e -> toggleRanquingPanel());
    }

    private void toggleRanquingPanel() {
        // Amaga panel
        if (ranquingActive) {
            Container container = parentPanel.getParent();
            container.remove(ranquingPanel);
            ranquingActive = false;
            container.revalidate();
            container.repaint();
            return;
        }

        ranquingActive = true;
        Container mainPanel = parentPanel.getParent();

        ranquingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                GradientPaint gradient = new GradientPaint(
                        (float)(width * 0.4), (float)(height * 0.15),
                        new Color(250, 240, 230),
                        (float)(width * 0.8), (float)(height * 0.95),
                        new Color(210, 210, 210)
                );

                g2d.setPaint(gradient);
                g2d.fillRoundRect((int)(width * 0.05), (int)(height * 0.05), (int)(width * 0.9), (int)(height * 0.9), 20, 20);

                g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
                g2d.setColor(new Color(80, 60, 50));
                String title = "Rànquings";
                FontMetrics fm = g2d.getFontMetrics();
                int titleWidth = fm.stringWidth(title);
                g2d.drawString(title, (width - titleWidth) / 2, (int)(height * 0.15));

                g2d.setColor(new Color(180, 160, 140));
                g2d.drawLine((int)(width * 0.1), (int)(height * 0.2), (int)(width * 0.9), (int)(height * 0.2));

                g2d.dispose();
            }
        };

        ranquingPanel.setLayout(null);
        ranquingPanel.setOpaque(false);
        int panelWidth = (int)(mainPanel.getWidth() * 0.6);
        ranquingPanel.setBounds((int)(mainPanel.getWidth() * 0.4), 0, panelWidth, mainPanel.getHeight());
        mainPanel.setLayout(null);

        //addRankingComponents(panelWidth, mainPanel.getHeight());

        mainPanel.add(ranquingPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }
}