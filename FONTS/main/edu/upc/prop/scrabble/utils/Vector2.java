package edu.upc.prop.scrabble.utils;

import java.util.Objects;

/**
 * Classe que representa un vector o punt 2D amb coordenades enteres.
 * Aquesta classe proporciona mètodes per a operacions vectorials bàsiques, com la suma,
 * així com la redefinició de mètodes per a la igualtat i la representació en forma de cadena.
 *
 * @author Gerard Gascón
 */
public final class Vector2 {
    /**
     * La coordenada x del vector.
     */
    public int x;
    /**
     * La coordenada y del vector.
     */
    public int y;

    /**
     * Construeix un Vector2 amb x i y inicialitzats a 0.
     */
    public Vector2() {
        this(0, 0);
    }

    /**
     * Construeix un Vector2 amb les coordenades x i y especificades.
     *
     * @param x la coordenada x del vector.
     * @param y la coordenada y del vector.
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Suma el vector donat a aquest vector i retorna un nou Vector2 com a resultat.
     *
     * @param other el vector a sumar a aquest vector.
     * @return un nou Vector2 que representa la suma d'aquest vector amb el vector donat.
     */
    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    /**
     * Compara aquest vector amb un altre objecte per veure si són iguals.
     * Els vectors es consideren iguals si tenen les mateixes coordenades x i y.
     *
     * @param obj l'objecte amb el qual comparar aquest vector.
     * @return true si l'objecte donat és igual a aquest vector, false altrament.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vector2) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    /**
     * Retorna el codi hash d'aquest vector basat en les seves coordenades x i y.
     *
     * @return el codi hash d'aquest vector.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Retorna una representació en forma de cadena del vector en el format:
     * Vector2[x=<x>, y=<y>].
     *
     * @return una representació en forma de cadena d'aquest vector.
     */
    @Override
    public String toString() {
        return "Vector2[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }
}
