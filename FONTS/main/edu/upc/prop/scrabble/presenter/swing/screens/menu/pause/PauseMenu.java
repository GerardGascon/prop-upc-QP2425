package edu.upc.prop.scrabble.presenter.swing.screens.menu.pause;

import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class PauseMenu extends JPanel {
    private final GameSaver gameSaver;

    private JPanel overlay;

    public PauseMenu(GameSaver gameSaver) {
        setLayout(null);
        setOpaque(false);

        this.gameSaver = gameSaver;

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

        overlay = new PauseOverlay(this, gameSaver);
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
