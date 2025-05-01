package scrabble.board;

import edu.upc.prop.scrabble.data.board.*;
import edu.upc.prop.scrabble.domain.board.PremiumTileTypeFiller;
import org.junit.Test;
import scrabble.stubs.BoardViewStub;

import static org.junit.Assert.assertEquals;

public class TestPremiumTileTypeFiller {
    @Test
    public void tileGetsSetAtCorrectPosition_StandardBoard() {
        Board board = new StandardBoard();
        BoardViewStub stub = new BoardViewStub();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, stub);

        filler.run();

        assertEquals(61, stub.getSetTileTypeCallReceived());
    }

    @Test
    public void tileGetsSetAtCorrectPosition_SuperBoard() {
        Board board = new SuperBoard();
        BoardViewStub stub = new BoardViewStub();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, stub);

        filler.run();

        assertEquals(125, stub.getSetTileTypeCallReceived());
    }

    @Test
    public void tileGetsSetAtCorrectPosition_JuniorBoard() {
        Board board = new JuniorBoard();
        BoardViewStub stub = new BoardViewStub();
        PremiumTileTypeFiller filler = new PremiumTileTypeFiller(board, stub);

        filler.run();

        assertEquals(45, stub.getSetTileTypeCallReceived());
    }
}
