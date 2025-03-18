package scrabble;

import edu.upc.prop.scrabble.data.Bag;
import edu.upc.prop.scrabble.data.Piece;
import edu.upc.prop.scrabble.domain.BagFiller;
import edu.upc.prop.scrabble.domain.PieceGenerator;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestBagFiller {
    @Test
    public void bagIsFilled() {
        String aPiece = "A 3 1";
        PieceGenerator pieceGenerator = new PieceGenerator();
        BagFiller bagFiller = new BagFiller(pieceGenerator);
        Bag bag = bagFiller.run(aPiece);

        assertNotNull(bag);
    }
}
