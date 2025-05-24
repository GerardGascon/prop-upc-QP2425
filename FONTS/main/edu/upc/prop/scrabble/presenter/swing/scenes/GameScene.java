package edu.upc.prop.scrabble.presenter.swing.scenes;

import edu.upc.prop.scrabble.data.Anchors;
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
import edu.upc.prop.scrabble.domain.actionmaker.SkipActionMaker;
import edu.upc.prop.scrabble.domain.ai.*;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.board.PremiumTileTypeFiller;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordAdder;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.game.IEndScreen;
import edu.upc.prop.scrabble.domain.pieces.*;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.presenter.localization.DictionaryReader;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.swing.objects.AIPlayerObject;
import edu.upc.prop.scrabble.presenter.swing.objects.HumanPlayerObject;
import edu.upc.prop.scrabble.presenter.swing.objects.PlayerObject;
import edu.upc.prop.scrabble.presenter.swing.screens.game.EndScreen;
import edu.upc.prop.scrabble.presenter.swing.screens.game.GameScreen;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.IBlankPieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.BlankPieceSelector;
import edu.upc.prop.scrabble.utils.Rand;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GameScene extends Scene {
    public GameScene(GameProperties properties, JFrame window) {
        Board board = getBoard(properties);
        DAWG dawg = new DAWG();
        CrossChecks crossChecks = switch (properties.language()) {
            case Language.Catalan -> new CatalanCrossChecks(board.getSize());
            case Language.Spanish -> new SpanishCrossChecks(board.getSize());
            case Language.English -> new EnglishCrossChecks(board.getSize());
        };
        Leaderboard leaderboard = new Leaderboard();

        HandView handView = new HandView();
        BlankPieceSelector blankPieceSelector = new BlankPieceSelector();
        BoardView boardView = new BoardView(board.getSize(), handView, blankPieceSelector);
        PremiumTileTypeFiller premiumTileTypeFiller = new PremiumTileTypeFiller(board, boardView);
        premiumTileTypeFiller.run();

        Player[] playersData = createPlayersData(properties);

        PointCalculator pointCalculator = new PointCalculator(board);

        PiecesReader piecesReader = new PiecesReader();
        PiecesConverterFactory piecesConverterFactory = new PiecesConverterFactory(piecesReader);
        PiecesConverter piecesConverter = piecesConverterFactory.run(properties.language());

        Bag bag = generateBag(properties.language());
        fillDAWG(dawg, properties.language());

        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, piecesConverter);

        PlayerObject[] players = instantiatePlayers(playersData, properties.language(), piecesConverter, pointCalculator, dawg, board, anchors, crossChecks);
        Endgame endgame = new Endgame(playersData);
        Turn turnManager = new Turn(endgame, players);

        IEndScreen endScreen = new EndScreen();
        GameStepper stepper = new GameStepper(turnManager, leaderboard, playersData, endScreen);

        configurePlayers(players, playersData, stepper, board, boardView, pointCalculator, bag, piecesConverter,
                anchorUpdater, dawg, crossChecks, handView);

        HandFiller handFiller = new HandFiller(bag, playersData, new Rand());
        handFiller.run();
        boardView.updateBoard();

        SidePanel sidePanel = new SidePanel(playersData);
        generateWindow(window, boardView, sidePanel, handView, blankPieceSelector);

        players[0].startTurn();
    }

    private static void generateWindow(JFrame window, BoardView boardView, SidePanel sidePanel, HandView handView, BlankPieceSelector blankPieceSelector) {
        GameScreen screen = new GameScreen();

        screen.addBoard(boardView);
        screen.addSidePanel(sidePanel);
        screen.addHandView(handView);
        screen.addBlankPieceSelector(blankPieceSelector);

        window.add(screen);
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

    private PlayerObject[] instantiatePlayers(Player[] players, Language language, PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Anchors anchors, CrossChecks crossChecks) {
        List<PlayerObject> playerObjects = new ArrayList<>();
        for (Player player : players) {
            if (player.getCPU()) {
                AIPlayerObject playerObject = instantiate(AIPlayerObject.class);
                playerObjects.add(playerObject);
                AI ai = switch (language) {
                    case Language.Catalan -> new CatalanAI(piecesConverter, pointCalculator, dawg, board, player, anchors, crossChecks);
                    case Language.Spanish -> new SpanishAI(piecesConverter, pointCalculator, dawg, board, player, anchors, crossChecks);
                    case Language.English -> new EnglishAI(piecesConverter, pointCalculator, dawg, board, player, anchors, crossChecks);
                };
                playerObject.configureAI(ai);
            } else {
                HumanPlayerObject playerObject = instantiate(HumanPlayerObject.class);
                playerObjects.add(playerObject);
            }
        }
        return playerObjects.toArray(PlayerObject[]::new);
    }

    private void configurePlayers(PlayerObject[] playerObjects, Player[] players, GameStepper stepper, Board board,
                                  BoardView boardView, PointCalculator pointCalculator, Bag bag,
                                  PiecesConverter piecesConverter, AnchorUpdater anchorUpdater, DAWG dawg,
                                  CrossChecks crossChecks, IHandView handView) {
        for (int i = 0; i < players.length; i++) {
            configurePlayer(i, playerObjects[i], players[i], stepper, board, boardView, pointCalculator, bag,
                    piecesConverter, anchorUpdater, dawg, crossChecks, handView);
        }
    }

    public void configurePlayer(int index, PlayerObject playerObject, Player player, GameStepper stepper, Board board,
                                BoardView boardView, PointCalculator pointCalculator, Bag bag,
                                PiecesConverter piecesConverter, AnchorUpdater anchorUpdater, DAWG dawg,
                                CrossChecks crossChecks, IHandView handView) {
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        PlaceActionMaker placeActionMaker = new PlaceActionMaker(player, bag, wordPlacer, stepper, piecesConverter,
                board, anchorUpdater, crossChecks, dawg, new Rand());
        // TODO: hand view updater
        DrawActionMaker drawActionMaker = new DrawActionMaker(bag, player, new Rand(), handView, stepper, piecesConverter);
        SkipActionMaker skipActionMaker = new SkipActionMaker(stepper);
        playerObject.configure(index, placeActionMaker, player, drawActionMaker, skipActionMaker, handView);
    }

    private Board getBoard(GameProperties properties) {
        return switch (properties.boardType()) {
            case BoardType.Junior -> new JuniorBoard();
            case BoardType.Standard -> new StandardBoard();
            case BoardType.Super -> new SuperBoard();
        };
    }
}
