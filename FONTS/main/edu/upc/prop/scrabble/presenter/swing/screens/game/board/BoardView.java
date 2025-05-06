package edu.upc.prop.scrabble.presenter.swing.screens.game.board;

import edu.upc.prop.scrabble.data.board.PremiumTileType;
import edu.upc.prop.scrabble.domain.board.IBoard;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.*;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.tiles.premium.*;

import javax.swing.*;
import java.awt.*;

public class BoardView extends JPanel implements IBoard {
    private final int size;
    private final IHandView handView;

    public BoardView(int size, IHandView handView) {
        super();
        setOpaque(false);
        setLayout(new GridLayout(size + 2, size + 2, 2, 2));
        setBackground(new Color(0x51, 0x78, 0x85));

        this.size = size;
        this.handView = handView;

        generateEmptyTiles(size, handView);
    }

    private void generateEmptyTiles(int size, IHandView handView) {
        for (int i = 0, j = 0; i < (size + 2) * (size + 2); i++) {
            int borderRow = i / (size + 2);
            int borderCol = i % (size + 2);

            if (borderRow == 0 || borderRow == size + 1) {
                add(new BoardCoordinateTile(getBorderCol(borderCol)));
                continue;
            }

            if (borderCol == 0 || borderCol == size + 1) {
                add(new BoardCoordinateTile(getBorderRow(borderRow)));
                continue;
            }

            int row = j / size;
            int col = j % size;
            j++;

            BoardCell cell = new BoardCell();
            cell.setTile(new BoardEmptyTile(col, row, handView, this));
            add(cell);
        }
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

    private String getBorderRow(int row) {
        if (row == 0 || row == size + 1)
            return "";
        return Integer.toString(row);
    }

    private String getBorderCol(int col) {
        if (col == 0 || col == size + 1)
            return "";
        return String.valueOf((char)('A' + col - 1));
    }

    public void changeTile(BoardTile tile, int x, int y) {
        BoardCell cell = getCell(x, y);
        cell.setTile(tile);
    }

    public BoardCell getCell(int x, int y) {
        return getComponentOfType(BoardCell.class, y * size + x);
    }

    public <TComponent extends Component> TComponent getComponentOfType(Class<TComponent> clazz, int index) {
        int count = 0;
        for (Component comp : getComponents()) {
            if (clazz.isInstance(comp)) {
                if (count == index)
                    return clazz.cast(comp);
                count++;
            }
        }
        throw new RuntimeException("No component found for " + clazz.getName());
    }

    public void placeTemporalPiece(String piece, int points, int x, int y) {
        BoardCell cell = getCell(x, y);
        if (cell.getTile().getClass() == BoardEmptyTile.class) {
            cell.setTile(new BoardTemporalPieceTile(piece, points, x, y, handView, this));
        }
    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void updateCell(String piece, int points, int x, int y) {
        changeTile(new BoardPieceTile(piece, points, x, y, handView, this), x, y);
    }

    @Override
    public void setPremiumTile(PremiumTileType type, int x, int y) {
        if (x == y && x == size / 2){
            changeTile(new BoardCenterTile(x, y, handView, this), x, y);
            return;
        }

        switch (type) {
            case QuadrupleWord -> changeTile(new BoardQuadrupleWordTile(x, y, handView, this), x, y);
            case TripleWord -> changeTile(new BoardTripleWordTile(x, y, handView, this), x, y);
            case DoubleWord -> changeTile(new BoardDoubleWordTile(x, y, handView, this), x, y);
            case QuadrupleLetter -> changeTile(new BoardQuadrupleLetterTile(x, y, handView, this), x, y);
            case TripleLetter -> changeTile(new BoardTripleLetterTile(x, y, handView, this), x, y);
            case DoubleLetter -> changeTile(new BoardDoubleLetterTile(x, y, handView, this), x, y);
        }
    }
}
