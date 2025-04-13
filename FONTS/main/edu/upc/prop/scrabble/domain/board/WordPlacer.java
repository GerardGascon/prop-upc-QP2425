package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.IBoard;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

public class WordPlacer {
    private final Board board;
    private final IBoard view;
    private final PointCalculator pointCalculator;

    public WordPlacer(Board board, IBoard view, PointCalculator pointCalculator) {
        this.board = board;
        this.view = view;
        this.pointCalculator = pointCalculator;
    }

    public int run(Piece[] pieces, int x, int y, Direction direction) {
        Vector2[] positions;
        if (direction == Direction.Vertical)
            positions = placeWordVertical(pieces, x, y);
        else
            positions = placeWordHorizontal(pieces, x, y);

        int points = pointCalculator.run(positions, pieces);

        view.UpdateBoard(board);
        return points;
    }

    private Vector2[] placeWordVertical(Piece[] pieces, int x, int y) {
        Vector2[] positions = new Vector2[pieces.length];

        for (int i = 0; i < pieces.length; i++) {
            Vector2 pos = new Vector2(x, y + i);
            board.placePiece(pieces[i], pos.x, pos.y);
            positions[i] = pos;
        }
        return positions;
    }

    private Vector2[] placeWordHorizontal(Piece[] pieces, int x, int y) {
        Vector2[] positions = new Vector2[pieces.length];

        for (int i = 0; i < pieces.length; i++) {
            Vector2 pos = new Vector2(x + i, y);
            board.placePiece(pieces[i], pos.x, pos.y);
            positions[i] = pos;
        }
        return positions;
    }
}
