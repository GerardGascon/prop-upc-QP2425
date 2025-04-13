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
}