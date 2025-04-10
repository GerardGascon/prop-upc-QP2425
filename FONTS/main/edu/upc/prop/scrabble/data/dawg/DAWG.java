package edu.upc.prop.scrabble.data.dawg;

import java.util.HashMap;
import java.util.Map;

public class DAWG {
    private final Node root;
    private final Map<Integer, Node> uniqueNodes;

    public DAWG() {
        root = new Node(null, 0, null, false);
        uniqueNodes = new HashMap<>();
        uniqueNodes.put(root.hashCode(), root);
    }

    public Node getRoot() {
        return root;
    }

    public Node getNode(int hash) {
        return uniqueNodes.get(hash);
    }

    public void addNode(Node node) {
        uniqueNodes.put(node.hashCode(), node);
    }

    public void removeNode(int hash) {
        uniqueNodes.remove(hash);
    }
}