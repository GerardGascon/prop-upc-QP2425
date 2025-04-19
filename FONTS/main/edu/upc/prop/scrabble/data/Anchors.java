package edu.upc.prop.scrabble.data;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.ArrayList;

import static java.util.Collections.copy;

public class Anchors {
    private final ArrayList<Vector2> anchors;

    public Anchors(Board board) {
        this.anchors = new ArrayList<>();
        addAnchor(board.getSize() / 2, board.getSize() / 2);
    }

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

    public Anchors rotate(Board board) {
        Anchors rotated = new Anchors(board);
        int boardSize = board.getSize();
        //eliminamos la que se crea automaticamente
        rotated.removeAnchor(boardSize / 2, boardSize / 2);
        for (Vector2 anchor : this.anchors) {
            int newX = anchor.y;
            int newY = (boardSize - 1) - anchor.x;
            rotated.addAnchor(newX, newY);
        }
        return rotated;
    }
}