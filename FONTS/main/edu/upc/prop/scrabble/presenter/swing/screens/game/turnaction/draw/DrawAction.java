package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.draw;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.ActionButtonPanel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe per gestionar la lògica del botó de Draw (Robar) en una partida.
 * @author Gina Escofet González
 */
public class DrawAction extends JPanel {
    /**
     * Botó principal que permet iniciar l'acció de robar peces.
     */
    private JButton drawBtn;
    private JButton confirmBtn;
    private JButton finishBtn;
    /**
     * Vista de la mà del jugador, des d'on es recuperen les peces seleccionades per intercanviar.
     */
    private final HandView handView;
    private ActionButtonPanel actionButtonPanel;
    private DrawActionMaker drawActionMaker;
    private SkipActionMaker skipActionMaker;
    private final List<String> pieces = new ArrayList<>();

    /**
     * Construeix un objecte `DrawAction`.
     * @param handView La vista de la mà del jugador per interactuar amb les peces seleccionades.
     */
    public DrawAction(HandView handView) {
        this.handView = handView;
        setOpaque(false);
        createDrawButton();
        add(drawBtn);
    }
    /**
     * Crea el botó de "Draw" i configura el seu comportament.
     * Quan es prem, el botó canvia a un botó "Confirm" per executar l'acció real.
     */
    private void createDrawButton() {
        drawBtn = new JButton("Draw");
        drawBtn.setBounds(1400, 575, 75, 50); //hardcoded
        drawBtn.addActionListener(_ -> startDraw());
    }

    private void startDraw() {
        confirmBtn = new JButton("Confirm");
        confirmBtn.setBounds(1400, 575, 75, 50); //hardcoded
        confirmBtn.addActionListener(_ -> {
            handView.getSelectedPiece();
            if (handView.getSelectedPiece() != null) {
                pieces.add(handView.getSelectedPiece());
                handView.piecePlaced();
            }
        });
        finishBtn = new JButton("Finish");
        finishBtn.setBounds(1400, 575, 75, 50); //hardcoded
        finishBtn.addActionListener(_ -> drawPieces());
        pieces.clear();
        remove(drawBtn);
        add(confirmBtn);
        add(finishBtn);
        actionButtonPanel.startDraw();
    }

    private void drawPieces() {
        if (pieces.isEmpty())
            return;

        try {
            drawActionMaker.run(pieces.toArray(String[]::new));
        } catch (ScrabbleException _) {
            skipActionMaker.run();
        }
        add(drawBtn);
        remove(confirmBtn);
        remove(finishBtn);
    }

    /**
     * Indica si aquest panell és opac.
     * Retorna sempre fals per assegurar que el fons sigui transparent,
     * evitant que el panell dibuixi un fons sòlid que pugui tapar altres components visuals.
     */
    @Override
    public boolean isOpaque() {
        return false;
    }

    public void setParent(ActionButtonPanel actionButtonPanel) {
        this.actionButtonPanel = actionButtonPanel;
    }

    public void setActionMaker(DrawActionMaker draw, SkipActionMaker skip) {
        drawActionMaker = draw;
        skipActionMaker = skip;
    }
}
