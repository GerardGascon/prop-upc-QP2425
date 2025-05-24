package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.place;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;

import javax.swing.*;

/**
 * Classe per gestionar la lògica del botó de Place (Posar) en una partida.
 * @author Gina Escofet González
 */
public class PlaceAction extends JPanel {
    private final JPanel parent;
    private JButton placeBtn;
    private JButton confirmBtn;
    private JButton cancelBtn;
    private final PlaceActionMaker placeActionMaker;
    private final BoardView boardView;
    private final HandView handView;

    /***
     * Construeix un objecte `PlaceAction`.
     * @param parent El panell pare on s'afegirà aquest component.
     * @param placeActionMaker L'objecte responsable de gestionar la lògica de robar peces.
     * @param boardView La vista del tauler de la partida per interactuar amb les peces seleccionades.
     * @param handView La vista de la mà del jugador per interactuar amb les peces seleccionades.
     */
    public PlaceAction(JPanel parent, PlaceActionMaker placeActionMaker, BoardView boardView, HandView handView) {
        setOpaque(false);
        this.boardView = boardView;
        this.handView = handView;
        this.placeActionMaker = placeActionMaker;
        this.parent = parent;
        createPlaceButton();
        add(placeBtn);
    }

    private void createPlaceButton() {
        placeBtn = new JButton("Place");
        placeBtn.setBounds(1400, 575, 75, 50); //hardcoded
        placeBtn.addActionListener(e -> {
            parent.remove(placeBtn);
            parent.revalidate();
            parent.repaint();

            confirmBtn = new JButton("Confirm");
            confirmBtn.setBounds(1400, 575, 75, 50); //hardcoded
            confirmBtn.addActionListener(confirmEvent -> {
                //Movement currentPlacement = boardView.getPlacement();
                //placeActionMaker.run(currentPlacement);
            });
            parent.add(confirmBtn);

            cancelBtn = new JButton("Cancel");
            cancelBtn.setBounds(1400, 575, 75, 50); //hardcoded
            cancelBtn.addActionListener(confirmEvent -> {
                //Piece[] returnedPieces = boardView.rollbackPlacement();
                //handView.addPieces(returnedPieces);
            });

            parent.add(cancelBtn);
            parent.revalidate();
            parent.repaint();
        });
        parent.add(placeBtn);
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}

