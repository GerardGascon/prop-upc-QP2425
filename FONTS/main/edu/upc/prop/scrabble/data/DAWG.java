package edu.upc.prop.scrabble.data;

import java.util.HashMap;
import java.util.Map;

public class DAWG {
    //puede que cambio de chars a pieza o caracteres especiales ($ 0 1)
    //por el caso de l.l y cosas asi
    private class Node {
        boolean terminal;//si es un nodo que acaba palabra
        //cambiar esto a piezas luego??? por la l.l y eso
        Character character;//no se usa mucho por los sucesores, pero util para
        // distinguir mejor solo con el hash y por lo tanto hacer comparaciones
        Map<Character, Node> successors;/*no nos almacenamos en cada nodo su caracter
        actual, sino aquí (el nodo inicial no tiene así que sin problema)*/
        Integer hashCode;
        int depth;
        Node parent;//para realizar cambios a los sucesores del previo

        public Node(Character character, int depth, Node parent) {
            this.terminal = false;
            this.character = character;
            this.successors = new HashMap<Character, Node>();
            this.hashCode = calculateHashCode();
            this.depth = depth;
            this.parent = parent;
        }

        public void setTerminal(boolean terminal) {
            if(this.terminal != terminal) {
                this.terminal = terminal;
                updateHashCodeAndPropagate(this);
            }
        }
        //unico para cada nodo
        private int calculateHashCode() {
            int result = 17;
            result = 31 * result;
            if (terminal) result += 1;
            result = 31 * result;
            if(character != null) result += character.hashCode();
            result = 31 * result + depth;
            result = 31 * result + successors.size();
            for (Map.Entry<Character, Node> entry : successors.entrySet()) {
                result = 31 * result + entry.getKey().hashCode();
                result = 31 * result + entry.getValue().hashCode();
            }
            return result;
        }


        public boolean equalNodes(Node othernode) {
            if (this.hashCode != othernode.hashCode) return false;
            if (this.terminal != othernode.terminal) return false;
            if(this.depth != othernode.depth) return false;
            if(this.character != othernode.character) return false;
            if (successors.size() != othernode.successors.size()) return false;
            for (Map.Entry<Character, Node> entry : successors.entrySet()) {
                Node otherSuccessor = othernode.successors.get(entry.getKey());
                if (otherSuccessor == null) return false;
                if (!entry.getValue().equalNodes(otherSuccessor)) return false;
            }
            return true;
        }

    }

    private Node root;
    private Node current;
    private String previousWord;/*al estar ordenados los diccionarios nos guardamos
    la palabra anterior para aprovecharnos de eso*/
    private Map<Integer, Node> uniqueNodes;//Signature - Nodo,
    // nos sirve para almacenar y evitar tener 2 iguales

    public void Initialize() {
        root = new Node(null, 0, null);
        current = root;
        previousWord = "";
        uniqueNodes = new HashMap<Integer, Node>(); //java no deja Map(?¿)
        uniqueNodes.put(root.hashCode, root); //el 0 SIGNATURE PLACEHOLDER
    }

    //hacer esto para cada palabra del diccionario evidentemente
    public void addWord(String word) {
        //Asegurarse de que vienen en orden por si acaso? Sobretodo por el . de l.l o algo asi
        int commonPrefix = findCommonPrefixIndex(previousWord, word);
        current = root;
        for(int i = 0; i < commonPrefix; i++) {
            current = current.successors.get(previousWord.charAt(i));
        }
        for(int i = commonPrefix; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            Node nextNode = findOrCreateUniqueNode(currentChar, i == word.length() -1, current.depth + 1, current);
            if(!current.successors.containsKey(word.charAt(i))) {
                current.successors.put(word.charAt(i), nextNode);
                updateHashCodeAndPropagate(current);
            }
            current = nextNode;
        }
        if(!current.terminal){
            current.setTerminal(true);
            updateHashCodeAndPropagate(current);
        }
        previousWord = word;
    }

    private Integer findCommonPrefixIndex(String prevword, String actword) {
        int minLength = Math.min(prevword.length(), actword.length());
        for (int i = 0; i < minLength; i++) {
            if (prevword.charAt(i) != actword.charAt(i)) {
                return i;
            }
        }
        return minLength; //tampoco deberia de pasar que sean iguales
    }
    //busca un nodo igual
    private Node findOrCreateUniqueNode(char character, boolean isTerminal, int depth, Node parent) {
        Node newNode = new Node(character, depth, parent);
        newNode.setTerminal(isTerminal);
        Node existingNode = uniqueNodes.get(newNode.hashCode);
        if (existingNode != null && existingNode.equalNodes(newNode)) {
            return existingNode;
        }
        uniqueNodes.put(newNode.hashCode, newNode);
        return newNode;
    }

    private void updateHashCodeAndPropagate(Node node){
        if(node == null) return;
        int oldHash = node.hashCode();
        node.hashCode = node.calculateHashCode();
        if(oldHash != node.hashCode){
            uniqueNodes.remove(oldHash);
            Node existingNode = uniqueNodes.get(node.hashCode);
            if (existingNode != null && existingNode.equalNodes(node)) {
                if(node.parent != null) {
                    updateHashCodeAndPropagate(node.parent);
                }
                else{
                    uniqueNodes.put(node.hashCode, node);
                    if(node.parent != null) {
                        updateHashCodeAndPropagate(node.parent);
                    }
                }
            }

        }
    }

    public boolean validWord(String word) {
        Node current = root;
        for(int i = 0; i < word.length(); i++) {
            if(!current.successors.containsKey(word.charAt(i))) return false;
            current = current.successors.get(word.charAt(i));
        }
        return current.terminal;
    }

    public int getuniqueNodes(){
       // for(int i = 0;i<uniqueNodes.size();++i)System.out.println(uniqueNodes.get(i));
        return uniqueNodes.size();
    }
}



