package edu.upc.prop.scrabble.presenter.swing.screens.game.hand;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HandView extends JPanel implements IHandView {
    private final List<HandPieceButton> pieceButtons = new ArrayList<>();
    private HandPieceButton selectedPieceButton;

    public static final int HAND_PIECES_SPACING = 10;

    public HandView(Player player) {
        setLayout(new FlowLayout(FlowLayout.CENTER, HAND_PIECES_SPACING, 0));
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));

        displayPieces(player);
    }

    private void displayPieces(Player player) {
        for (Piece piece : player.getHand()) {
            HandPieceButton pieceButton = piece.isBlank()
                    ? new HandPieceButton("", piece.value())
                    : new HandPieceButton(piece.letter(), piece.value());

            pieceButtons.add(pieceButton);
            pieceButton.addActionListener(e -> {
                HandPieceButton clickedButton = (HandPieceButton) e.getSource();
                if (selectedPieceButton != null && selectedPieceButton.equals(clickedButton)) {
                    selectedPieceButton = null;
                } else {
                    selectedPieceButton = clickedButton;
                }
            });

            add(pieceButton);
        }
    }

    @Override
    public String getSelectedPiece() {
        return selectedPieceButton == null ? "" : selectedPieceButton.getLetter();
    }

    @Override
    public int getSelectedPiecePoints() {
        return selectedPieceButton == null ? 0 : selectedPieceButton.getPoints();
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