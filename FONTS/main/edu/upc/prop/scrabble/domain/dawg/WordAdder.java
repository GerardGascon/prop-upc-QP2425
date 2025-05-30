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
    /**
     * Última paraula emmagatzemada. Servirà per aprofitar que s'han d'afegir paraules
     * en ordre alfabètic.
     */
    private String lastWordAdded = "";

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
     * @param current Node en el qual cal començar a afegir nodes.
     * @return Últim node creat per representar la paraula.
     * @see Node
     * @see DAWG
     */
    private Node addNecessaryNodes(String word, int commonPrefix, Node current) {
        for(int i = commonPrefix; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            current = current.getOrAddEdge(currentChar);
        }

        current.setEndOfWord(true);

        return current;
    }

    /**
     * Actualitza el paràmetre del node donat que indica si un node és final de paraula a True.
     * També recalcula i propaga el seu codi hash.
     * @param current Node a modificar.
     * @see Node
     */
    private void setNodeAsEndOfWord(Node current) {
        current.setEndOfWord(true);
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
}