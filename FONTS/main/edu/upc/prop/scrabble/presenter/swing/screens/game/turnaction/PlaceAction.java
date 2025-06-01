package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;

import javax.swing.*;

/**
 * Classe per gestionar la lògica del botó de Place (Posar) en una partida.
 * @author Gina Escofet González
 */
public class PlaceAction extends JPanel {
    /** Botó per iniciar l'acció de posar peces al tauler. */
    private JButton placeBtn;
    /** Botó per confirmar la col·locació de peces al tauler. */
    private JButton confirmBtn;
    /** Botó per cancel·lar la col·locació i retornar les peces a la mà. */
    private JButton cancelBtn;
    /** Vista del tauler de joc, per accedir a la col·locació actual. */
    private final BoardView boardView;
    /**
     * La vista on es mostren les fitxes de la mà
     */
    private final HandView handView;
    /**
     * El panell amb els botons d'acció
     */
    private ActionButtonPanel actionButtonPanel;
    /**
     * El controlador per col·locar fitxes
     */
    private PlaceActionMaker placeActionMaker;
    /**
     * El controlador per passar el torn
     */
    private SkipActionMaker skipActionMaker;

    /***
     * Construeix un objecte {@code PlaceAction}.
     * @param boardView La vista del tauler de la partida per interactuar amb les peces seleccionades.
     * @param handView La vista on es mostren les fitxes del jugador
     */
    public PlaceAction(BoardView boardView, HandView handView) {
        setOpaque(false);
        this.boardView = boardView;
        this.handView = handView;
        createPlaceButton();
    }

    /**
     * Crea el botó "Place" que inicia el procés de col·locació.
     * Quan es prem, elimina aquest botó i mostra els botons "Confirm" i "Cancel".
     */
    private void createPlaceButton() {
        placeBtn = new JButton("Col·locar");
        placeBtn.addActionListener(_ -> startPlace());
        add(placeBtn);
    }
    /**
     * Inicia el procés de col·locació de peces al tauler.
     * Aquest mètode reemplaça el botó "Col·locar" pels botons "Confirmar" i "Cancel·lar",
     * i configura la lògica d'aquests dos últims botons.
     * - En prémer "Confirmar", s'intenta executar l'acció de col·locació via PlaceActionMaker.
     *   En cas d'error, es crida l'acció de passar torn.
     * - En prémer "Cancel·lar", es cancel·la la col·locació i es restaura la vista inicial.
     */
    private void startPlace() {
        confirmBtn = new JButton("Confirmar");
        confirmBtn.addActionListener(_ -> {
            Movement currentPlacement = boardView.getPlacement();
            if (currentPlacement == null)
                return;

            removeAll();
            add(placeBtn);
            try {
                placeActionMaker.run(currentPlacement);
            } catch (ScrabbleException _) {
                skipActionMaker.run();
            }
            boardView.endPlace();
            revalidate();
            repaint();
        });

        cancelBtn = new JButton("Cancel·lar");
        cancelBtn.addActionListener(_ -> {
            removeAll();
            add(placeBtn);
            actionButtonPanel.showButtons();
            boardView.endPlace();
            handView.cancelPlace();
            revalidate();
            repaint();
        });
        removeAll();
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(confirmBtn);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(cancelBtn);

        add(buttonPanel);

        actionButtonPanel.startPlace();
        boardView.startPlace();
        handView.startPlace();
        revalidate();
        repaint();
    }

    /**
     * Indica que aquest panell no és opac per permetre efectes de transparència.
     *
     * @return fals per indicar que el component no és opac.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }

    /**
     * Assigna el panell pare que conté aquest panell d'accions.
     * Això permet la comunicació i control entre els botons d'accions i el panell que els conté.
     *
     * @param actionButtonPanel Panell que conté aquest PlaceAction.
     */
    public void setParent(ActionButtonPanel actionButtonPanel) {
        this.actionButtonPanel = actionButtonPanel;
    }
    /**
     * Assigna els objectes que gestionen la lògica de les accions de col·locar i passar torn.
     * Així aquest component pot cridar la funcionalitat adequada quan s'activen els botons.
     *
     * @param place Objecte que gestiona la lògica per col·locar peces.
     * @param skip Objecte que gestiona la lògica per passar el torn.
     */
    public void setActionMaker(PlaceActionMaker place, SkipActionMaker skip) {
        placeActionMaker = place;
        skipActionMaker = skip;
    }
}

