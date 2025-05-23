package edu.upc.prop.scrabble.data.dawg;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe que representa un DAWG (Directed Acyclic Word Graph)
 * @author Albert Usero
 */
public class DAWG {
    /**
     * Node arrel
     * @see Node
     */
    private final Node root;

    /**
     * Mapa que relaciona números de hash amb nodes per emmagatzemar nodes únics.
     */
    private final Map<Integer, Node> uniqueNodes;

    /**
     * Crea un DAWG buit
     */
    public DAWG() {
        root = new Node(null, 0, null, false);
        uniqueNodes = new HashMap<>();
        uniqueNodes.put(root.hashCode(), root);
    }
    /**
     * @return Node arrel
     * @see Node
     */
    public Node getRoot() {
        return root;
    }

    /**
     * @param hash Número de hash que referència al node a retornar
     * @return Node al qual correspon el hash donat
     * @see Node
     */
    public Node getNode(int hash) {
        return uniqueNodes.get(hash);
    }

    /**
     * Afegeix un node al conjunt de nodes únics
     * @param node Node que s'ha d'afegir al conjunt de nodes únics
     * @see Node
     */
    public void addNode(Node node) {
        uniqueNodes.put(node.hashCode(), node);
    }

    /**
     * Elimina un node del conjunt de nodes únics
     * @param hash Codi hash del node que s'ha d'eliminar del conjunt de nodes únics
     * @see Node
     */
    public void removeNode(int hash) {
        uniqueNodes.remove(hash);
    }
}