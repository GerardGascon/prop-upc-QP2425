package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.domain.board.IBoard;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardCell;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardEmptyTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardPieceTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel implements IBoard {
    private final int size;
    private final IHandView handView;

    public BoardView(int size, IHandView handView) {
        super();
        setLayout(new GridLayout(size, size, 2, 2));
        setBackground(new Color(0x50, 0x84, 0x6e));

        this.size = size;
        this.handView = handView;

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
        BoardCell cell = (BoardCell)(getComponent(y * size + x));
        cell.setTile(tile);
    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void updateCell(String piece, int points, int x, int y) {
        changeTile(new BoardPieceTile(piece, points, x, y, handView), x, y);
    }
}
