package edu.upc.prop.scrabble.domain.pieces;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import java.lang.reflect.InaccessibleObjectException;
import java.util.*;

public class PieceDrawer {
    private final Bag bag;
    private final Player player;

    public PieceDrawer(Bag bag, Player player) {
        this.bag = bag;
        this.player = player;
    }

    // pre: es vol posar una fitxa al taulell
    // post: agafar n fitxes random de la bossa
    public Piece[] run(Integer n) {
        ArrayList<Piece> drawnPieces = new ArrayList<>();
        if (bag.is_Empty()) return drawnPieces.toArray(new Piece[0]);

        Random rand = new Random();
        Vector<Piece> hand = player.getHand();
        int available = Math.min(hand.size(), Math.min(bag.getSize(), n));
        int i = 0;
        while (i < available && !bag.is_Empty()) {
            int r = rand.nextInt(bag.getSize());
            Piece drawnPiece = bag.getPiece(r);
            drawnPieces.add(drawnPiece);
            ++i;
        }

        for (i = 0; i < drawnPieces.size(); i++) {
            Piece handPiece = hand.get(0);
            player.RemovePiece(handPiece);
            bag.addPiece(handPiece);
            player.AddPiece(drawnPieces.get(i));
            hand = player.getHand();
        }
        return drawnPieces.toArray(new Piece[0]);
    }
}
