package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.draw;

import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;

import javax.swing.*;

/**
 * Classe per gestionar la lògica del botó de Draw (Robar) en una partida.
 * @author Gina Escofet González
 */
public class DrawAction extends JPanel {
    /**
     * Panell contenidor on s'afegeixen i treuen els botons (Draw / Confirm).
     */
    private final JPanel parent;
    /**
     * Botó principal que permet iniciar l'acció de robar peces.
     */
    private JButton drawBtn;
    /**
     * Gestor de la lògica que executa l'acció de robar peces un cop confirmada.
     */
    private final DrawActionMaker drawActionMaker;
    /**
     * Vista de la mà del jugador, des d'on es recuperen les peces seleccionades per intercanviar.
     */
    private final HandView handView;

    /**
     * Construeix un objecte `DrawAction`.
     * @param parent El panell pare on s'afegirà aquest component.
     * @param drawActionMaker L'objecte responsable de gestionar la lògica de robar peces.
     * @param handView La vista de la mà del jugador per interactuar amb les peces seleccionades.
     */
    public DrawAction(JPanel parent, DrawActionMaker drawActionMaker, HandView handView) {
        this.drawActionMaker = drawActionMaker;
        this.parent = parent;
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
        drawBtn.addActionListener(e -> {
            parent.remove(drawBtn);
            parent.revalidate();
            parent.repaint();

            JButton confirmBtn = new JButton("Confirm");
            confirmBtn.setBounds(1400, 575, 75, 50); //hardcoded
            confirmBtn.addActionListener(confirmEvent -> {
                String[] selectedPieces = handView.getSelectedPiece();
                if (selectedPieces != null && selectedPieces.length > 0) {
                    drawActionMaker.run(selectedPieces);
                } else {
                    System.out.println("No pieces to draw.");
                }
                parent.remove(confirmBtn);
                parent.add(drawBtn);
                parent.revalidate();
                parent.repaint();
            });
            parent.add(confirmBtn);
            parent.revalidate();
            parent.repaint();
        });
        parent.add(drawBtn);
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
}
