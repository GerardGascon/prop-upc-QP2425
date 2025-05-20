package edu.upc.prop.scrabble.presenter.swing.screens.game.hand;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class HandView extends JPanel implements IHandView {
    private final List<HandPieceButton> pieceButtons = new ArrayList<>();
    private final List<HandPieceButton> selectedPieceButtons = new ArrayList<>();
    private boolean allowMultiple = false;

    public static final int HAND_PIECES_SPACING = 10;

    public HandView(boolean allowMultiple) {
        this.allowMultiple = allowMultiple;
        setLayout(new FlowLayout(FlowLayout.CENTER, HAND_PIECES_SPACING, 0));
        setOpaque(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    public String[] getSelectedPiece() {
        if (selectedPieceButtons.isEmpty()) {
            return null;
        } else {
            ArrayList<String> letters = new ArrayList<>();
            for (HandPieceButton button : selectedPieceButtons) {
                letters.add(button.getLetter());
            }
            return letters.toArray(new String[0]);
        }
    }

    @Override
    public int getSelectedPiecePoints() {
        if (selectedPieceButtons.isEmpty()) {
            return 0;
        } else {
            int count = 0;
            for (HandPieceButton button : selectedPieceButtons) {
                count += button.getPoints();
            }
            return count;
        }
    }

    @Override
    public void showPieces(Piece[] pieces) {
        pieceButtons.clear();
        selectedPieceButtons.clear();
        removeAll();
        for (Piece piece : pieces) {
            HandPieceButton pieceButton = piece.isBlank()
                    ? new HandPieceButton("", piece.value())
                    : new HandPieceButton(piece.letter(), piece.value());

            pieceButtons.add(pieceButton);
            pieceButton.addActionListener(e -> {
                HandPieceButton clickedButton = (HandPieceButton) e.getSource();
                if (allowMultiple) {
                    if (clickedButton.isSelected()) {       // cas peça ja selecionada -> des-seleccionar-la
                        clickedButton.setSelected(false);
                        selectedPieceButtons.remove(clickedButton);
                    }
                    else {
                        clickedButton.setSelected(true);        // cas peça no seleccionada -> seleccionarla
                        selectedPieceButtons.add(clickedButton);
                    }
                } else {
                    if (selectedPieceButtons.contains(clickedButton)) { // cas per des-seleccionar una peça que es torna a seleccionar
                        clickedButton.setSelected(false);
                        selectedPieceButtons.clear();
                    }
                    else {
                        if (!selectedPieceButtons.isEmpty()) {      // cas des-seleccionar una altra peça
                            selectedPieceButtons.getFirst().setSelected(false);
                            selectedPieceButtons.clear();
                        }
                        clickedButton.setSelected(true);        // seleccionar la peça clickada
                        selectedPieceButtons.add(clickedButton);
                    }
                }
            });

            add(pieceButton);
        }
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