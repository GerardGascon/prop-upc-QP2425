package edu.upc.prop.scrabble.data.dawg;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Classe per representar nodes que formaran el DAWG.
 * Els nodes representen caràcters no peces.
 * @author Albert Usero
 * @see DAWG
 */
public class Node {
    /**
     * Indica si el node pot arribar a ser un final de paraula
     */
    private boolean isEndOfWord;
    /**
     * Mapa caràcter-Node per indicar els possibles caràcters
     * successors del Node i en cas que existeixin, el Node
     * al qual avançar.
     *
     */
    private final Map<Character, Node> successors = new HashMap<>();

    /**
     * Obtenim informació sobre els caràcters successors del node i si sí que
     * existeixen retornem el node que els representa.
     * @param character Caràcter del qual volem veure si el node actual té successor.
     * @return Node successor al node actual que representa el caràcter donat.
     * En cas que no existeixi null.
     */
    public Node getSuccessor(Character character) {
        return successors.get(character);
    }

    /**
     * Retorna tots els successors del node actual
     * @return Mapa caràcter-successors del node actual
     */
    public Map<Character, Node> getSuccessors() { return successors;}

    /**
     * @return True si el node és final de paraula, altrament False
     */
    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    /**
     * Modifica el paràmetre endOfWord
     * @param endOfWord Valor al qual volem establir end of word
     */
    public void setEndOfWord(boolean endOfWord) {
        this.isEndOfWord = endOfWord;
    }

    /**
     * Afegeix un node o obté aquell node si ja existeix.
     *
     * @param character El caràcter que identifica el node.
     * @return El node ja existent o una nova instància d'aquell node.
     */
    public Node getOrAddEdge(char character) {
        Node successor = successors.get(character);
        if (successor == null) {
            successor = new Node();
            successors.put(character, successor);
        }
        return successor;
    }

    /**
     * Comparador
     * @param o Objecte amb el qual el volem comparar
     * @return True si és igual a l'objecte a comparar, False altrament
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;
        return hashCode() == node.hashCode();
    }
}