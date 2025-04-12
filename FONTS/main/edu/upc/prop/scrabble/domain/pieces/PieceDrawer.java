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
        ArrayList<Piece> pieces = new ArrayList<>();
        if (bag.isEmpty()) return pieces.toArray(new Piece[0]);
        int i = 0;
        Random rand = new Random();
        while(!bag.isEmpty() && i < n){
            int r = rand.nextInt(bag.getSize());
            pieces.add(bag.getPiece(r));
            ++i;
        }
        return pieces.toArray(new Piece[0]);
    }
}
