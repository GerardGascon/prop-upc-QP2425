package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardCell;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardEmptyTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel {
    private final int size;

    public BoardView(int size, IHandView handView) {
        super();
        setLayout(new GridLayout(size, size, 2, 2));
        setBackground(new Color(0x50, 0x84, 0x6e));

        this.size = size;

        for (int i = 0; i < size * size; i++) {
            int row = i / size;
            int col = i % size;

            BoardCell cell = new BoardCell();
            BoardTile tile = new BoardEmptyTile(col, row, handView);
            cell.setTile(tile);

            add(cell);
        }
    }

    public void changeTile(BoardTile tile, int x, int y) {
        BoardCell cell = (BoardCell)(getComponent(x * size + y));

        cell.setTile(tile);
    }
}
