package edu.upc.prop.scrabble.presenter.swing.screens.game.hand;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HandView extends JPanel implements IHandView {
    private final List<HandPieceButton> pieceButtons = new ArrayList<>();
    private HandPieceButton selectedPieceButton;

    public static final int HAND_PIECES_SPACING = 10;

    public HandView() {
        setLayout(new FlowLayout(FlowLayout.CENTER, HAND_PIECES_SPACING, 0));
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public String[] getSelectedPiece() {
        if (selectedPieceButton == null)
            return null;

        return new String[] { selectedPieceButton.getLetter() };
    }

    @Override
    public int getSelectedPiecePoints() {
        if (selectedPieceButton == null)
            return 0;

        return selectedPieceButton.getPoints();
    }

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

    @Override
    public void piecePlaced() {
        remove(selectedPieceButton);
        selectedPieceButton = null;
        revalidate();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g.setColor(getBackground());
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        super.paintComponent(g);
        g2.dispose();
    }

    public void setPieceSize(int handPieceSize) {
        Dimension size = new Dimension(handPieceSize, handPieceSize);
        for (JButton pieceButton : pieceButtons) {
            pieceButton.setPreferredSize(size);
            pieceButton.setMinimumSize(size);
            pieceButton.setMaximumSize(size);
        }
    }
}