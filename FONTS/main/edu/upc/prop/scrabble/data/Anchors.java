package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;

public class Anchors {
    private final ArrayList<Vector2> anchors;
    public Anchors() { this.anchors = new ArrayList<>(); }

    public void addAnchor(int x, int y) { anchors.add(new Vector2(x, y)); }
    public int getSize() { return anchors.size(); }

    // precon position < size
    public Vector2 getAnchor(int position) { return anchors.get(position); }

    // GIGAineficiente tal vez sea mejor hacer que anchors sea un set
    public boolean exists(int x, int y) {
        for (Vector2 anchor : anchors) {
            if (anchor.x == x && anchor.y == y) {
                return true;
            }
        }
        return false;
    }
}