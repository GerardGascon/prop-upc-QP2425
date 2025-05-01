package edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles;

import javax.swing.*;
import java.awt.*;

public class BoardCell extends JPanel {
    private BoardTile tile;

    public BoardCell() {
        setOpaque(false);
        setLayout(new BorderLayout());
    }

    public void setTile(BoardTile tile) {
        if (this.tile != null)
            remove(this.tile);

        this.tile = tile;
        add(tile);

        revalidate();
        repaint();
    }

    public BoardTile getTile() {
        return tile;
    }
}
