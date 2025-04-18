package edu.upc.prop.scrabble.utils;

import java.util.Objects;

/**
 * A class that represents a 2D vector or point with integer coordinates.
 * This class provides methods for basic vector operations, such as addition,
 * as well as overriding methods for equality and string representation.
 *
 * @author Gerard Gascón
 */
public final class Vector2 {
    /**
     * The x-coordinate of the vector.
     */
    public int x;
    /**
     * The y-coordinate of the vector.
     */
    public int y;

    /**
     * Constructs a Vector2 with both x and y set to 0.
     */
    public Vector2(){
        this(0,0);
    }

    /**
     * Constructs a Vector2 with the specified x and y coordinates.
     *
     * @param x the x-coordinate of the vector.
     * @param y the y-coordinate of the vector.
     */
    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds the given vector to this vector and returns a new Vector2 that is the result.
     *
     * @param other the vector to add to this vector.
     * @return a new Vector2 representing the sum of this vector and the given vector.
     */
    public Vector2 add(Vector2 other){
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    /**
     * Compares this vector with another object for equality.
     * The vectors are considered equal if both their x and y coordinates are the same.
     *
     * @param obj the object to compare this vector with.
     * @return true if the given object is equal to this vector, false otherwise.
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
     * Returns a hash code for this vector based on its x and y coordinates.
     *
     * @return the hash code for this vector.
     */
    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    /**
     * Returns a string representation of the vector in the format:
     * Vector2[x=<x>, y=<y>].
     *
     * @return a string representation of this vector.
     */
    @Override
    public String toString() {
        return "Vector2[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }
}
