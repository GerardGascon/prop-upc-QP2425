package edu.upc.prop.scrabble.domain.dawg;

import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;

/**
 * Afegeix una nova paraula al DAWG.
 * Les paraules s'han d'afegir en ordre alfabètic per un correcte funcionament.
 * @author Albert Usero
 * @see DAWG
 */
public class WordAdder {
    /**
     * DAWG al qual afegim paraules.
     * @see DAWG
     */
    private final DAWG dawg;
    // Al estar ordenados los diccionarios nos guardamos la palabra anterior para aprovecharnos de eso
    /**
     * Última paraula emmagatzemada. Servirà per aprofitar que s'han d'afegir paraules
     * en ordre alfabètic.
     */
    private String lastWordAdded = " ";

    /**
     * Crea un nou WordAdder.
     * @param dawg DAWG sobre el qual afegirem paraules.
     */
    public WordAdder(DAWG dawg) {
        this.dawg = dawg;
    }

    /**
     * Executa el WordAdder per afegir una paraula.
     * @param word Paraula que afegirem al DAWG
     */
    public void run(String word) {
        int commonPrefix = findCommonPrefixIndex(lastWordAdded, word);
        Node lastNode = getStartingPoint(commonPrefix);
        lastNode = addNecessaryNodes(word, commonPrefix, lastNode);
        setNodeAsEndOfWord(lastNode);

        lastWordAdded = word;
    }

    /**
     * Gestiona al DAWG afegir els nodes necessaris per poder representar
     * la paraula donada. Retorna l'últim node creat per representar la paraula i
     * servirà perquè a 'run' es marqui com a final de paraula.
     * @param word Paraula a afegir.
     * @param commonPrefix Prefix en comú de la paraula a afegir amb l'última afegida.
     * @param startingPoint Node en el qual cal començar a afegir nodes.
     * @return Últim node creat per representar la paraula.
     * @see Node
     * @see DAWG
     */
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

    /**
     * Si el node predecessor no té com a successor un caràcter com el que volem afegir,
     * afegim com a successor el caràcter i node donats.
     * @param parent Node predecessor
     * @param currentChar Caràcter a considerar per crear o no el successor
     * @param nextNode Node el qual representaría el caràcter en cas que s'afegeixi.
     * @see Node
     */
    private void tryAddSuccessor(Node parent, char currentChar, Node nextNode) {
        if (parent.getSuccessor(currentChar) == null) {
            parent.addSuccessor(currentChar, nextNode);
            updateHashCodeAndPropagate(parent);
        }
    }

    /**
     * Actualitza el paràmetre del node donat que indica si un node és final de paraula a True.
     * També recalcula i propaga el seu codi hash.
     * @param current Node a modificar.
     * @see Node
     */
    private void setNodeAsEndOfWord(Node current) {
        if(!current.isEndOfWord()){
            current.setEndOfWord(true);
            updateHashCodeAndPropagate(current);
        }
    }

    /**
     * Aprofita que les paraules s'afegeixen en ordre alfabètic i avança en el DAWG
     * el prefix que tenen en comú.
     * @param commonPrefix Prefix en comú que tenien l'última paraula afegida i la que volem afegir actualment.
     * @return Node final al recorrer el DAWG seguint els nodes que formen el prefix.
     * @see Node
     * @see DAWG
     */
    private Node getStartingPoint(int commonPrefix) {
        Node current = dawg.getRoot();
        for(int i = 0; i < commonPrefix; i++) {
            current = current.getSuccessor(lastWordAdded.charAt(i));
        }
        return current;
    }

    /**
     * Troba el prefix en comú de 2 paraules. Servirà per aprofitar la característica
     * que les paraules s'han d'afegir en ordre alfabètic.
     * @param prevword Última paraula introduïda.
     * @param actword Paraula que anem a introduir.
     * @return Prefix en comú de les 2 paraules donades.
     */
    private int findCommonPrefixIndex(String prevword, String actword) {
        int minLength = Math.min(prevword.length(), actword.length());
        for (int i = 0; i < minLength; i++) {
            if (prevword.charAt(i) != actword.charAt(i))
                return i;
        }
        return minLength;
    }

    /**
     * Tracta de trobar un node amb certes característiques. En cas que no
     * existeixi en crea un i el retorna. Altrament retorna el prèviament existent.
     * @param character Caràcter del node a trobar/crear.
     * @param isEndOfWord Característica és final de paraula o no del node a trobar/crear.
     * @param depth Profunditat del node a crear/trobar.
     * @param parent Node predecessor del node a crear/trobar.
     * @return Node amb les característiques introduïdes.
     * @see Node
     * @see DAWG
     */
    private Node findOrCreateNode(char character, boolean isEndOfWord, int depth, Node parent) {
        Node newNode = new Node(character, depth, parent, isEndOfWord);
        Node existingNode = dawg.getNode(newNode.hashCode());

        if (existingNode != null && existingNode.equals(newNode))
            return existingNode;

        dawg.addNode(newNode);
        return newNode;
    }

    /**
     * Recalcula el codi hash del node donat i propaga el canvi
     * als seus predecessors.
     * @param node Node el qual recalcularà el seu codi hash i propagarà.
     * @see Node
     */
    private void updateHashCodeAndPropagate(Node node) {
        if (node == null)
            return;

        int oldHash = node.hashCode();
        node.calculateHashCode();
        if (oldHash == node.hashCode())
            return;

        for (Node successor : node.getSuccessors().values()) {
            successor.setParent(node);
        }

        dawg.removeNode(oldHash);
        dawg.addNode(node);

        updateHashCodeAndPropagate(node.getParent());
    }
}