package edu.upc.prop.scrabble.presenter.terminal.draw;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.data.pieces.Bag;

import java.util.ArrayList;
import java.util.Vector;


public class PlayerDrawReader implements IDrawReader {
    private final Reader reader;
    private final Player player;
    private final Bag bag;

    public PlayerDrawReader(Reader reader, Player p, Bag bag) {
        this.reader = reader;
        this.player = p;
        this.bag = bag;
    }

    @Override
    public Piece[] run(int n) {
        System.out.println("Introduce " + n + " letters to draw:");
        while (true) {
            String input = reader.readLine();
            if (input == null) continue;

            PiecesConverter Pconverter = new PiecesConverter();
            PiecesInHandVerifier Pverifier = new PiecesInHandVerifier(player, Pconverter);
            Piece[] piecesInHand = Pverifier.run(input);

            if (piecesInHand.length == n) {
                PiecesInHandGetter Pgetter = new PiecesInHandGetter(bag, player);
                Piece[] newPieces = Pgetter.run(piecesInHand);
                return newPieces;
            }
            else {
                System.out.println("Error: some pieces to draw are missing.");
            }
        }
    }
}
