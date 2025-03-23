package edu.upc.prop.scrabble.domain.pieces;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

public class PieceDrawer {
    private final Bag bag;

    public PieceDrawer(Bag bag){
        this.bag = bag;
    }

    // pre: es vol posar una fitxa al taulell
    // post: agafar una fitxa random de la bossa
    public Piece run() {
        if (bag.isEmpty()) {
            throw new InaccessibleObjectException("Bag is empty.");
        }
        Random rand = new Random();
        int r = rand.nextInt(bag.getSize());
        return bag.getPiece(r);
    }
}
