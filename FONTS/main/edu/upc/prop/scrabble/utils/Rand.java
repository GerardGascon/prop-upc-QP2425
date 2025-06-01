package edu.upc.prop.scrabble.utils;

import java.util.Random;

/**
 * Rand és una implementació concreta de la interfície IRand.
 * Proporciona mètodes per generar nombres aleatoris utilitzant la classe java.util.Random.
 *
 * @see IRand
 * @see Random
 *
 * @author Gerard Gascón
 */
public class Rand implements IRand {
    /**
     * Implementació del generador a utilitzar
     */
    private final Random random = new Random();

    /**
     * Crea una instància del generador de nombres aleatoris
     */
    public Rand() {

    }

    /**
     * Retorna un enter aleatori. Aquest mètode genera enters aleatoris dins de tot el rang possible d'enters.
     *
     * @return un enter aleatori.
     */
    @Override
    public int nextInt() {
        return random.nextInt();
    }

    /**
     * Retorna un enter aleatori des de 0 (inclusiu) fins al límit especificat (exclusiu).
     *
     * @param bound el límit superior (exclusiu) per al nombre aleatori.
     * @return un enter aleatori dins del rang [0, bound).
     */
    @Override
    public int nextInt(int bound) {
        return random.nextInt(bound);
    }

    /**
     * Retorna un enter aleatori entre l'origen especificat (inclusiu) i el límit (exclusiu).
     *
     * @param origin el límit inferior (inclusiu) per al nombre aleatori.
     * @param bound  el límit superior (exclusiu) per al nombre aleatori.
     * @return un enter aleatori dins del rang [origin, bound).
     */
    @Override
    public int nextInt(int origin, int bound) {
        return random.nextInt(origin, bound);
    }
}
