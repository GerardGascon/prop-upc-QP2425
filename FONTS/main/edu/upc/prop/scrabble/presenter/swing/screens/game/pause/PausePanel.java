package edu.upc.prop.scrabble.presenter.swing.screens.game.pause;

import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

import javax.swing.*;
import java.awt.*;

/**
 * Panell central del menú de pausa. Mostra dues opcions: continuar o sortir de la partida.
 * Si l'usuari vol sortir, es demana confirmació i si vol guardar la partida.
 * @author Biel Pérez Silvestre
 */
public class PausePanel extends JPanel {
    /**
     * Indica si l'usuari ha començat el procés de sortir de la partida.
     */
    private boolean isQuitting = false;

    /**
     * Creadora del panell de pausa. Inicialitza els botons per continuar o sortir, i gestiona
     * el canvi a la confirmació de sortida amb opció de guardar la partida.
     * @param pauseOverlay l'overlay sobre el qual es mostra aquest panell
     * @param gameSaver objecte encarregat de guardar la partida si l'usuari ho desitja
     */
    public PausePanel(PauseOverlay pauseOverlay, GameSaver gameSaver) {
        setLayout(null);

        int w = pauseOverlay.getWidth();
        int h = pauseOverlay.getHeight();

        JButton continueButton = new JButton("Continuar Partida");
        continueButton.setBounds((int) (w * 0.35), (int) (h * 0.4875), (int) (w * 0.1), (int) (h * 0.05));
        continueButton.setBackground(new Color(80, 130, 200));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);
        add(continueButton);

        JButton exitButton = new JButton("Sortir Partida");
        exitButton.setBounds((int) (w * 0.55), (int) (h * 0.4875), (int) (w * 0.1), (int) (h * 0.05));
        exitButton.setBackground(new Color(200, 100, 100));
        exitButton.setForeground(Color.WHITE);
        add(exitButton);

        exitButton.addActionListener(_ -> {
            isQuitting = true;
            remove(exitButton);
            remove(continueButton);

            JButton yesButton = new JButton("Guardar Partida");
            yesButton.setBounds((int) (w * 0.35), (int) (h * 0.4875), (int) (w * 0.1), (int) (h * 0.05));
            yesButton.setBackground(new Color(80, 130, 200));
            yesButton.setForeground(Color.WHITE);
            add(yesButton);

            JButton noButton = new JButton("Sortir Sense Guardar");
            noButton.setBounds((int) (w * 0.55), (int) (h * 0.4875), (int) (w * 0.1), (int) (h * 0.05));
            noButton.setBackground(new Color(200, 100, 100));
            noButton.setForeground(Color.WHITE);
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
    /**
     * Pinta el fons del panell i el títol, amb un missatge diferent si l'usuari està sortint.
     *
     * @param g context gràfic utilitzat per dibuixar
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        int w = getWidth();
        int h = getHeight();

        g2.setColor(new Color(240, 240, 250));
        g2.fillRoundRect((int) (w * 0.3), (int) (h * 0.3), (int) (w * 0.4), (int) (h * 0.4), 20, 20);

        // Title
        g2.setFont(new Font("SansSerif", Font.BOLD, 28));
        g2.setColor(new Color(60, 60, 80));
        if (isQuitting) {
            String title = "T'agradria Guardar la Partida?";
            g2.drawString(title,  (int)(w*0.425), (int)(h*0.35));
        } else {
            String title = "Pausa";
            g2.drawString(title, (int) (w * 0.475), (int) (h * 0.35));
        }

        // Línia títol
        g2.setColor(new Color(180, 180, 200));
        g2.drawLine((int) (w * 0.35), (int) (h * 0.375), (int) (w * 0.65), (int) (h * 0.375));

        g2.dispose();
    }
    /**
     * Indica que el panell és translúcid (no opac) per permetre veure l'overlay del darrere.
     * @return sempre retorna false per mantenir la transparència
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
