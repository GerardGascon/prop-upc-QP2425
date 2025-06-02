package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction;

import edu.upc.prop.scrabble.data.exceptions.ScrabbleException;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;

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
    /**
     * Botó de confirmar l'intercanvi d'una fitxa
     */
    private JButton confirmBtn;
    /**
     * Botó per finalitzar l'intercanvi
     */
    private JButton finishBtn;
    /**
     * Botó per cancelar l'intercanvi
     */
    private JButton cancelBtn;

    /**
     * Vista de la mà del jugador, des d'on es recuperen les peces seleccionades per intercanviar.
     */
    private final HandView handView;
    /**
     * Panell on es troben tots els botons d'accions
     */
    private ActionButtonPanel actionButtonPanel;
    /**
     * Controlador que s'encarrega d'executar l'acció de robar fitxes
     */
    private DrawActionMaker drawActionMaker;
    /**
     * Controlador que s'encarrega d'executar l'acció de passar el torn
     */
    private SkipActionMaker skipActionMaker;
    /**
     * Llista de les fitxes a intercanviar
     */
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
        drawBtn = new JButton("Robar");
        drawBtn.addActionListener(_ -> startDraw());
    }

    /**
     * Inicia l'acció de robar fitxes
     */
    private void startDraw() {
        confirmBtn = new JButton("Confirmar");
        confirmBtn.addActionListener(_ -> {
            handView.getSelectedPiece();
            if (handView.getSelectedPiece() != null) {
                pieces.add(handView.getSelectedPiece());
                handView.piecePlaced();
            }
        });
        finishBtn = new JButton("Acabar");
        finishBtn.addActionListener(_ -> {
                drawPieces();
                removeAll();
                add(drawBtn);
                actionButtonPanel.showButtons();
                revalidate();
                repaint();
        });

        cancelBtn = new JButton("Cancel·lar");
        cancelBtn.addActionListener(_ -> {
                    removeAll();
                    add(drawBtn);
                    actionButtonPanel.showButtons();
                    revalidate();
                    repaint();
                    handView.cancelPlace();
        });

        pieces.clear();
        removeAll();
        handView.startPlace();

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(confirmBtn);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(finishBtn);
        buttonPanel.add(Box.createVerticalStrut(5));
        buttonPanel.add(cancelBtn);

        add(buttonPanel);

        actionButtonPanel.startDraw();
        revalidate();
        repaint();
    }

    /**
     * Executa l'acció de robar fitxes
     */
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
     *
     * @return false sempre
     */
    @Override
    public boolean isOpaque() {
        return false;
    }

    /**
     * Assigna el panell de botons on es troba aquesta classe
     *
     * @param actionButtonPanel el panell de botons d'acció
     */
    public void setParent(ActionButtonPanel actionButtonPanel) {
        this.actionButtonPanel = actionButtonPanel;
    }

    /**
     * Assigna els controladors d'accions
     *
     * @param draw Controlador per robar fitxes
     * @param skip Controlador per passar el torn
     */
    public void setActionMaker(DrawActionMaker draw, SkipActionMaker skip) {
        drawActionMaker = draw;
        skipActionMaker = skip;
    }
}
