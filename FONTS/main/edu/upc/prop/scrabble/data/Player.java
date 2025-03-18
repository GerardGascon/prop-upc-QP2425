package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;

import java.util.Scanner;
import java.util.Vector;

public class Player {
    private String name;
    private Integer score;
    public Player(String name, boolean CPU) {
        this.name = name;
        this.CPU = CPU;
        this.score = 0;
    }

    public String getName() {return name;}

    private void setName(String name) {this.name = name;}

    private boolean CPU = false;//cambiar luego quizas

    public boolean getCPU() {return CPU;}

    private void setCPU(boolean CPU) {this.CPU = CPU;}

    public Integer getScore() {return score;}

    private void setScore(Integer score) {this.score = score;}

    private Vector<Piece> hand = new Vector <Piece>(7);

    public Vector<Piece> getHand() {return hand;}

    public void drawPiece(Bag bag){
        if(hand.size() < 7) hand.add(bag.drawPiece());
        //posar throw si
    }
}
