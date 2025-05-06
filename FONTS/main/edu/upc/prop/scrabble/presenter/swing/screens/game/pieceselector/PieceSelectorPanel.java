package edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector;

import javax.swing.*;
import java.awt.*;

class PieceSelectorPanel extends JPanel {
    public PieceSelectorPanel(LayoutManager layout) {
        super(layout);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        super.paintComponent(g2);
        g2.setColor(new Color(0x2e, 0x3a, 0x3c));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 58, 58);
        g2.dispose();
    }

    @Override
    public boolean isOpaque() {
        return false;
    }
}
