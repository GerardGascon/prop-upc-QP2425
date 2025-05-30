package edu.upc.prop.scrabble.domain.dawg;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;

/**
 * Valida certa paraula donada mitjançant el DAWG.
 * El DAWG representa un diccionari de paraules vàl·lides.
 * @author Albert Usero
 * @see DAWG
 */
public class WordValidator {
    /**
     * DAWG el qual fem servir per validar.
     * @see DAWG
     */
    private final DAWG dawg;

    /**
     * Crea un WordValidator
     * @param dawg Dawg que representa les paraules vàl·lides
     * @see DAWG
     */
    public WordValidator(DAWG dawg) {
        this.dawg = dawg;
    }

    /**
     * Executa el WordValidator per determinar si una paraula és vàlida o no.
     * @param word Paraula la qual volem validar
     * @return True si és vàlida, False altrament.
     * @see Node
     * @see DAWG
     */
    public boolean run(String word){
        if (word.equals("TRAINTS")){
            System.out.println("TRAINTS");
        }

        Node current = dawg.getRoot();
        for(int i = 0; i < word.length(); i++) {
            Node successor = current.getSuccessor(word.charAt(i));
            if (successor == null) return false;
            current = successor;
        }
        return current.isEndOfWord();
    }

}