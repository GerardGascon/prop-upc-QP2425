package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.*;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.turns.Endgame;
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
        Player[] playersData = createPlayersData(properties);
        IGamePlayer[] players = instantiatePlayers(playersData, boardView, piecesConverter, pointCalculator, board);
        Endgame endgame = new Endgame(playersData);
        Turn turnManager = new Turn(endgame, players);
        //TODO: Replace with call to game class
        turnManager.run();

        boardView.updateBoard(board);
    }

    private Player[] createPlayersData(GameProperties properties) {
        return new Player[]{
                new Player("Player1", false),
                new Player("Player2", false),
                new Player("Player3", false),
                new Player("Player4", false),
        };
    }

    private PlayerObject[] instantiatePlayers(Player[] players, BoardView boardView, PiecesConverter piecesConverter,
                                              PointCalculator pointCalculator, Board board) {
        List<PlayerObject> playerObjects = new ArrayList<>();
        for (Player player : players) {
            if (player.getCPU()) {
                AIPlayerObject playerObject = createAIPlayer(player, piecesConverter, pointCalculator, board, boardView);
                playerObjects.add(playerObject);
            } else {
                HumanPlayerObject playerObject = createHumanPlayer(player, piecesConverter, pointCalculator, board, boardView);
                playerObjects.add(playerObject);
            }
        }
        return playerObjects.toArray(PlayerObject[]::new);
    }

    private HumanPlayerObject createHumanPlayer(Player player, PiecesConverter piecesConverter,
                                                PointCalculator pointCalculator, Board board, BoardView boardView) {

        HumanPlayerObject playerObject = instantiate(HumanPlayerObject.class);
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        MovementMaker movementMaker = new MovementMaker(piecesConverter, wordPlacer);
        playerObject.configure(movementMaker, player);
        return playerObject;
    }

    private AIPlayerObject createAIPlayer(Player player, PiecesConverter piecesConverter,
                                          PointCalculator pointCalculator, Board board, BoardView boardView) {
        AIPlayerObject playerObject = instantiate(AIPlayerObject.class);
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        MovementMaker movementMaker = new MovementMaker(piecesConverter, wordPlacer);
        playerObject.configure(movementMaker, player);
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
