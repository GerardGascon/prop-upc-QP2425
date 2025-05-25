package edu.upc.prop.scrabble.presenter.swing.screens.game.hand;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Vista gràfica de la mà del jugador, que mostra les peces disponibles
 * i permet seleccionar-ne una per jugar-la.
 * Implementa la interfície {@link IHandView} per interactuar amb la lògica del joc.
 * @author Gina Escofet Gonzalez
 */
public class HandView extends JPanel implements IHandView {
    /**
     * Llista de botons que representen les peces de la mà del jugador.
     */
    private final List<HandPieceButton> pieceButtons = new ArrayList<>();
    /**
     * Referència a la peça seleccionada actualment (si n’hi ha una).
     * Només es pot seleccionar una peça a la vegada.
     */
    private HandPieceButton selectedPieceButton;
    /**
     * Espai horitzontal (en píxels) entre les peces de la mà del jugador.
     */
    public static final int HAND_PIECES_SPACING = 10;
    /**
     * Constructor. Configura el layout i la transparència del panell.
     */
    public HandView() {
        setLayout(new FlowLayout(FlowLayout.CENTER, HAND_PIECES_SPACING, 0));
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }
    /**
     * Retorna la lletra de la peça seleccionada actualment, si n'hi ha.
     */
    @Override
    public String[] getSelectedPiece() {
        if (selectedPieceButton == null)
            return null;

        return new String[] { selectedPieceButton.getLetter() };
    }
    /**
     * Retorna els punts de la peça seleccionada actualment, o 0 si no n'hi ha.
     */
    @Override
    public int getSelectedPiecePoints() {
        if (selectedPieceButton == null)
            return 0;

        return selectedPieceButton.getPoints();
    }
    /**
     * Mostra les peces de la mà. Crea botons per cada peça i gestiona la selecció.
     * @param pieces Array de peces a mostrar
     */
    @Override
    public void showPieces(Piece[] pieces) {
        pieceButtons.clear();
        selectedPieceButton = null;

        removeAll();
        for (Piece piece : pieces) {
            HandPieceButton pieceButton = piece.isBlank()
                    ? new HandPieceButton("", piece.value())
                    : new HandPieceButton(piece.letter(), piece.value());

            pieceButtons.add(pieceButton);
            pieceButton.addActionListener(e -> {
                HandPieceButton clickedButton = (HandPieceButton) e.getSource();
                if (selectedPieceButton == clickedButton) { // cas per des-seleccionar una peça que es torna a seleccionar
                    clickedButton.setSelected(false);
                    selectedPieceButton = null;
                } else {
                    if (selectedPieceButton != null) {      // cas des-seleccionar una altra peça
                        selectedPieceButton.setSelected(false);
                        selectedPieceButton = null;
                    }
                    clickedButton.setSelected(true);        // seleccionar la peça clickada
                    selectedPieceButton = pieceButton;
                }
            });

            add(pieceButton);
        }
        revalidate();
        repaint();
    }
    /**
     * Elimina la peça seleccionada de la mà (després que hagi estat jugada).
     */
    @Override
    public void piecePlaced() {
        remove(selectedPieceButton);
        selectedPieceButton = null;
        revalidate();
        repaint();
    }
    /**
     * Pinta el fons arrodonit de la mà del jugador.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        super.paintComponent(g);
        g2.dispose();
    }
    /**
     * Estableix la mida de totes les peces visibles a la mà.
     *
     * @param handPieceSize mida (amplada i alçada) de cada peça
     */
    public void setPieceSize(int handPieceSize) {
        Dimension size = new Dimension(handPieceSize, handPieceSize);
        for (JButton pieceButton : pieceButtons) {
            pieceButton.setPreferredSize(size);
            pieceButton.setMinimumSize(size);
            pieceButton.setMaximumSize(size);
        }
    }
}