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
    private final HandView handView;
    private ActionButtonPanel actionButtonPanel;
    private PlaceActionMaker placeActionMaker;
    private SkipActionMaker skipActionMaker;

    /***
     * Construeix un objecte `PlaceAction`.
     * @param boardView La vista del tauler de la partida per interactuar amb les peces seleccionades.
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

    public void setParent(ActionButtonPanel actionButtonPanel) {
        this.actionButtonPanel = actionButtonPanel;
    }

    public void setActionMaker(PlaceActionMaker place, SkipActionMaker skip) {
        placeActionMaker = place;
        skipActionMaker = skip;
    }
}

