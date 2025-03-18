package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;

import java.util.Scanner;
import java.util.Vector;

public class Player {
    private String name;

    public Player(String name, boolean CPU) { //añadir password?
        this.name = name;
        this.CPU = CPU;
    }

    public String getName() {return name;}

    private void setName(String name) {this.name = name;}

    private boolean CPU = false; //fer la subclasse després o no cal?
    // Demomento hacen lo mismo

    public boolean getCPU() {return CPU;}

    private void setCPU(boolean CPU) {this.CPU = CPU;}

    private Vector<Piece> hand = new Vector <Piece>(7);  //7 letras como mucho según las normas

    public Vector<Piece> getHand() {return hand;} //cambiar de String(o char) a pieces
    //o quizas no hacen falta que haya algo que sea piece: String valor y miramos el valor luego

    // public void setHand(String[] hand) {this.hand = hand;} no hace falta, robar

    public void drawPiece(Bag bag){
        if(hand.size() < 7) hand.add(bag.drawPiece());
    }

    public void placePiece(Board board) {
        Scanner in  = new Scanner(System.in);
        int x  = in.nextInt();
        int y = in.nextInt();
        int numeropieza = in.nextInt();
        //hacer ifs o chequear de alguna manera que el numero de la pieza
        //esté en la mano (o no se pase)
        //que x y esten dentro lo mira board

        //board.placePiece(hand.get(numeropieza), x, y); place pice pide un string

        //cambié hand de chars a strings pq asi esta en board
    }
}
