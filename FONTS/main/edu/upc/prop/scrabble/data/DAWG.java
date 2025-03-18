package edu.upc.prop.scrabble.data;

import java.util.HashMap;
import java.util.Map;

public class DAWG {
    private class Node{
        boolean terminal;//si es un nodo que acaba palabra
        int index;//para diferenciar entre nodos, anque 'tengan la misma letra',
        // pero dferentes sucesores
        Map<Character, Node> successors;/*no nos almacenamos en cada nodo su caracter
        atual, sino aquí (el nodo inicial no tiene así que sin problema)*/

        public Node() {
            this.terminal = false;
            this.index = -1;
            this.successors = new HashMap<Character, Node>();
        }
    }

    private Node root;
    private Node current;
    private String previosWord;/*al estar ordenados los diccionarios nos guardamos
    la palabra anterior para aprovecharnos de eso*/
    private int counter;//para ir asignando indices
    private Map<String, Node> previousSignatures;/*para evitar hacer 2 nodos
    iguales nos guardamos las signatures. Una signature es un String que resume las características
    de un nodo en un solo string y nos sirve para comparar de manera mucho más eficiente
    (sino habría que comparar el mapa de sucesores*/

    private void Initialize() {
        root = new Node();
        current = root;
        root.index = 0;
        previosWord = "";
        counter = 1;
        previousSignatures = new HashMap<String, Node>(); //no map pq peta??
        //bueno hash map no mantiene el orden pero da igual pq las consultas son efienctes

    }

    /*public añadir palabra

     */

}
