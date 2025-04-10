package edu.upc.prop.scrabble.domain.dawg;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;

public class WordValidator {
    private final DAWG dawg;

    public WordValidator(DAWG dawg) {
        this.dawg = dawg;
    }

    public boolean run(String word){
        Node current = dawg.getRoot();
        for(int i = 0; i < word.length(); i++) {
            Node successor = current.getSuccessor(word.charAt(i));
            if (successor == null) return false;
            current = successor;
        }
        return current.isEndOfWord();
    }
}