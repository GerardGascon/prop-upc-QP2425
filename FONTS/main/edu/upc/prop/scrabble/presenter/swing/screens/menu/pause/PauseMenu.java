package edu.upc.prop.scrabble.presenter.swing.screens.menu.pause;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PauseMenu extends JPanel {
    private JPanel overlay;

    public PauseMenu() {
        setLayout(null);
        setOpaque(false);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "PAUSE_BUTTON");
        actionMap.put("PAUSE_BUTTON", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Escape key pressed!");
                openPausePanel();
            }
        });
    }

    private void openPausePanel() {
        if (overlay != null)
            return;

        overlay = new PauseOverlay(this);
        add(overlay);
        overlay.requestFocusInWindow();
        setComponentZOrder(overlay, 0);
        revalidate();
        repaint();
    }

    public void closePauseMenu() {
        overlay = null;
    }
}
