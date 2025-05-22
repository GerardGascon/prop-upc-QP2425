package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import javax.swing.*;
import java.awt.*;

public class ContinueButton extends MenuButton {

    private JPanel parentPanel;
    private boolean menuActive = false;
    private JPanel settingsPanel;


    public ContinueButton(JPanel parent) {
        super("Continuar");
        this.parentPanel = parent;
        addActionListener(_ -> {
            toggleSettingsPanel();
        });
    }

    public void Close(){
        Container container = parentPanel.getParent();
        // TODO: Quitar esto es horrible inicializar para borrar
        toggleSettingsPanel();
        container.remove(settingsPanel);
        menuActive = false;
        container.revalidate();
        container.repaint();
        return;
    }

    public void toggleSettingsPanel() {
        // Retornar al mode original
        if (menuActive) {
            Container container = parentPanel.getParent();
            container.remove(settingsPanel);
            menuActive = false;
            container.revalidate();
            container.repaint();
            return;
        }

        // Guarda menu original
        menuActive = true;
        Container menuMockPanel = parentPanel.getParent();

        // Panel de settings
        settingsPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = getWidth();
                int height = getHeight();

                // Fill
                GradientPaint gradient = new GradientPaint(
                        (float) (width * 0.4), (float) (height * 0.15),
                        new Color(240, 240, 250),
                        (float) (width * 0.8), (float) (height * 0.95), new Color(210, 210, 230)
                );

                g2d.setPaint(gradient);
                g2d.fillRoundRect((int) (width * 0.05), (int) (height * 0.05), (int) (width * 0.9), (int) (height * 0.9), 20, 20);

                // Títol
                g2d.setFont(new Font("SansSerif", Font.BOLD, 30));
                g2d.setColor(new Color(60, 60, 80));
                String title = "Continuar Partida";
                FontMetrics fm = g2d.getFontMetrics();
                int titleWidth = fm.stringWidth(title);
                g2d.drawString(title, (int) (width * 0.4), (int) (height * 0.15));

                // Linea títol
                g2d.setColor(new Color(180, 180, 200));
                g2d.drawLine((int) (width * 0.1), (int) (height * 0.2), (int) (width * 0.9), (int) (height * 0.2));

                // Settings títols
                g2d.setFont(new Font("SansSerif", Font.BOLD, 20));
                g2d.setColor(new Color(80, 80, 100));

                g2d.dispose();
            }
        };

        settingsPanel.setLayout(null);
        settingsPanel.setOpaque(false);

        // SetBounds
        menuMockPanel.setLayout(null);
        int menuWidth = (int) (menuMockPanel.getWidth() * 0.6);
        settingsPanel.setBounds(
                (int) (menuMockPanel.getWidth() * 0.4),
                0,
                menuWidth,
                menuMockPanel.getHeight()
        );

        // Afegir bottons d'opció multiple
        //addSettingsComponents(settingsPanel.getWidth(), settingsPanel.getHeight());
        // Fusionar amb l'escena original
        menuMockPanel.add(settingsPanel);

        // Refresh
        menuMockPanel.revalidate();
        menuMockPanel.repaint();
    }
}



