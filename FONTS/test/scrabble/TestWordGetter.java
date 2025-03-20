package scrabble;

import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.StandardBoard;
import edu.upc.prop.scrabble.domain.WordGetter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestWordGetter {
    @Test
    public void getVerticalWord(){
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 1), 0, 0);
        board.placePiece(new Piece("E", 1), 0, 1);
        board.placePiece(new Piece("S", 1), 0, 2);
        board.placePiece(new Piece("T", 1), 0, 3);
        WordGetter sut = new WordGetter(board);

        Piece[] pieces = sut.run(new Piece("S", 1), new Vector2(0, 4), Direction.Vertical);

        assertEquals(5, pieces.length);
        assertEquals(new Piece("T", 1), pieces[0]);
        assertEquals(new Piece("E", 1), pieces[1]);
        assertEquals(new Piece("S", 1), pieces[2]);
        assertEquals(new Piece("T", 1), pieces[3]);
        assertEquals(new Piece("S", 1), pieces[4]);
    }

    @Test
    public void getVerticalWordWithTwoWordsInline(){
        Board board = new StandardBoard();
        board.placePiece(new Piece("N", 1), 0, 0);
        board.placePiece(new Piece("O", 1), 0, 1);

        board.placePiece(new Piece("T", 1), 0, 6);
        board.placePiece(new Piece("E", 1), 0, 7);
        board.placePiece(new Piece("S", 1), 0, 8);
        board.placePiece(new Piece("T", 1), 0, 9);
        WordGetter sut = new WordGetter(board);

        Piece[] pieces = sut.run(new Piece("S", 1), new Vector2(0, 10), Direction.Vertical);

        assertEquals(5, pieces.length);
        assertEquals(new Piece("T", 1), pieces[0]);
        assertEquals(new Piece("E", 1), pieces[1]);
        assertEquals(new Piece("S", 1), pieces[2]);
        assertEquals(new Piece("T", 1), pieces[3]);
        assertEquals(new Piece("S", 1), pieces[4]);
    }

    @Test
    public void getHorizontalWord(){
        Board board = new StandardBoard();
        board.placePiece(new Piece("T", 1), 0, 0);
        board.placePiece(new Piece("E", 1), 1, 0);
        board.placePiece(new Piece("S", 1), 2, 0);
        board.placePiece(new Piece("T", 1), 3, 0);
        WordGetter sut = new WordGetter(board);

        Piece[] pieces = sut.run(new Piece("S", 1), new Vector2(4, 0), Direction.Horizontal);

        assertEquals(5, pieces.length);
        assertEquals(new Piece("T", 1), pieces[0]);
        assertEquals(new Piece("E", 1), pieces[1]);
        assertEquals(new Piece("S", 1), pieces[2]);
        assertEquals(new Piece("T", 1), pieces[3]);
        assertEquals(new Piece("S", 1), pieces[4]);
    }

    @Test
    public void getHorizontalWordWithTwoWordsInline(){
        Board board = new StandardBoard();
        board.placePiece(new Piece("N", 1), 0, 0);
        board.placePiece(new Piece("O", 1), 1, 0);

        board.placePiece(new Piece("T", 1), 6, 0);
        board.placePiece(new Piece("E", 1), 7, 0);
        board.placePiece(new Piece("S", 1), 8, 0);
        board.placePiece(new Piece("T", 1), 9, 0);
        WordGetter sut = new WordGetter(board);

        Piece[] pieces = sut.run(new Piece("S", 1), new Vector2(10, 0), Direction.Horizontal);

        assertEquals(5, pieces.length);
        assertEquals(new Piece("T", 1), pieces[0]);
        assertEquals(new Piece("E", 1), pieces[1]);
        assertEquals(new Piece("S", 1), pieces[2]);
        assertEquals(new Piece("T", 1), pieces[3]);
        assertEquals(new Piece("S", 1), pieces[4]);
    }
}
