package edu.upc.prop.scrabble.data.dawg;

/**
 * Classe per representar un DAWG (Directed Acyclic Word Graph)
 * @author Albert Usero
 */
public class DAWG {
    /**
     * Node arrel
     * @see Node
     */
    private final Node root = new Node();

    /**
     * @return Node arrel
     * @see Node
     */
    public Node getRoot() {
        return root;
    }
}