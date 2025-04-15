package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.data.Player;
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
import edu.upc.prop.scrabble.presenter.terminal.players.AIPlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.players.HumanPlayerObject;
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

        BoardView boardView = instantiate(BoardView.class);
        IGamePlayer[] players = instantiatePlayers(properties, boardView, piecesConverter, pointCalculator, board);
        Turn turnManager = new Turn(players);
//        turnManager.run();
    }

    private PlayerObject[] instantiatePlayers(GameProperties properties, BoardView boardView, PiecesConverter piecesConverter,
                                              PointCalculator pointCalculator, Board board) {
        List<PlayerObject> playerObjects = new ArrayList<>();
        for (int i = 0; i < properties.players(); i++) {
            HumanPlayerObject playerObject = createHumanPlayer("Human", piecesConverter, pointCalculator, board, boardView);
            playerObjects.add(playerObject);
        }
        for (int i = 0; i < properties.cpus(); i++) {
            AIPlayerObject playerObject = createAIPlayer("AI", piecesConverter, pointCalculator, board, boardView);
            playerObjects.add(playerObject);
        }
        return playerObjects.toArray(PlayerObject[]::new);
    }

    private HumanPlayerObject createHumanPlayer(String name, PiecesConverter piecesConverter,
                                      PointCalculator pointCalculator, Board board, BoardView boardView) {

        HumanPlayerObject playerObject = instantiate(HumanPlayerObject.class);
        Player player = new Player(name, false);
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        MovementMaker movementMaker = new MovementMaker(piecesConverter, wordPlacer);
        playerObject.configure(movementMaker);
        return playerObject;
    }

    private AIPlayerObject createAIPlayer(String name, PiecesConverter piecesConverter,
                                          PointCalculator pointCalculator, Board board, BoardView boardView) {
        AIPlayerObject playerObject = instantiate(AIPlayerObject.class);
        Player player = new Player(name, true);
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        MovementMaker movementMaker = new MovementMaker(piecesConverter, wordPlacer);
        playerObject.configure(movementMaker);
        return playerObject;
    }

    private Board getBoard(GameProperties properties) {
        return switch (properties.boardType()) {
            case BoardType.Junior -> new JuniorBoard();
            case BoardType.Standard -> new StandardBoard();
            case BoardType.Super -> new SuperBoard();
        };
    }
}
