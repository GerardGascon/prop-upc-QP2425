package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.ArrayList;
import java.util.Vector;

/**
 * Class that represents a player with it's attributes
 * @author Albert Usero
 */
public class Player {
    private final String name;
    private int score;
    private final boolean CPU;
    private final ArrayList<Piece> hand = new ArrayList<>(7);

    public Player(String name, boolean CPU) {
        this.name = name;
        this.CPU = CPU;
        this.score = 0;
    }

    /**
     * @return Name of the player
     */
    public String getName() {
        return name;
    }

    /**
     * @return True if it's CPU, False otherwise
     */
    public boolean getCPU() {
        return CPU;
    }

    /**
     *
     * @return Current score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Adds given score to the one that the player already haves
     * @param scoretoadd Score that we want to add to the current one of the pl
     *                   player
     */
    public void addScore(int scoretoadd) {
        this.score += scoretoadd;
    }

    public Piece[] getHand() {return hand.toArray(Piece[]::new);}

    /**
     * Adds a given piece to the hand of the player
     * @param piece Piece that that will be added
     * @see Piece
     */
    public void addPiece(Piece piece) {
        hand.add(piece);
    }
    /**
     * Removes a given piece to the hand of the player
     * @param piece Piece that that will be removed
     * @see Piece
     */
    public Piece removePiece(Piece piece) {
        if (piece.isBlank()){
            for (int i = 0; i < hand.size(); i++) {
                if (hand.get(i).isBlank()) {
                    Piece p = hand.remove(i);
                    p.setLetter(piece.letter());
                    return p;
                }
            }
            throw new PlayerDoesNotHavePieceException("Player " + name + " does not have the piece " + piece.letter());
        }

        for (int i = 0; i < hand.size(); i++) {
            Piece value = hand.get(i);
            if (value.letter().equals(piece.letter())) {
                hand.remove(i);
                return value;
            }
        }

        throw new PlayerDoesNotHavePieceException("Player " + name + " does not have the piece " + piece.letter());
    }
    public Piece hasPiece(String piece) {
        Piece blankPiece = null;
        for(Piece value : hand) {
            if(value.isBlank()) blankPiece = value;
            else if(value.letter().equals(piece)) return value;
        }
        return blankPiece;
    }
}