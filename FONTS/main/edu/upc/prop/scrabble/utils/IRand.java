package edu.upc.prop.scrabble.utils;

/**
 * Interfície que defineix mètodes per generar enters aleatoris.
 * És útil per aleatoritzar elements del joc, com ara la selecció o col·locació de fitxes.
 *
 * @author Gerard Gascón
 */
public interface IRand {
    /**
     * Genera un enter aleatori.
     *
     * @return Un enter aleatori.
     */
    int nextInt();

    /**
     * Genera un enter aleatori dins del rang especificat.
     *
     * @param bound El límit superior (exclusiu) per al nombre aleatori.
     * @return Un enter aleatori entre 0 (inclusiu) i el límit donat (exclusiu).
     */
    int nextInt(int bound);

    /**
     * Genera un enter aleatori dins del rang especificat (inclusiu per l'origen, exclusiu pel límit).
     *
     * @param origin El límit inferior (inclusiu) per al nombre aleatori.
     * @param bound  El límit superior (exclusiu) per al nombre aleatori.
     * @return Un enter aleatori entre l'origen (inclusiu) i el límit (exclusiu).
     */
    int nextInt(int origin, int bound);
}
