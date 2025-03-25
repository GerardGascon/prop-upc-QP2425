package edu.upc.prop.scrabble.data;

import java.util.HashMap;
import java.util.Map;

public class DAWG {
    //puede que cambio de chars a pieza o caracteres especiales ($ 0 1)
    //por el caso de l.l y cosas asi
    private class Node {
        boolean terminal;//si es un nodo que acaba palabra
        //cambiar esto a piezas luego??? por la l.l y eso
        Map<Character, Node> successors;/*no nos almacenamos en cada nodo su caracter
        actual, sino aquí (el nodo inicial no tiene así que sin problema)*/
        String signature;

        public Node() {
            this.terminal = false;
            this.successors = new HashMap<Character, Node>();
            this.signature = generateSignature();
        }

        public void setTerminal(boolean terminal) {
            this.terminal = terminal;
            this.signature = generateSignature();
        }

        private String generateSignature() {
            return "0";
        }

        public boolean equalNodes(Node othernode){
            return this.signature.equals(othernode.signature);
        }

    }
    //public String generteSignature(){

    private Node root;
    private Node current;
    private String previousWord;/*al estar ordenados los diccionarios nos guardamos
    la palabra anterior para aprovecharnos de eso*/
    private Map<String, Node> uniqueNodes;//Signature - Nodo,
    // nos sirve para almacenar y evitar tener 2 iguales

    public void Initialize() {
        root = new Node();
        current = root;
        previousWord = " ";
        uniqueNodes = new HashMap<String, Node>(); //java no deja Map(?¿)
        uniqueNodes.put(root.signature, root); //el 0 SIGNATURE PLACEHOLDER
    }

    //hacer esto para cada palabra del diccionario evidentemente
    public void AddWord(String word) {
        //Asegurarse de que vienen en orden por si acaso? Sobretodo por el . de l.l o algo asi
        int commonPrefix = findCommonPrefixIndex(previousWord, word);
        current = root;
        for(int i = 0; i < commonPrefix; i++) {
            current = current.successors.get(previousWord.charAt(i));
        }
        for(int i = commonPrefix; i < word.length(); i++) {
            Node nextNode = findOrCreateNode(false);
            current = nextNode;
            if(!current.successors.containsKey(word.charAt(i))) {
                Node succssorNode = findOrCreateNode(i == word.length() - 1);
                current.successors.put(word.charAt(i), nextNode);
            }
            current = nextNode;
        }
        current.setTerminal(true);
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

    private Node findOrCreateNode(boolean terminal) {
        Node newNode = new Node();
        return newNode;
    }

    public boolean validWord(String word) {
        Node current = root;
        for(int i = 0; i < word.length(); i++) {
            if(!current.successors.containsKey(word.charAt(i))) return false;
            current = current.successors.get(word.charAt(i));
        }
        return current.terminal;
    }
}



