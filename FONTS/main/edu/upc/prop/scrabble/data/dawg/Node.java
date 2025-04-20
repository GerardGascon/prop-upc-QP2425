package edu.upc.prop.scrabble.data.dawg;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private boolean isEndOfWord;
    private final Character character;
    private final Map<Character, Node> successors;
    private int hashCode;
    private final int depth;
    private final Node parent;

    public Node(Character character, int depth, Node parent, boolean isEndOfWord) {
        this.isEndOfWord = isEndOfWord;
        this.character = character;
        this.successors = new HashMap<>();
        this.depth = depth;
        this.parent = parent;
        calculateHashCode();
    }

    public Node getSuccessor(Character character) {
        return successors.get(character);
    }

    public Map<Character, Node> getSuccessors() { return successors;}

    public void addSuccessor(Character character, Node successor) {
        successors.put(character, successor);
    }

    public int getDepth() {
        return depth;
    }

    public Node getParent() {
        return parent;
    }

    public boolean isEndOfWord() {
        return isEndOfWord;
    }

    public void setEndOfWord(boolean endOfWord) {
        if(this.isEndOfWord != endOfWord) {
            this.isEndOfWord = endOfWord;
        }
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;
        return hashCode() == node.hashCode();
    }

    @Override
    public int hashCode() {
        return hashCode;
    }
}