package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;

public class AnchorUpdater {
    private final Anchors anchors;
    private final Board board;
    private final PiecesConverter piecesConverter;

    public AnchorUpdater(Anchors anchors, Board board, PiecesConverter piecesConverter) {
        this.anchors = anchors;
        this.board = board;
        this.piecesConverter = piecesConverter;
        this.anchors.addAnchor(board.getSize() / 2, board.getSize() / 2);
    }

    public void run(Movement move) {
        int size = piecesConverter.run(move.word()).length;

        if (move.direction() == Direction.Vertical)
            updateVerticalAnchors(move.x(), move.y(), size);
        if (move.direction() == Direction.Horizontal)
            updateHorizontalAnchors(move.x(), move.y(), size);
    }

    private void updateHorizontalAnchors(int x, int y, int size) {
        if (board.isCellValid(x - 1, y) && board.isCellEmpty(x - 1, y))
            anchors.addAnchor(x - 1, y); // Previous to first
        if (board.isCellValid(x + size, y) && board.isCellEmpty(x + size, y))
            anchors.addAnchor(x + size, y); // Next to last

        for (int i = x; i < x + size; i++) {
            if (board.isCellValid(i, y) && anchors.exists(i, y))
                anchors.removeAnchor(i, y);
            if (board.isCellValid(i, y + 1) && board.isCellEmpty(i, y + 1))
                anchors.addAnchor(i, y + 1);
            if (board.isCellValid(i, y - 1) && board.isCellEmpty(i, y - 1))
                anchors.addAnchor(i, y - 1);
        }
    }

    private void updateVerticalAnchors(int x, int y, int size) {
        if (board.isCellValid(x, y - 1) && board.isCellEmpty(x, y - 1))
            anchors.addAnchor(x, y - 1);
        if (board.isCellValid(x, y + size) && board.isCellEmpty(x, y + size))
            anchors.addAnchor(x, y + size);

        for (int i = y; i < y + size; i++) {
            if (board.isCellValid(x, i) && anchors.exists(x, i))
                anchors.removeAnchor(x, i);
            if (board.isCellValid(x + 1, i) && board.isCellEmpty(x + 1, i))
                anchors.addAnchor(x + 1, i);
            if (board.isCellValid(x - 1, i) && board.isCellEmpty(x - 1, i))
                anchors.addAnchor(x - 1, i);
        }
    }
}