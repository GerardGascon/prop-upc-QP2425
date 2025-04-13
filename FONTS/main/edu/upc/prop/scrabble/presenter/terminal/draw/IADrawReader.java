package edu.upc.prop.scrabble.presenter.terminal.draw;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

public class IADrawReader implements IDrawReader {
    private Player iaplayer;
    private Bag bag;
    public IADrawReader(Bag bag, Player iaplayer) {
        this.bag = bag;
        this.iaplayer = iaplayer;
    }

    public Piece[] run(int n) {
        Vector<Piece> hand = iaplayer.getHand();
        int available = Math.min(hand.size(), n);

        Random rand = new Random();
        Piece[] selectedPieces = new Piece[available];
        for (int i = 0; i < available; i++) {
            int r = rand.nextInt(available);
            selectedPieces[i] = hand.get(r);
        }
        PiecesInHandGetter Pgetter = new PiecesInHandGetter(bag, iaplayer);
        Piece[] newPieces = Pgetter.run(selectedPieces);
        System.out.print("Player" + iaplayer.getName() + " changed: ");
        for (Piece p : newPieces) {
            System.out.print(p.letter() + " ");
        }
        System.out.println();
        return newPieces;
    }
}
