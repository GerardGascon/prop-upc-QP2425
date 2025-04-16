package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.utils.Pair;

import java.util.ArrayList;

public class Anchors {
    ArrayList<Pair<Integer,Integer>> anchors;

    public Anchors(ArrayList<Pair<Integer,Integer>> anchors) {
        this.anchors = new ArrayList<>();
    }

    public void addAnchor(int x, int y) {
        anchors.add(new Pair<>(x, y));
    }
}
