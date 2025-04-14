package edu.upc.prop.scrabble.domain.dawg;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;

public class WordValidator {
    private final DAWG dawg;

    public WordValidator(DAWG dawg) {
        this.dawg = dawg;
    }

    public boolean run(Piece[] pieces){
        String word = "";
        for(int i = 0; i < pieces.length; i++){
            word = word.concat(pieces[i].letter());
        }
        Node current = dawg.getRoot();
        for(int i = 0; i < word.length(); i++) {
            Node successor = current.getSuccessor(word.charAt(i));
            if (successor == null) return false;
            current = successor;
        }
        return current.isEndOfWord();
    }
    //para el crosschecks (paso de liarme creando piezas que valgan 1
    //solo para guardar el string)
    public boolean runString(String word){
        Node current = dawg.getRoot();
        for(int i = 0; i < word.length(); i++) {
            Node successor = current.getSuccessor(word.charAt(i));
            if (successor == null) return false;
            current = successor;
        }
        return current.isEndOfWord();
    }

    //sabes que la palabra ya esta bien (estaba puesta en el tablero), quieres el nodo de final
    public Integer getFinalNode(String word){
        Node current = dawg.getRoot();
        for(int i = 0; i < word.length(); i++) {
            current = current.getSuccessor(word.charAt(i));
        }
        return current.hashCode();
    }

    //dado un nodo y una pieza dice si en sus sucesores se encuentra esa pieza y es final
    public boolean nextNodeTerminal(Integer hash, String piece){
        Node current = dawg.getNode(hash);
        //if(current == null) return false; siempre deberiamos de encontrarlo
        for(int i = 0; i < piece.length(); i++) {
            Node successor = current.getSuccessor(piece.charAt(i));
            if(successor == null) return false;
        }
        return current.isEndOfWord();
    }
}