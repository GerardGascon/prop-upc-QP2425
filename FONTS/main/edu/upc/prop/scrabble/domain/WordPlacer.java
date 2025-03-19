package edu.upc.prop.scrabble.domain;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

public class WordPlacer {
    private final Board board;
    private final IBoard view;

    public WordPlacer(Board board, IBoard view) {
        this.board = board;
        this.view = view;
    }

    public int run(Piece[] pieces, int x, int y, Direction direction) {
        Vector2[] positions;
        if (direction == Direction.Vertical)
            positions = placeWordVertical(pieces, x, y);
        else
            positions = placeWordHorizontal(pieces, x, y);

        int points = calculatePoints(positions);

        view.UpdateBoard();
        return points;
    }

    private int calculatePoints(Vector2[] positions) {
        return 0;
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
