package edu.upc.prop.scrabble.presenter.terminal.movements;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.scenes.SceneObject;
import edu.upc.prop.scrabble.presenter.terminal.utils.Reader;

public class MovementMaker extends SceneObject {
    private final Reader reader = new Reader();
    private PiecesConverter piecesConverter;
    private WordPlacer wordPlacer;

    public void configure(PiecesConverter piecesConverter, WordPlacer placer) {
        wordPlacer = placer;
        this.piecesConverter = piecesConverter;
    }

    @Override
    public void onProcess(float delta) {
        String movementRaw = reader.readLine();
        if (movementRaw == null){
            return;
        }

        Movement move = MovementParser.parse(movementRaw);
        System.out.println(move);
        Piece[] pieces = piecesConverter.run(move.word());
        wordPlacer.run(pieces, move.x(), move.y(), move.direction());

        SceneManager.getInstance().quit();
    }
}
