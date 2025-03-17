package edu.upc.prop.scrabble;

import com.google.gson.Gson;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.JuniorBoard;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.domain.WordPlacer;
import edu.upc.prop.scrabble.presenter.terminal.BoardView;
import edu.upc.prop.scrabble.utils.Direction;

public class Main {
    public static void main(String[] args) {
        Board board = new SuperBoard();
        BoardView boardView = new BoardView(board);
        WordPlacer placer = new WordPlacer(board, boardView);
        placer.run("POTATO", 1, 2, Direction.Horizontal);
    }

    public float division(int a, int b) throws ArithmeticException {
        return a / b;
    }
}