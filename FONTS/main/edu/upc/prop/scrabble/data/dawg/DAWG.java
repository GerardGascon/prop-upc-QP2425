package edu.upc.prop.scrabble.data.dawg;

/**
 * Classe per representar un DAWG (Directed Acyclic Word Graph)
 * @author Albert Usero
 */
public class DAWG {

    /**
     * Constructor que inicialitza un DAWG buit.
     * <p>
     * Crea un DAWG amb un node arrel inicial però sense paraules afegides.
     * El node arrel s'utilitza com a punt d'entrada per a totes les operacions
     * amb el graf.
     */
    public DAWG() {
    }

    /**
     * Node arrel
     * @see Node
     */
    private final Node root = new Node();

    /**
     * Retorna el node arrel
     * @return Node arrel
     * @see Node
     */
    public Node getRoot() {
        return root;
    }
}