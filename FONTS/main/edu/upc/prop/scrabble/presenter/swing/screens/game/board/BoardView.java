package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.domain.board.IBoard;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardCell;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardEmptyTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardPieceTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.BoardTile;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium.*;

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

    @Override
    public void setPremiumTile(PremiumTileType type, int x, int y) {
        if (x == y && x == size / 2){
            changeTile(new BoardCenterTile(x, y, handView), x, y);
            return;
        }

        switch (type) {
            case QuadrupleWord -> changeTile(new BoardQuadrupleWordTile(x, y, handView), x, y);
            case TripleWord -> changeTile(new BoardTripleWordTile(x, y, handView), x, y);
            case DoubleWord -> changeTile(new BoardDoubleWordTile(x, y, handView), x, y);
            case QuadrupleLetter -> changeTile(new BoardQuadrupleLetterTile(x, y, handView), x, y);
            case TripleLetter -> changeTile(new BoardTripleLetterTile(x, y, handView), x, y);
            case DoubleLetter -> changeTile(new BoardDoubleLetterTile(x, y, handView), x, y);
        }
    }
}
