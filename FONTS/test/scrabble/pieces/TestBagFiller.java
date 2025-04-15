package scrabble.pieces;

import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.domain.pieces.BagFiller;
import edu.upc.prop.scrabble.domain.pieces.PieceGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBagFiller {
    @Test
    public void bagIsFilled() {
        String aPiece = "A 3 1";
        PieceGenerator pieceGenerator = new PieceGenerator();
        BagFiller sut = new BagFiller(pieceGenerator);
        Bag bag = sut.run(aPiece);

        assertNotNull(bag);
    }
}
