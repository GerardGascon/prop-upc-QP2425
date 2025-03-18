package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.domain.PieceDrawer;

import java.util.Scanner;
import java.util.Vector;

public class Player {
    private String name;
    private int score;

    public Player(String name, boolean CPU) {
        this.name = name;
        this.CPU = CPU;
        this.score = 0;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    private boolean CPU = false;//cambiar luego quizas

    public boolean getCPU() {
        return CPU;
    }

    private void setCPU(boolean CPU) {
        this.CPU = CPU;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int scoretoadd) {
        this.score += scoretoadd;
    }

    private Vector<Piece> hand = new Vector<Piece>(7);

    public Vector<Piece> getHand() {return hand;}
}
