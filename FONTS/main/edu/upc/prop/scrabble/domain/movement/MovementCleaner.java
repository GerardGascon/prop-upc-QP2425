package edu.upc.prop.scrabble.domain.movement;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;

import java.util.ArrayList;

public class MovementCleaner {
    private final Board board;
    private final PiecesConverter piecesConverter;
    public MovementCleaner(Board board, PiecesConverter piecesConverter) {
        this.board = board;
        this.piecesConverter = piecesConverter;
    }

    public Piece[] run(Movement movement) {
        Direction direction = movement.direction();
        if (direction == Direction.Vertical) {
            return CleanVertical(movement);
        }
        else {
           return CleanHorizontal(movement);
        }
    }

    private Piece[] CleanHorizontal(Movement movement) {
        ArrayList<Piece> requiredPieces= new ArrayList<Piece>();
        int x = movement.x();
        int y = movement.y();
        Piece[] allPieces = piecesConverter.run(movement.word());
        int n = allPieces.length;
        for (int i = 0; i < n; ++i) {
            if (board.isCellEmpty(x, y + i)) {
                requiredPieces.add(allPieces[i]);
            }
            else {
                Piece pieceInCell = board.getCellPiece(x, y + i);
                if (pieceInCell == allPieces[i]) {
                    continue;
                }
                // else throw exception
            }
        }
        return requiredPieces.toArray(new Piece[0]);

    }

    private Piece[] CleanVertical(Movement movement) {
        ArrayList<Piece> requiredPieces= new ArrayList<Piece>();
        int x = movement.x();
        int y = movement.y();
        Piece[] allPieces = piecesConverter.run(movement.word());
        int n = allPieces.length;
        for (int i = 0; i < n; ++i) {
            if (board.isCellEmpty(x + i, y)) {
                requiredPieces.add(allPieces[i]);
            }
            else {
                Piece pieceInCell = board.getCellPiece(x + i, y);
                if (pieceInCell == allPieces[i]) {
                    continue;
                }
                // else throw exception
            }
        }
        return requiredPieces.toArray(new Piece[0]);
    }
}
