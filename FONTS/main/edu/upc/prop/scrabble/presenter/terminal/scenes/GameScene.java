package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.*;
import edu.upc.prop.scrabble.data.crosschecks.CatalanCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.SpanishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.board.*;
import edu.upc.prop.scrabble.domain.crosschecks.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.pieces.*;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.IGamePlayer;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.terminal.BoardView;
import edu.upc.prop.scrabble.presenter.terminal.PieceDisplay;
import edu.upc.prop.scrabble.presenter.terminal.players.AIPlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.players.HumanPlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.players.PlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.movements.MovementMaker;
import edu.upc.prop.scrabble.utils.Rand;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene {
    public GameScene(GameProperties properties) {
        Board board = getBoard(properties);
        DAWG dawg = new DAWG();
        CrossChecks crossChecks = switch (properties.language()) {
            case Language.Catalan -> new CatalanCrossChecks(board, dawg);
            case Language.Spanish -> new SpanishCrossChecks(board, dawg);
            case Language.English -> new EnglishCrossChecks(board, dawg);
        };

        BoardView boardView = instantiate(BoardView.class);
        Player[] playersData = createPlayersData(properties);

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);

        PiecesConverter piecesConverter = switch (properties.language()) {
            case Language.Catalan -> new CatalanPiecesConverter();
            case Language.Spanish -> new SpanishPiecesConverter();
            case Language.English -> new PiecesConverter();
        };

        Bag bag = new Bag(); //TODO: Fill bag
        MovementBoundsChecker boundsChecker = new MovementBoundsChecker(board, piecesConverter);
        MovementCleaner movementCleaner = new MovementCleaner(board, piecesConverter);
        WordValidator wordValidator = new WordValidator(dawg);
        PresentPiecesWordCompleter presentPiecesWordCompleter = new PresentPiecesWordCompleter(wordGetter);
        CrossCheckUpdater crossCheckUpdater = new CrossCheckUpdater(piecesConverter, crossChecks, board, dawg);
        IPiecePrinter piecePrinter = new PieceDisplay();

        IGamePlayer[] players = instantiatePlayers(playersData, board, boardView, pointCalculator, bag, piecePrinter,
                boundsChecker, wordValidator, movementCleaner, presentPiecesWordCompleter, crossCheckUpdater);
        Endgame endgame = new Endgame(playersData);
        Turn turnManager = new Turn(endgame, players);

        boardView.updateBoard(board);

        //TODO: Add pieces to players
        //TODO: Replace with call to game class
        turnManager.run();
    }

    private Player[] createPlayersData(GameProperties properties) {
        return new Player[]{
                new Player("Player1", false),
                new Player("Player2", false),
                new Player("Player3", false),
                new Player("Player4", false),
        };
    }

    private PlayerObject[] instantiatePlayers(Player[] players, Board board, BoardView boardView,
                                              PointCalculator pointCalculator, Bag bag, IPiecePrinter piecePrinter,
                                              MovementBoundsChecker boundsChecker, WordValidator wordValidator,
                                              MovementCleaner movementCleaner,
                                              PresentPiecesWordCompleter presentPiecesWordCompleter,
                                              CrossCheckUpdater crossCheckUpdater) {
        List<PlayerObject> playerObjects = new ArrayList<>();
        for (Player player : players) {
            if (player.getCPU()) {
                AIPlayerObject playerObject = instantiate(AIPlayerObject.class);
                configurePlayer(playerObject, player, board, boardView, pointCalculator, bag, piecePrinter,
                        boundsChecker, wordValidator, movementCleaner, presentPiecesWordCompleter, crossCheckUpdater);
                playerObjects.add(playerObject);
            } else {
                HumanPlayerObject playerObject = instantiate(HumanPlayerObject.class);
                configurePlayer(playerObject, player, board, boardView, pointCalculator, bag, piecePrinter,
                        boundsChecker, wordValidator, movementCleaner, presentPiecesWordCompleter, crossCheckUpdater);
                playerObjects.add(playerObject);
            }
        }
        return playerObjects.toArray(PlayerObject[]::new);
    }

    public void configurePlayer(PlayerObject playerObject, Player player, Board board, BoardView boardView,
                                       PointCalculator pointCalculator, Bag bag, IPiecePrinter piecePrinter,
                                       MovementBoundsChecker boundsChecker, WordValidator wordValidator,
                                       MovementCleaner movementCleaner,
                                       PresentPiecesWordCompleter presentPiecesWordCompleter,
                                       CrossCheckUpdater crossCheckUpdater) {
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        PiecesInHandGetter piecesInHandGetter = new PiecesInHandGetter(bag, player, piecePrinter, new Rand());
        PlaceActionMaker placeActionMaker = new PlaceActionMaker(boundsChecker, wordValidator, piecesInHandGetter,
                movementCleaner, wordPlacer, presentPiecesWordCompleter, crossCheckUpdater);
        MovementMaker movementMaker = new MovementMaker(placeActionMaker);
        playerObject.configure(movementMaker, player);
    }

    private Board getBoard(GameProperties properties) {
        return switch (properties.boardType()) {
            case BoardType.Junior -> new JuniorBoard();
            case BoardType.Standard -> new StandardBoard();
            case BoardType.Super -> new SuperBoard();
        };
    }
}
