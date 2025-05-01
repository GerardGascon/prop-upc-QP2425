package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardEmptyTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardPieceTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;
import edu.upc.prop.scrabble.utils.Rand;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private final IHandView handView;

    public BoardView(int size, IHandView handView) {
        super();
        setLayout(new GridLayout(size, size, 2, 2));
        setBackground(new Color(0x50, 0x84, 0x6e));

        this.handView = handView;

        for (int i = 0; i < size * size; i++) {
            Rand rand = new Rand();
            BoardTile cell;
            if (rand.nextInt() % 2 == 0)
                cell = new BoardPieceTile();
            else
                cell = new BoardEmptyTile();
            int row = i / size;
            int col = i % size;
            cell.addActionListener(_ -> cellClicked(col, row));
            add(cell);
        }
    }

    private void cellClicked(int x, int y) {
        String piece = handView.getSelectedPiece();
    }
}
