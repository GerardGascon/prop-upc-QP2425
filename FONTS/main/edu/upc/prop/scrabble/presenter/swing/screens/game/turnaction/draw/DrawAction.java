package edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.draw;

import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.presenter.terminal.actionmaker.HandView;

import javax.swing.*;

public class DrawAction extends JPanel {
    private final JPanel parent;
    private JButton drawBtn;
    private final DrawActionMaker drawActionMaker;
    private final HandView handView;

    public DrawAction(JPanel parent, DrawActionMaker drawActionMaker, HandView handView) {
        this.drawActionMaker = drawActionMaker;
        this.parent = parent;
        this.handView = handView;
        setOpaque(false);
        createDrawButton();
        add(drawBtn);
    }

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

    @Override
    public boolean isOpaque() {
        return false;
    }
}
