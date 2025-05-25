package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.GamesPlayedLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.GamesWonLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.WinRateLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.MaxScoreLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.TotalScoreLeaderboard;
import edu.upc.prop.scrabble.domain.leaderboard.PlayerValuePair;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Botó encarregat de gestionar la visualització dels rànquings
 * @author Felipe Martínez LassalleB
 */
public class RanquingButton extends MenuButton {
    /**
     * Panell principal sobre el qual es mostren els menús desplegables del botó.
     */
    private JPanel parentPanel;
    /**
     * Indica si el menú desplegable del botó està actualment actiu o no.
     */
    private boolean ranquingActive = false;
    /**
     *Panell visualització dels rànquings
     */
    private JPanel ranquingPanel;
    /**
     * Selector dels diferents mètodes de rànquing
     */
    private JComboBox<String> modeSelector;
    /**
     * Text del botó
     */
    private JTextArea ranquingText;
    /**
     * Array dels botons que facilita la gestió dels panells del menú
     */
    private MenuButton[] otherButtons;
    /**
     * Contenidor de les dades a mostrar
     */
    private Leaderboard leaderboard;

    /**
     * Creador del botó "Rànquing"
     * @param parent El panell principal sobre el qual es mostrarà el menú.
     */
    public RanquingButton(JPanel parent) {
        super("Rànquing");
        this.parentPanel = parent;
        addActionListener(_ -> {
            if (otherButtons != null) {
                for (MenuButton b : otherButtons) {
                //    if (b != null) b.Close();
                }
            }
            toggleRanquingPanel();
        });
    }
    /**
     * Assigna els botons complementaris amb el qual es coordina l'intercanvi de panells.
     * Normalment utilitzat per assegurar que només un menú està actiu alhora.
     * @param otherButtons Els botons amb què es coordina.
     */
    public void setOtherButtons(MenuButton[] otherButtons) {
        this.otherButtons = otherButtons;
    }

    /**
     * Tanca el panell desplegat si està actiu.
     */
    //@Override
    public void Close() {
        if(!ranquingActive) return;

        Container container = parentPanel.getParent();
        container.remove(ranquingPanel);
        ranquingActive = false;
        container.revalidate();
        container.repaint();

    }

    /**
     * Mostra o amaga el panell associat al botó "Rànquing".
     * Si el panell ja està actiu, s’elimina de la interfície i es refresca l’escena.
     * Si no està actiu, es crea un nou panell amb disseny personalitzat (fons degradat, títol i seccions),
     * es posiciona al costat dret i es fusiona amb el panell principal.
     */
    public void toggleRanquingPanel() {
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

        addRankingComponents(panelWidth, mainPanel.getHeight());

        mainPanel.add(ranquingPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    /**
     * Afegeix els components presents al panell
     * @param width Amplada del panell
     * @param height Altura del panell
     */
    private void addRankingComponents(int width, int height) {
        int componentHeight = (int)(height * 0.05);

        // Ranking text area
        ranquingText = new JTextArea();
        ranquingText.setEditable(false);
        ranquingText.setFont(new Font("Monospaced", Font.PLAIN, 16));
        JScrollPane scrollPane = new JScrollPane(ranquingText);
        scrollPane.setBounds((int)(width * 0.1), (int)(height * 0.25), (int)(width * 0.8), (int)(height * 0.5));
        ranquingPanel.add(scrollPane);

        // Mode selector
        String[] modes = {"Partides Jugades", "Partides Guanyades",
                          "Percentatge de Victòries", "Màxima Puntuació", "Puntuació Total"};
        modeSelector = new JComboBox<>(modes);
        modeSelector.setSelectedItem("Partides Jugades");
        modeSelector.setBounds((int)(width * 0.1), (int)(height * 0.2), (int)(width * 0.4), componentHeight);
        modeSelector.addActionListener(this::updateRanquing);
        ranquingPanel.add(modeSelector);

        JButton closeButton = new JButton("Tancar");
        closeButton.setBounds((int)(width * 0.7), (int)(height * 0.85), (int)(width * 0.2), componentHeight);
        closeButton.setBackground(new Color(200, 100, 100));
        closeButton.setForeground(Color.WHITE);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> toggleRanquingPanel());
        ranquingPanel.add(closeButton);

        updateRanquing(null); // Initial population
    }

    /**
     * Actualitza el mètode d'ordenació del ranking
     * @param e Esdeveniment que causa l'actualització
     */
    private void updateRanquing(ActionEvent e) {
        String mode = (String) modeSelector.getSelectedItem();
        PlayerValuePair[] ranquingData = new PlayerValuePair[0];
        Leaderboard leaderboard = new Leaderboard(); // A QUITAR!
        switch (mode) {
            case "Partides Jugades":
                GamesPlayedLeaderboard gamesPlayedLeaderboard = new GamesPlayedLeaderboard();
                ranquingData = gamesPlayedLeaderboard.run(leaderboard.getScoreArray());
                break;
            case "Partides Guanyades":
                GamesWonLeaderboard gamesWonLeaderboard = new GamesWonLeaderboard();
                ranquingData = gamesWonLeaderboard.run(leaderboard.getScoreArray());
                break;
            case "Percentatge de Victòries":
                WinRateLeaderboard winRateLeaderboard = new WinRateLeaderboard();
                ranquingData = winRateLeaderboard.run(leaderboard.getScoreArray());
                break;
            case "Màxima Puntuació":
                MaxScoreLeaderboard maxScoreLeaderboard = new MaxScoreLeaderboard();
                ranquingData = maxScoreLeaderboard.run(leaderboard.getScoreArray());
                break;
            case "Puntuació Total":
                TotalScoreLeaderboard totalScoreLeaderboard = new TotalScoreLeaderboard();
                ranquingData = totalScoreLeaderboard.run(leaderboard.getScoreArray());
                break;
        }

        StringBuilder builder = new StringBuilder();
        int pos = 1;
        for (int i = 0; i < ranquingData.length; i++) {
            // No ensenyar decimals si no cal
            double value = ranquingData[i].value();
            String valueStr;
            if (mode.equals("Percentatge de Victòries")) {
                valueStr = String.format("%.2f", value);
            } else {
                valueStr = String.format("%.0f", value);
            }
            if(i != 0 && value != ranquingData[i - 1].value()) ++pos; // Empat de posicions
            builder.append(String.format("%d. %-10s - %s%n", pos, ranquingData[i].playerName(), valueStr));
        }
        ranquingText.setText(builder.toString());
    }
}