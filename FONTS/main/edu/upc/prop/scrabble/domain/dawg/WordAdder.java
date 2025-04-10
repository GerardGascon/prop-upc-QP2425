package edu.upc.prop.scrabble.domain.dawg;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;

public class WordAdder {
    private final DAWG dawg;
    // Al estar ordenados los diccionarios nos guardamos la palabra anterior para aprovecharnos de eso
    private String lastWordAdded = " ";

    public WordAdder(DAWG dawg) {
        this.dawg = dawg;
    }

    public void run(String word) {
        int commonPrefix = findCommonPrefixIndex(lastWordAdded, word);
        Node lastNode = getStartingPoint(commonPrefix);
        lastNode = addNecessaryNodes(word, commonPrefix, lastNode);
        setNodeAsEndOfWord(lastNode);

        lastWordAdded = word;
    }

    private Node addNecessaryNodes(String word, int commonPrefix, Node startingPoint) {
        for(int i = commonPrefix; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            boolean isEndOfWord = i == word.length() - 1;
            int depth = startingPoint.getDepth() + 1;

            Node successor = findOrCreateNode(currentChar, isEndOfWord, depth, startingPoint);
            tryAddSuccessor(startingPoint, currentChar, successor);

            startingPoint = successor;
        }
        return startingPoint;
    }

    private void tryAddSuccessor(Node parent, char currentChar, Node nextNode) {
        if (parent.getSuccessor(currentChar) == null) {
            parent.addSuccessor(currentChar, nextNode);
            updateHashCodeAndPropagate(parent);
        }
    }

    private void setNodeAsEndOfWord(Node current) {
        if(!current.isEndOfWord()){
            current.setEndOfWord(true);
            updateHashCodeAndPropagate(current);
        }
    }

    private Node getStartingPoint(int commonPrefix) {
        Node current = dawg.getRoot();
        for(int i = 0; i < commonPrefix; i++) {
            current = current.getSuccessor(lastWordAdded.charAt(i));
        }
        return current;
    }

    private int findCommonPrefixIndex(String prevword, String actword) {
        int minLength = Math.min(prevword.length(), actword.length());
        for (int i = 0; i < minLength; i++) {
            if (prevword.charAt(i) != actword.charAt(i))
                return i;
        }
        return minLength;
    }

    private Node findOrCreateNode(char character, boolean isEndOfWord, int depth, Node parent) {
        Node newNode = new Node(character, depth, parent, isEndOfWord);
        Node existingNode = dawg.getNode(newNode.hashCode());

        if (existingNode != null && existingNode.equals(newNode))
            return existingNode;

        dawg.addNode(newNode);
        return newNode;
    }

    private void updateHashCodeAndPropagate(Node node){
        if(node == null)
            return;

        int oldHash = node.hashCode();
        node.calculateHashCode();
        if(oldHash == node.hashCode())
            return;

        dawg.removeNode(oldHash);
        dawg.addNode(node);
        updateHashCodeAndPropagate(node.getParent());
    }
}