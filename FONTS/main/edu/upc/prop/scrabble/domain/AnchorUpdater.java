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
        if(move.direction() == Direction.Vertical) {
            for (int i = move.y(); i < size; i++) {
                        if(board.isCellValid(move.x()+1, i) && board.isCellEmpty(move.x()+1, i)) anchors.addAnchor(move.x()+1, i);
                        if(board.isCellValid(move.x()-1, i) && board.isCellEmpty(move.x()-1, i)) anchors.addAnchor(move.x()-1, i);
            }
            if(board.isCellValid(move.x(), move.y()-1) && board.isCellEmpty(move.x(), move.y()-1) ) anchors.addAnchor(move.x(), move.y()-1);
            if(board.isCellValid(move.x(), move.y()+size) && board.isCellEmpty(move.x(), move.y()+size)) anchors.addAnchor(move.x(), move.y()+size);
        }
        else if(move.direction() == Direction.Horizontal) {

        }

    }
}
