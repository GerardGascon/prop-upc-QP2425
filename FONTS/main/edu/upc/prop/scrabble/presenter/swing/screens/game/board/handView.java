package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

public class handView extends JPanel implements IHandView {
    private final Map<JButton, Piece> pieceButtons = new HashMap<>();
    private String getSelectedPiece = null;
    private int selectedPiecePoints = 0;

    public handView(Player player) {
        setLayout(new GridLayout(1, player.getHand().length, 2, 2));
        for (Piece piece : player.getHand()) {
            JButton pieceButton = new JButton(piece.letter());
            pieceButtons.put(pieceButton, piece);
            pieceButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton clickedButton = (JButton) e.getSource();
                    Piece clickedPiece = pieceButtons.get(clickedButton);
                    if (getSelectedPiece != null && getSelectedPiece.equals(clickedPiece.letter())) {
                        getSelectedPiece = null;
                        selectedPiecePoints = 0;
                    } else {
                        getSelectedPiece = clickedPiece.letter();
                        selectedPiecePoints = clickedPiece.value();
                    }
                }
            });
            add(pieceButton);
        }
    }

    @Override
    public String getSelectedPiece() {
        return getSelectedPiece;

    }

    @Override
    public int getSelectedPiecePoints() {
        return selectedPiecePoints;
    }

}