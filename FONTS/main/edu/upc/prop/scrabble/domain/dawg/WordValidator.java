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
        Node current = dawg.getRoot();
        for(int i = 0; i < word.length(); i++) {
            current = current.getSuccessor(word.charAt(i));
            if (current == null)
                return false;
        }
        return current.isEndOfWord();
    }
}