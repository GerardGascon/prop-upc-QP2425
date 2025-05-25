package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.skip;

import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;

import javax.swing.*;
/**
 * Classe per gestionar la lògica del botó de Skip (Saltar) en una partida.
 * @author Gina Escofet González
 */
public class SkipAction extends JPanel {
    /**
     * Panell pare on s'afegirà el botó Skip.
     */
    private final JPanel parent;
    /**
     * Botó que representa l'acció de saltar el torn.
     */
    private JButton skipBtn;
    /**
     * Objecte que gestiona la lògica associada a l'acció de Skip.
     */
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
    /**
     * Crea el botó Skip, li assigna posició fixa i el listener per executar l'acció.
     * El botó s'afegeix directament al panell pare.
     */
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
    /**
     * Indica que el panell no és opac per permetre transparències o dibuixos sota seu.
     * @return fals sempre.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
}
