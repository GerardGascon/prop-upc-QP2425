package edu.upc.prop.scrabble.data;

import java.util.Vector;

public class Player {
    private String name;

    public Player(String name, boolean CPU) { //añadir password?
        this.name = name;
        this.CPU = CPU;
    }

    public String getName() {return name;}

    private void setName(String name) {this.name = name;}

    private boolean CPU = false;

    public boolean getCPU() {return CPU;}

    private void setCPU(boolean CPU) {this.CPU = CPU;}

    private char[] hand = new char[7];//7 letras como mucho según las normas

    public char[] getHand() {return hand;}

    public void setHand(char[] hand) {this.hand = hand;}
}
