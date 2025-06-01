package edu.upc.prop.scrabble.presenter.swing.screens.game.pause;

import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

/**
 * Panell superposat (overlay) que enfosqueix la pantalla i mostra el menú de pausa.
 * Gestiona la tecla ESC per tancar-lo.
 * @author Biel Pérez Silvestre
 */
public class PauseOverlay extends JPanel {
    /**
     * Panell sobre el ho apliquem tot
     */
    private final PauseMenu parent;

    /**
     * Creadora del panell de pausa (overlay), que es mostra a sobre de la pantalla actual.
     * @param parent panell sobre el qual es mostra l'overlay
     * @param gameSaver objecte responsable de gestionar el guardat de la partida
     * @see GameSaver
     */
    public PauseOverlay(PauseMenu parent, GameSaver gameSaver) {
        super(null);
        this.parent = parent;

        setBounds(0, 0, parent.getWidth(), parent.getHeight());
        setOpaque(false);

        addMouseWheelListener(InputEvent::consume);

        InputMap inputMap = getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = getActionMap();

        inputMap.put(KeyStroke.getKeyStroke("ESCAPE"), "PAUSE_BUTTON");
        actionMap.put("PAUSE_BUTTON", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("close pause!");
                closePanel();
            }
        });

        addMouseWheelListener(InputEvent::consume);

        layoutComponents(gameSaver);
    }

    /**
     * Tanca l'overlay eliminant-lo del panell pare i actualitza la vista.
     */
    public void closePanel() {
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
        parent.closePauseMenu();
    }

    /**
     * Inicialitza i afegeix el panell de pausa a l'overlay.
     * @param gameSaver objecte utilitzat per gestionar el guardat de partida
     * @see GameSaver
     * @see PausePanel
     */
    private void layoutComponents(GameSaver gameSaver) {
        JPanel popup = new PausePanel(this, gameSaver);
        add(popup);

        popup.setBounds(0, 0, getWidth(), getHeight());
        popup.setOpaque(false);
    }

    /**
     * Pinta el fons translúcid per donar un efecte de popup sobre el contingut
     * principal.
     * @param g Context gràfic per pintar
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.dispose();
    }

    /**
     * Indica que el panell no és completament opac.
     * @return fals sempre, ja que es vol un fons translúcid
     */

    @Override
    public boolean isOpaque() {
        return false;
    }
}
