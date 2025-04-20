package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;

public class Anchors {
    private final ArrayList<Vector2> anchors;

    public Anchors() { this.anchors = new ArrayList<>(); }

    public void addAnchor(int x, int y) {
        anchors.add(new Vector2(x, y));
    }

    public void removeAnchor(int x, int y) {
        anchors.remove(new Vector2(x, y));
    }

    public int getSize() {
        return anchors.size();
    }

    public Vector2 getAnchor(int position) {
        return anchors.get(position);
    }

    public boolean exists(int x, int y) {
        return anchors.contains(new Vector2(x, y));
    }

    public Anchors rotate(int boardSize) {
        Anchors rotated = new Anchors();
        for (Vector2 anchor : this.anchors){
            rotated.addAnchor(anchor.y, boardSize - anchor.x - 1);
        }
        return rotated;
    }
}