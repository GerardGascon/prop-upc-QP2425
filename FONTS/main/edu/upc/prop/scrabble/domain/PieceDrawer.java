package edu.upc.prop.scrabble.domain;
import edu.upc.prop.scrabble.data.Bag;
import edu.upc.prop.scrabble.data.Piece;
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
        int i = rand.nextInt(bag.getSize());
        return bag.getPiece(i);
    }
}
