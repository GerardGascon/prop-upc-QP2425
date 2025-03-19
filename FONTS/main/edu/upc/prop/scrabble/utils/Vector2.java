package edu.upc.prop.scrabble.utils;

import java.util.Objects;

public final class Vector2 {
    public int x;
    public int y;

    public Vector2(){
        this(0,0);
    }

    public Vector2(int x){
        this(x,0);
    }

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other){
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (Vector2) obj;
        return this.x == that.x &&
                this.y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Vector2[" +
                "x=" + x + ", " +
                "y=" + y + ']';
    }

}
