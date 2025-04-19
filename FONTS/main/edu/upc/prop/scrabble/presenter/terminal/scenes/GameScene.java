package edu.upc.prop.scrabble.presenter.terminal.scenes;

import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.*;
import edu.upc.prop.scrabble.data.crosschecks.CatalanCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.SpanishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.properties.GameProperties;
import edu.upc.prop.scrabble.data.properties.Language;
import edu.upc.prop.scrabble.domain.actionmaker.DrawActionMaker;
import edu.upc.prop.scrabble.domain.actionmaker.PlaceActionMaker;
import edu.upc.prop.scrabble.domain.board.*;
import edu.upc.prop.scrabble.domain.crosschecks.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.localization.DictionaryReader;
import edu.upc.prop.scrabble.domain.localization.PiecesReader;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.pieces.*;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.terminal.BoardView;
import edu.upc.prop.scrabble.presenter.terminal.PieceDisplay;
import edu.upc.prop.scrabble.presenter.terminal.actionmaker.HandView;
import edu.upc.prop.scrabble.presenter.terminal.players.AIPlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.players.HumanPlayerObject;
import edu.upc.prop.scrabble.presenter.terminal.players.PlayerObject;
import edu.upc.prop.scrabble.utils.Rand;

import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene {
    public GameScene(GameProperties properties) {
        Board board = getBoard(properties);
        DAWG dawg = new DAWG();
        CrossChecks crossChecks = switch (properties.language()) {
            case Language.Catalan -> new CatalanCrossChecks(board.getSize());
            case Language.Spanish -> new SpanishCrossChecks(board.getSize());
            case Language.English -> new EnglishCrossChecks(board.getSize());
        };
        Leaderboard leaderboard = new Leaderboard();

        BoardView boardView = instantiate(BoardView.class);
        Player[] playersData = createPlayersData(properties);

        WordGetter wordGetter = new WordGetter(board);
        PointCalculator pointCalculator = new PointCalculator(board, wordGetter);

        PiecesConverter piecesConverter = switch (properties.language()) {
            case Language.Catalan -> new CatalanPiecesConverter();
            case Language.Spanish -> new SpanishPiecesConverter();
            case Language.English -> new PiecesConverter();
        };

        Bag bag = generateBag(properties.language());
        fillDAWG(dawg, properties.language());

        MovementBoundsChecker boundsChecker = new MovementBoundsChecker(board, piecesConverter);
        MovementCleaner movementCleaner = new MovementCleaner(board, piecesConverter);
        WordValidator wordValidator = new WordValidator(dawg);
        PresentPiecesWordCompleter presentPiecesWordCompleter = new PresentPiecesWordCompleter(wordGetter);
        CrossCheckUpdater crossCheckUpdater = new CrossCheckUpdater(piecesConverter, crossChecks, board, dawg);
        IPiecePrinter piecePrinter = new PieceDisplay();

        PlayerObject[] players = instantiatePlayers(playersData);
        Endgame endgame = new Endgame(playersData);
        Turn turnManager = new Turn(endgame, players);
        GameStepper stepper = new GameStepper(turnManager, leaderboard, playersData);

        configurePlayers(players, playersData, stepper, board, boardView, pointCalculator, bag, piecePrinter, boundsChecker,
                wordValidator, movementCleaner, presentPiecesWordCompleter, crossCheckUpdater, piecesConverter);

        HandFiller handFiller = new HandFiller(bag, playersData, new Rand());
        handFiller.run();
        boardView.updateBoard(board);

        players[0].startTurn();
    }

    private static void fillDAWG(DAWG dawg, Language language) {
        WordAdder wordAdder = new WordAdder(dawg);
        DictionaryReader reader = new DictionaryReader();
        String dictionary = reader.run(language);
        dictionary.lines().forEach(wordAdder::run);
    }

    private static Bag generateBag(Language language) {
        PiecesReader reader = new PiecesReader();
        String piecesFile = reader.run(language);
        PieceGenerator pieceGenerator = new PieceGenerator();
        BagFiller bagFiller = new BagFiller(pieceGenerator);
        return bagFiller.run(piecesFile);
    }

    private Player[] createPlayersData(GameProperties properties) {
        List<Player> players = new ArrayList<>();
        for (String name : properties.players())
            players.add(new Player(name, false));
        for (String name : properties.Cpus())
            players.add(new Player(name, true));
        return players.toArray(Player[]::new);
    }

    private PlayerObject[] instantiatePlayers(Player[] players) {
        List<PlayerObject> playerObjects = new ArrayList<>();
        for (Player player : players) {
            if (player.getCPU()) {
                AIPlayerObject playerObject = instantiate(AIPlayerObject.class);
                playerObjects.add(playerObject);
            } else {
                HumanPlayerObject playerObject = instantiate(HumanPlayerObject.class);
                playerObjects.add(playerObject);
            }
        }
        return playerObjects.toArray(PlayerObject[]::new);
    }

    private void configurePlayers(PlayerObject[] playerObjects, Player[] players, GameStepper stepper, Board board,
                                  BoardView boardView, PointCalculator pointCalculator, Bag bag,
                                  IPiecePrinter piecePrinter, MovementBoundsChecker boundsChecker,
                                  WordValidator wordValidator, MovementCleaner movementCleaner,
                                  PresentPiecesWordCompleter presentPiecesWordCompleter,
                                  CrossCheckUpdater crossCheckUpdater, PiecesConverter piecesConverter) {
        for (int i = 0; i < players.length; i++) {
            configurePlayer(playerObjects[i], players[i], stepper, board, boardView, pointCalculator, bag, piecePrinter,
                    boundsChecker, wordValidator, movementCleaner, presentPiecesWordCompleter, crossCheckUpdater,
                    piecesConverter);
        }
    }

    public void configurePlayer(PlayerObject playerObject, Player player, GameStepper stepper, Board board,
                                BoardView boardView, PointCalculator pointCalculator, Bag bag,
                                IPiecePrinter piecePrinter, MovementBoundsChecker boundsChecker,
                                WordValidator wordValidator, MovementCleaner movementCleaner,
                                PresentPiecesWordCompleter presentPiecesWordCompleter,
                                CrossCheckUpdater crossCheckUpdater, PiecesConverter piecesConverter) {
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        PiecesInHandGetter piecesInHandGetter = new PiecesInHandGetter(bag, player, piecePrinter, new Rand());
        PlaceActionMaker placeActionMaker = new PlaceActionMaker(boundsChecker, wordValidator, piecesInHandGetter,
                movementCleaner, wordPlacer, presentPiecesWordCompleter, crossCheckUpdater, stepper, piecesConverter,
                board);
        DrawActionMaker drawActionMaker = new DrawActionMaker(bag, player, new Rand(), new HandView());
        playerObject.configure(placeActionMaker, player, drawActionMaker);
    }

    private Board getBoard(GameProperties properties) {
        return switch (properties.boardType()) {
            case BoardType.Junior -> new JuniorBoard();
            case BoardType.Standard -> new StandardBoard();
            case BoardType.Super -> new SuperBoard();
        };
    }
}
