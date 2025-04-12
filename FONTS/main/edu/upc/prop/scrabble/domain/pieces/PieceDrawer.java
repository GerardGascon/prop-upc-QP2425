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
    // post: agafar n fitxes random de la bossa
    public Piece[] run(Integer n) {
        if (bag.isEmpty()) {
            throw new InaccessibleObjectException("Bag is empty.");
        }
        Piece[] pieces = new Piece[n];
        for (int i = 0; i < n; i++) {
            if (bag.isEmpty()) {
                throw new InaccessibleObjectException("Bag is empty.");
            }
            Random rand = new Random();
            int r = rand.nextInt(bag.getSize());
            pieces[i] = bag.getPiece(r);
        }
        return pieces;
    }
}
