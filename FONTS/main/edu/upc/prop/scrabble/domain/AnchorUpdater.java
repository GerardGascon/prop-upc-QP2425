package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;

public class AnchorUpdater {
    Anchors anchors;
    Board board;
    PiecesConverter piecesConverter;

    //poner piece converter segun el idioma
    public AnchorUpdater(Anchors anchors, Board board, PiecesConverter piecesConverter) {
        this.anchors = anchors;
        this.board = board;
        this.piecesConverter = piecesConverter;
    }

    public void run(Movement move) {

        int size = piecesConverter.run(move.word()).length;
        int x = move.x();
        int y = move.y();

        if(move.direction() == Direction.Vertical) {
            if(board.isCellValid(x, y - 1) && board.isCellEmpty(x, y - 1) ) anchors.addAnchor(x, y - 1); // Previous to first
            if(board.isCellValid(x, y + size) && board.isCellEmpty(x, y + size)) anchors.addAnchor(x, y + size); // Next to last
            for (int i_y = y; i_y < y+size; i_y++) {
                if(board.isCellValid(x, i_y)  && anchors.exists(x, i_y)) anchors.removeAnchor(x, i_y);
                if(board.isCellValid(x + 1, i_y) && board.isCellEmpty(x + 1, i_y)) anchors.addAnchor(x + 1, i_y);
                if(board.isCellValid(x - 1, i_y) && board.isCellEmpty(x - 1, i_y)) anchors.addAnchor(x - 1, i_y);
            }
        }
        else if(move.direction() == Direction.Horizontal) {
            if(board.isCellValid(x - 1, y) && board.isCellEmpty(x - 1, y) ) anchors.addAnchor(x - 1, y); // Previous to first
            if(board.isCellValid(x + size, y) && board.isCellEmpty(x + size, y)) anchors.addAnchor(x + size, y); // Next to last
            for (int i_x = x; i_x < x+size; i_x++) {
                if(board.isCellValid(i_x, y) && anchors.exists(i_x, y)) anchors.removeAnchor(i_x, y);
                if(board.isCellValid(i_x, y + 1) && board.isCellEmpty(i_x, y + 1)) anchors.addAnchor(i_x, y + 1);
                if(board.isCellValid(i_x, y - 1) && board.isCellEmpty(i_x, y - 1)) anchors.addAnchor(i_x, y - 1);
            }
        }
    }
}