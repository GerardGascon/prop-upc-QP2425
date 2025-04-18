package edu.upc.prop.scrabble.domain.board;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

/**
 * The WordPlacer class is responsible for placing words (pieces) on the board and updating the scores.
 * It triggers a refresh on the view after the word is placed.
 *
 * @author Gerard Gascón
 */
public class WordPlacer {
    private final Board board;
    private final IBoard view;
    private final PointCalculator pointCalculator;
    private final Player player;

    /**
     * Constructs a WordPlacer instance with the specified player, board, view, and point calculator.
     *
     * @param player          The player who is making the move.
     * @param board           The game board where the pieces will be placed.
     * @param view            The view that will be updated after the move.
     * @param pointCalculator The point calculator used to calculate the score for the move.
     */
    public WordPlacer(Player player, Board board, IBoard view, PointCalculator pointCalculator) {
        this.player = player;
        this.board = board;
        this.view = view;
        this.pointCalculator = pointCalculator;
    }

    /**
     * Places the given pieces on the board and updates the player's score and the view.
     * <p>
     * This method calculates the positions for each piece based on the starting position and direction
     * (horizontal or vertical), places the pieces on the board, calculates the score using the PointCalculator,
     * and updates the board view.
     *
     * @param pieces    An array of the new pieces to place on the board.
     * @param x         The X position of the start of the word.
     * @param y         The Y position of the start of the word.
     * @param direction The direction in which the word will be placed (horizontal or vertical).
     */
    public void run(Piece[] pieces, int x, int y, Direction direction) {
        Vector2[] positions;
        if (direction == Direction.Vertical)
            positions = placeWordVertical(pieces, x, y);
        else
            positions = placeWordHorizontal(pieces, x, y);

        int points = pointCalculator.run(positions, pieces);
        player.addScore(points);

        view.updateBoard(board);
    }

    private Vector2[] placeWordVertical(Piece[] pieces, int x, int y) {
        Vector2[] positions = new Vector2[pieces.length];

        for (int i = y, j = 0; i < board.getSize(); i++) {
            Vector2 pos = new Vector2(x, i);
            if (board.getCellPiece(pos.x, pos.y) != null)
                continue;
            board.placePiece(pieces[j], pos.x, pos.y);
            positions[j] = pos;
            j++;
            if (j == pieces.length)
                break;
        }
        return positions;
    }

    private Vector2[] placeWordHorizontal(Piece[] pieces, int x, int y) {
        Vector2[] positions = new Vector2[pieces.length];

        for (int i = x, j = 0; i < board.getSize(); i++) {
            Vector2 pos = new Vector2(i, y);
            if (board.getCellPiece(pos.x, pos.y) != null)
                continue;
            board.placePiece(pieces[j], pos.x, pos.y);
            positions[j] = pos;
            j++;
            if (j == pieces.length)
                break;
        }
        return positions;
    }
}
