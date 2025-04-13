package edu.upc.prop.scrabble.presenter.terminal.movements;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;

public class MovementMaker {
    private final PiecesConverter piecesConverter;
    private final WordPlacer wordPlacer;

    public MovementMaker(PiecesConverter piecesConverter, WordPlacer placer) {
        wordPlacer = placer;
        this.piecesConverter = piecesConverter;
    }

    public int makeMove(String movementRaw) {
        Movement move = MovementParser.parse(movementRaw);
        System.out.println(move);
        Piece[] pieces = piecesConverter.run(move.word());
        return wordPlacer.run(pieces, move.x(), move.y(), move.direction());
    }
}
