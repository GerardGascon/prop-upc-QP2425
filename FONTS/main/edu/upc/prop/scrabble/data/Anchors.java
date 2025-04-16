package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.utils.Pair;

import java.util.ArrayList;

public class Anchors {
    private ArrayList<Pair<Integer,Integer>> anchors;
    public Anchors() { this.anchors = new ArrayList<>(); }

    public void addAnchor(int x, int y) { anchors.add(new Pair<>(x, y)); }
    public int getSize() { return anchors.size(); }

    // precon position < size
    public Pair<Integer,Integer> getAnchor(int position) { return anchors.get(position); }
}