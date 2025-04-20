package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
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

    // precon position < size
    public Vector2 getAnchor(int position) {
        return anchors.get(position);
    }

    //esto sige siendo ineficiente aunque + clean
    //para hacerlo + eficiente hacer que cada ancor tenga asociado un hash
    //y hacer si existe en el array list de anchors ese hash del anchor q queremos mirar
    //No creo que sea muy importante pq dudo mucho que tengamos muchos anchors a la vez (nisiquera cerca
    //de la mitad de board*board)
    public boolean exists(int x, int y) {
        return anchors.contains(new Vector2(x, y));
    }

    public Anchors rotate(int boardSize) {
        Anchors rotated = new Anchors();
        for (Vector2 anchor : this.anchors){
            rotated.addAnchor(boardSize - anchor.y - 1, anchor.x);
        }
        return rotated;
    }
}