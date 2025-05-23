package edu.upc.prop.scrabble.presenter.swing.screens.menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PauseButton extends MenuButton {

    private final JPanel parentPanel;
    private boolean menuActive = false;
    private JPanel pausePanel;
    private JPanel confirmExitPanel;

    public PauseButton(JPanel parent) {
        super("Pausa");
        this.parentPanel = parent;

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "PAUSE_BUTTON");
        actionMap.put("PAUSE_BUTTON", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Escape key pressed!");
                togglePausePanel();
            }
        });
    }

    private void togglePausePanel() {
        if (menuActive) {
            closePanels();
            return;
        }

        menuActive = true;
        Container container = parentPanel.getParent();

        pausePanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();

                // Box
                g2d.setColor(new Color(240, 240, 250));
                g2d.fillRoundRect((int)(w*0.855), (int)(h*0.1), (int)(w*0.14), (int)(h*0.7), 20, 20);

                // Title
                g2d.setFont(new Font("SansSerif", Font.BOLD, 28));
                g2d.setColor(new Color(60, 60, 80));
                String title = "Pausa";
                g2d.drawString(title,  (int)(w*0.91), (int)(h*0.2));

                // Linea títol
                g2d.setColor(new Color(180, 180, 200));
                g2d.drawLine((int) (w * 0.86), (int) (h * 0.225), (int) (w * 0.98), (int) (h * 0.225));

                g2d.dispose();
            }
        };

        int w = parentPanel.getWidth();
        int h = parentPanel.getHeight();

        pausePanel.setBounds(0,0,getWidth(),getHeight());
        pausePanel.setOpaque(false);

        JButton continueButton = new JButton("Continuar Partida");
        continueButton.setBounds((int)(w*0.875), (int)(h*0.4), (int)(w*0.1), (int)(h*0.025));
        continueButton.setBackground(new Color(80, 130, 200));
        continueButton.setForeground(Color.WHITE);
        continueButton.setFocusPainted(false);


        JButton exitButton = new JButton("Sortir Partida");
        exitButton.setBounds((int)(w*0.875), (int)(h*0.525), (int)(w*0.1), (int)(h*0.025));
        exitButton.setBackground(new Color(200, 100, 100));
        exitButton.setForeground(Color.WHITE);

        continueButton.addActionListener(_ -> closePanels());

        exitButton.addActionListener(e -> {
            container.remove(pausePanel);

            confirmExitPanel = createConfirmExitPanel(container);
            container.add(confirmExitPanel);
            container.setComponentZOrder(confirmExitPanel, 0);

            confirmExitPanel.revalidate();
            confirmExitPanel.repaint();

            container.revalidate();
            container.repaint();
        });

        pausePanel.add(continueButton);
        pausePanel.add(exitButton);

        container.add(pausePanel);
        container.setComponentZOrder(pausePanel, 0);
        container.revalidate();
        container.repaint();
    }

    private JPanel createConfirmExitPanel(Container container) {
        confirmExitPanel = new JPanel(null) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int w = getWidth();
                int h = getHeight();

                // Box
                g2d.setColor(new Color(240, 240, 250));
                g2d.fillRoundRect((int)(w*0.855), (int)(h*0.1), (int)(w*0.14), (int)(h*0.7), 20, 20);

                // Title
                g2d.setFont(new Font("SansSerif", Font.BOLD, 28));
                g2d.setColor(new Color(60, 60, 80));
                String title = "Quieres Guardar?";
                g2d.drawString(title,  (int)(w*0.88), (int)(h*0.2));

                // Linea títol
                g2d.setColor(new Color(180, 180, 200));
                g2d.drawLine((int) (w * 0.86), (int) (h * 0.225), (int) (w * 0.98), (int) (h * 0.225));

                g2d.dispose();
            }

        };

        int w = parentPanel.getWidth();
        int h = parentPanel.getHeight();
        confirmExitPanel.setOpaque(false);
        confirmExitPanel.setBounds(0,0,w,h);

        JButton yesButton = new JButton("Sí");
        yesButton.setBounds((int)(w*0.875), (int)(h*0.4), (int)(w*0.1), (int)(h*0.025));
        yesButton.setBackground(new Color(80, 130, 200));

        JButton noButton = new JButton("No");
        noButton.setBounds((int)(w*0.875), (int)(h*0.525), (int)(w*0.1), (int)(h*0.025));
        noButton.setBackground(new Color(200, 100, 100));


        yesButton.addActionListener(e -> {
            // TODO: Guardar partida aquí
            System.out.println("Guardando...");
            closePanels();
            System.exit(0);
        });

        noButton.addActionListener(e -> {
            closePanels();
            System.exit(0);
        });

        confirmExitPanel.add(yesButton);
        confirmExitPanel.add(noButton);

        return confirmExitPanel;
    }

    private void closePanels() {
        Container container = parentPanel.getParent();
        if (pausePanel != null) container.remove(pausePanel);
        if (confirmExitPanel != null) container.remove(confirmExitPanel);
        menuActive = false;
        container.revalidate();
        container.repaint();
    }
}
