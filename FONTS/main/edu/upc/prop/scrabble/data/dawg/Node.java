package edu.upc.prop.scrabble.data.dawg;

import java.util.HashMap;
import java.util.Map;

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
     * Emmagatzema el caràcter que representa el node
     */
    private final Character character;
    /**
     * Mapa caràcter-Node per indicar els possibles caràcters
     * successors del Node i en cas que existeixin, el Node
     * al qual avançar.
     *
     */
    private final Map<Character, Node> successors;
    /**
     * Número creat a partir de les característiques del node
     * que pretén representar-lo de manera única i inequívocament.
     */
    private int hashCode;
    /**
     * Indica a quants nodes de distància del node arrel
     * està situat aquest node.
     */
    private final int depth;
    /**
     * Indica el node predecessor al node actual.
     */
    private Node parent;

    /**
     * Crea un node amb les característiques donades.
     * Calcula el seu codi de hash actual.
     * @param character Caràcter que representarà el node
     * @param depth Profunditat respete l'arrel del node
     * @param parent node predecessor al node creat
     * @param isEndOfWord Boole+a que indica si el node pot ser o no final de paraula.
     */
    public Node(Character character, int depth, Node parent, boolean isEndOfWord) {
        this.isEndOfWord = isEndOfWord;
        this.character = character;
        this.successors = new HashMap<>();
        this.depth = depth;
        this.parent = parent;
        calculateHashCode();
    }

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
     * Afegeix un nou successor
     * @param character Caràcter que representa el successor
     * @param successor Node successor a afegir
     */
    public void addSuccessor(Character character, Node successor) {
        successors.put(character, successor);
    }

    /**
     * @return Profunditat del node
     */
    public int getDepth() {
        return depth;
    }

    /**
     * @return Node predecessor
     */
    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

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
        if(this.isEndOfWord != endOfWord) {
            this.isEndOfWord = endOfWord;
        }
    }

    /**
     * Calcula i actualitza codi de Hash del node segons els
     * valors que té actualment.
     */
    public void calculateHashCode() {
        int result = 17;
        result = 31 * result;
        if (isEndOfWord) result += 1;
        result = 31 * result;
        if(character != null) result += character.hashCode();
        result = 31 * result + depth;
        result = 31 * result + successors.size();
        for (Map.Entry<Character, Node> entry : successors.entrySet()) {
            result = 31 * result + entry.getKey().hashCode();
            result = 31 * result + entry.getValue().hashCode();
        }
        this.hashCode = result;
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

    /**
     * @return Hash code del node
     */
    @Override
    public int hashCode() {
        return hashCode;
    }

    @Override
    public String toString() {
        return character == null ? "" : parent.toString() + character;
    }
}