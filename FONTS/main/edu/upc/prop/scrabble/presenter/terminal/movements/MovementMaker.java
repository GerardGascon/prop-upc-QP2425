package edu.upc.prop.scrabble.presenter.terminal.movements;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;

public class MovementMaker {
    private final PlaceActionMaker placeActionMaker;

    public MovementMaker(PlaceActionMaker placeActionMaker) {
        this.placeActionMaker = placeActionMaker;
    }

    public void makeMove(String movementRaw) {
        Movement move = MovementParser.parse(movementRaw);
        System.out.println(move);
        placeActionMaker.run(move);
    }
}
