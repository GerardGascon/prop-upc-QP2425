package edu.upc.prop.scrabble.presenter.swing.screens.menu.pause;

import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;

public class PauseOverlay extends JPanel {
    private final PauseMenu parent;

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

    public void closePanel() {
        parent.remove(this);
        parent.revalidate();
        parent.repaint();
        parent.closePauseMenu();
    }

    private void layoutComponents(GameSaver gameSaver) {
        JPanel popup = new PausePanel(this, gameSaver);
        add(popup);

        popup.setBounds(0, 0, getWidth(), getHeight());
        popup.setOpaque(false);
    }

    /**
     * Pinta el fons translúcid per donar un efecte de popup sobre el contingut
     * principal.
     *
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
     *
     * @return false sempre, ja que es vol un fons translúcid
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
