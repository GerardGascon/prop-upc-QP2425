package edu.upc.prop.scrabble.presenter.terminal.draw;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;

import java.util.Vector;

public class IADrawReader implements IDrawReader {
    private Player player;

    public IADrawReader(Player player) {
        this.player = player;
    }

    public Piece[] run(int n) {

    }
}
