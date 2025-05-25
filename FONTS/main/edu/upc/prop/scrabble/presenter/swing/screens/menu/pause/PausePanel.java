package edu.upc.prop.scrabble.presenter.swing.screens.menu.pause;

import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

import javax.swing.*;
import java.awt.*;

public class PausePanel extends JPanel {
    private boolean isQuitting = false;

    public PausePanel(PauseOverlay pauseOverlay, GameSaver gameSaver) {
        setLayout(null);

        int w = pauseOverlay.getWidth();
        int h = pauseOverlay.getHeight();

        JButton continueButton = new JButton("Continuar Partida");
        continueButton.setBounds((int) (w * 0.875), (int) (h * 0.4), (int) (w * 0.1), (int) (h * 0.025));
        continueButton.setBackground(new Color(80, 130, 200));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        add(continueButton);

        JButton exitButton = new JButton("Sortir Partida");
        exitButton.setBounds((int) (w * 0.875), (int) (h * 0.525), (int) (w * 0.1), (int) (h * 0.025));
        exitButton.setBackground(new Color(200, 100, 100));
        exitButton.setForeground(Color.WHITE);
        add(exitButton);

        exitButton.addActionListener(_ -> {
            isQuitting = true;
            remove(exitButton);
            remove(continueButton);

            JButton yesButton = new JButton("Sí");
            yesButton.setBounds((int) (w * 0.875), (int) (h * 0.4), (int) (w * 0.1), (int) (h * 0.025));
            yesButton.setBackground(new Color(80, 130, 200));
            add(yesButton);

            JButton noButton = new JButton("No");
            noButton.setBounds((int) (w * 0.875), (int) (h * 0.525), (int) (w * 0.1), (int) (h * 0.025));
            noButton.setBackground(new Color(200, 100, 100));
            add(noButton);

            yesButton.addActionListener(_ -> {
                gameSaver.run();
                System.exit(0);
            });

            noButton.addActionListener(_ -> {
                System.exit(0);
            });

            revalidate();
            repaint();
        });

        continueButton.addActionListener(_ -> pauseOverlay.closePanel());
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(240, 240, 250));
        g2.fillRoundRect((int) (w * 0.855), (int) (h * 0.1), (int) (w * 0.14), (int) (h * 0.7), 20, 20);

        // Title
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        g2.setColor(new Color(60, 60, 80));
        if (isQuitting) {
            String title = "Quieres Guardar?";
            g2.drawString(title,  (int)(w*0.88), (int)(h*0.2));
        } else {
            String title = "Pausa";
            g2.drawString(title, (int) (w * 0.91), (int) (h * 0.2));
        }

        // Línia títol
        g2.setColor(new Color(180, 180, 200));
        g2.drawLine((int) (w * 0.86), (int) (h * 0.225), (int) (w * 0.98), (int) (h * 0.225));

        g2.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
