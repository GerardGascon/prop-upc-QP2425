package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.board.SuperBoard;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.scenes.SceneManager;
import edu.upc.prop.scrabble.presenter.terminal.BoardView;
import edu.upc.prop.scrabble.presenter.terminal.PlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.utils.Direction;

public class GameScene extends Scene {
    public GameScene() {
        PlayerObject player = instantiate(PlayerObject.class);
        BoardView boardView = instantiate(BoardView.class);

        Board board = new SuperBoard();
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        WordPlacer placer = new WordPlacer(board, boardView, pointCalculator);
        PiecesConverter piecesConverter = new PiecesConverter();
        MovementMaker movementMaker = new MovementMaker(piecesConverter, placer);

        player.configure(movementMaker);
        player.startTurn();
    }
}
