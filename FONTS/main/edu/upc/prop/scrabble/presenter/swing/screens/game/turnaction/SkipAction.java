package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction;

import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;

import javax.swing.*;

/**
 * Classe per gestionar la lògica del botó de Skip (Saltar) en una partida.
 * @author Gina Escofet González
 */
public class SkipAction extends JPanel {
    /**
     * Botó que representa l'acció de saltar el torn.
     */
    private final JButton skipBtn;
    /**
     * Controlador per a passar el torn.
     */
    private SkipActionMaker skip;

    /***
     * Construeix un objecte `SkipAction`.
     */
    public SkipAction() {
        setOpaque(false);

        skipBtn = new JButton("Passar");
        skipBtn.addActionListener(_ -> skipTurn());
        add(skipBtn);
    }
    /**
     * Indica que el panell no és opac per permetre transparències o dibuixos sota seu.
     * @return fals sempre.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }
    /**
     * Assigna l'objecte que gestiona la lògica per saltar el torn.
     * Aquest objecte s'executarà quan l'usuari premi el botó "Passar".
     *
     * @param skip Instància de SkipActionMaker per gestionar l'acció de saltar torn.
     */
    public void setSkip(SkipActionMaker skip) {
        this.skip = skip;
    }
    /**
     * Executa l'acció de saltar el torn cridant al SkipActionMaker associat,
     * sempre que aquest no sigui nul.
     */
    private void skipTurn() {
        if (skip != null)
            skip.run();
    }
}
