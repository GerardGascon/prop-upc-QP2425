package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.skip;

import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;

import javax.swing.*;
/**
 * Classe per gestionar la lògica del botó de Skip (Saltar) en una partida.
 * @author Gina Escofet González
 */
public class SkipAction extends JPanel {
    private final JPanel parent;
    private JButton skipBtn;
    private final SkipActionMaker skipActionMaker;
    /***
     * Construeix un objecte `SkipAction`.
     * @param parent El panell pare on s'afegirà aquest component.
     * @param skipActionMaker L'objecte responsable de gestionar la lògica de robar peces.
     */
    public SkipAction(SkipActionMaker skipActionMaker, JPanel parent) {
        setOpaque(false);

        this.skipActionMaker = skipActionMaker;
        this.parent = parent;
        createSkipButton();
        add(skipBtn);
    }

    private void createSkipButton() {
        skipBtn = new JButton("Skip");
        skipBtn.setBounds(1400, 650, 75, 50); //hardcoded
        skipBtn.addActionListener(e -> {
            skipActionMaker.run();
            parent.revalidate();
            parent.repaint();
        });
        parent.add(skipBtn);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
