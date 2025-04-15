package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.data.board.*;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.terminal.BoardView;
import edu.upc.prop.scrabble.presenter.terminal.factories.PlayerFactory;
import edu.upc.prop.scrabble.presenter.terminal.players.PlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene {
    public GameScene(GameProperties properties) {
        Board board = getBoard(properties);
        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);
        PiecesConverter piecesConverter = new PiecesConverter();

        IGamePlayer[] players = instantiatePlayers(properties, piecesConverter, pointCalculator, board);
        Turn turnManager = new Turn(players);
//        turnManager.run();
    }

    private PlayerObject[] instantiatePlayers(GameProperties properties, PiecesConverter piecesConverter,
                                    PointCalculator pointCalculator, Board board) {
        List<PlayerObject> playerObjects = new ArrayList<>();
        for (int i = 0; i < properties.players() + properties.cpus(); i++) {
            PlayerObject player = instantiate(PlayerObject.class);
            BoardView boardView = instantiate(BoardView.class);
            PlayerFactory.createPlayer(player, "Potato", false, piecesConverter, pointCalculator, board, boardView);
            playerObjects.add(player);
        }
        return playerObjects.toArray(PlayerObject[]::new);
    }

    private Board getBoard(GameProperties properties) {
        return switch (properties.boardType()) {
            case BoardType.Junior -> new JuniorBoard();
            case BoardType.Standard -> new StandardBoard();
            case BoardType.Super -> new SuperBoard();
        };
    }
}
