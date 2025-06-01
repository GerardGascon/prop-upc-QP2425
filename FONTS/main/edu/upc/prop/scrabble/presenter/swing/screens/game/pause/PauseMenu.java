package edu.upc.prop.scrabble.presenter.swing.screens.game.pause;

import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

import javax.swing.*;
import java.awt.event.ActionEvent;
/**
 * Panell del menú de pausa, que detecta la tecla ESC per obrir un panell d'opcions (overlay),
 * com guardar o continuar la partida.
 * @author Biel Pérez Silvestre
 */
public class PauseMenu extends JPanel {
    /**
     * Objecte encarregat de gestionar el guardat de la partida.
     */
    private final GameSaver gameSaver;
    /**
     * Panell superposat (overlay) que es mostra quan s’activa el menú de pausa.
     */
    private JPanel overlay;

    /**
     * Constructor del menú de pausa. Configura l’input ESC per obrir el panell de pausa
     * i assigna el gestor de guardat.
     * @param gameSaver objecte encarregat de guardar l'estat actual de la partida
     * @see GameSaver
     */
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

    /**
     * Obre el panell de pausa si no està ja actiu. Afegeix l'overlay a la vista,
     * li dona el focus i actualitza l’escena.
     */
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
    /**
     * Tanca el menú de pausa eliminant la referència a l’overlay actiu.
     */
    public void closePauseMenu() {
        overlay = null;
    }
}
