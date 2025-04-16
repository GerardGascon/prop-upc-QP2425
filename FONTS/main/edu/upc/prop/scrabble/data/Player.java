package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.Vector;

/**
 * Class that represents a player with it's attributes
 * @author Albert Usero
 */
public class Player {
    private String name;
    private int score;

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
     * Sets the name of the player
     * @param name Name that we want the player to have
     *
     */
    private void setName(String name) {
        this.name = name;
    }

    private boolean CPU = false;//cambiar luego quizas

    /**
     * @return True if it's CPU, False otherwise
     */
    public boolean getCPU() {
        return CPU;
    }

    /**
     * Sets the CPU status of the player
     * @param CPU True if it's CPU, False otherwise
     *
     */
    private void setCPU(boolean CPU) {
        this.CPU = CPU;
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

    private Vector<Piece> hand = new Vector<Piece>(7);

    public Vector<Piece> getHand() {return hand;}

    /**
     * Adds a given piece to the hand of the player
     * @param piece Piece that that will be added
     * @see Piece
     */
    public void AddPiece(Piece piece) {
        hand.addElement(piece);
    }
    /**
     * Removes a given piece to the hand of the player
     * @param piece Piece that that will be removed
     * @see Piece
     */
    public void RemovePiece(Piece piece) {
        hand.removeElement(piece);
    }
}
