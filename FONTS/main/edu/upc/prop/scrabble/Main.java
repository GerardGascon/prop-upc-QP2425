package edu.upc.prop.scrabble;

import com.google.gson.Gson;
import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.domain.PointCalculator;
import edu.upc.prop.scrabble.domain.WordGetter;
import edu.upc.prop.scrabble.domain.WordPlacer;
import edu.upc.prop.scrabble.presenter.terminal.BoardView;
import edu.upc.prop.scrabble.utils.Direction;

public class Main {
    public static void main(String[] args) {
        Board board = new SuperBoard();
        BoardView boardView = new BoardView(board);
        Piece[] pieces = new Piece[]{
                new Piece("P", 1),
                new Piece("O", 1),
                new Piece("T", 1),
                new Piece("A", 1),
                new Piece("T", 1),
                new Piece("O", 1)
        };
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        WordPlacer placer = new WordPlacer(board, boardView, pointCalculator);
        placer.run(pieces, 1, 2, Direction.Horizontal);
    }

    public float division(int a, int b) throws ArithmeticException {
        return a / b;
    }
}