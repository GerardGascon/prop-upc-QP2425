package edu.upc.prop.scrabble.presenter.swing.scenes;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.GameData;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.*;
import edu.upc.prop.scrabble.data.crosschecks.CatalanCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.EnglishCrossChecks;
import edu.upc.prop.scrabble.data.crosschecks.SpanishCrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.leaderboard.Leaderboard;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
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
import edu.upc.prop.scrabble.domain.pieces.*;
import edu.upc.prop.scrabble.domain.turns.Endgame;
import edu.upc.prop.scrabble.domain.turns.Turn;
import edu.upc.prop.scrabble.persistence.platform.gson.deserializers.GsonDeserializer;
import edu.upc.prop.scrabble.persistence.platform.gson.serializers.GsonSerializer;
import edu.upc.prop.scrabble.persistence.platform.gson.streamers.SaveReader;
import edu.upc.prop.scrabble.persistence.platform.gson.streamers.SaveWriter;
import edu.upc.prop.scrabble.persistence.runtime.controllers.DataCollector;
import edu.upc.prop.scrabble.persistence.runtime.controllers.DataRestorer;
import edu.upc.prop.scrabble.persistence.runtime.controllers.GameLoader;
import edu.upc.prop.scrabble.persistence.runtime.controllers.GameSaver;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.IDeserializer;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveReader;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISaveWriter;
import edu.upc.prop.scrabble.persistence.runtime.interfaces.ISerializer;
import edu.upc.prop.scrabble.presenter.localization.DictionaryReader;
import edu.upc.prop.scrabble.presenter.localization.PiecesReader;
import edu.upc.prop.scrabble.presenter.scenes.Scene;
import edu.upc.prop.scrabble.presenter.swing.objects.AIPlayerObject;
import edu.upc.prop.scrabble.presenter.swing.objects.HumanPlayerObject;
import edu.upc.prop.scrabble.presenter.swing.objects.PlayerObject;
import edu.upc.prop.scrabble.presenter.swing.screens.game.endscreen.EndScreen;
import edu.upc.prop.scrabble.presenter.swing.screens.game.GameScreen;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.BoardView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.board.sidepanel.SidePanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.hand.HandView;
import edu.upc.prop.scrabble.domain.actionmaker.IHandView;
import edu.upc.prop.scrabble.presenter.swing.screens.game.pieceselector.BlankPieceSelector;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.ActionButtonPanel;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.draw.DrawAction;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.place.PlaceAction;
import edu.upc.prop.scrabble.presenter.swing.screens.game.turnaction.skip.SkipAction;
import edu.upc.prop.scrabble.presenter.swing.screens.menu.pause.PauseMenu;
import edu.upc.prop.scrabble.utils.Rand;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa l'escena principal del joc de Scrabble.
 *
 * @author Gerard Gascón
 */
public class GameScene extends Scene {
    /**
     * Nom del fitxer on es desa i es carrega la partida guardada.
     */
    public static final String SAVE_FILE_NAME = "save.data";
    private final JFrame window;
    private final GameScreen gameScreen;

    /**
     * Constructor que crea la escena del joc.
     *
     * @param properties Propietats que configuren el joc.
     * @param window     Finestra principal del joc.
     */
    public GameScene(GameProperties properties, JFrame window) {
        DataCollector dataCollector = new DataCollector();
        ISerializer serializer = new GsonSerializer();
        ISaveWriter saveWriter = new SaveWriter();
        GameSaver saver = new GameSaver(dataCollector, serializer, saveWriter, SAVE_FILE_NAME);

        this.window = window;
        gameScreen = new GameScreen();

        DataRestorer dataRestorer = new DataRestorer();
        IDeserializer deserializer = new GsonDeserializer();
        ISaveReader saveReader = new SaveReader();
        GameLoader loader = new GameLoader(dataRestorer, deserializer, saveReader, SAVE_FILE_NAME);

        if (properties.reload() && saveReader.exists(SAVE_FILE_NAME)) {
            loadGame(window, dataRestorer, loader, saver, dataCollector);
        } else {
            createNewGame(properties, window, saver, dataCollector);
        }
    }

    @Override
    protected void onDetach() {
        super.onDetach();
        window.remove(gameScreen);
    }

    /**
     * Crea una nova partida amb les propietats indicades.
     *
     * @param properties    Propietats del joc.
     * @param window        Finestra on es mostrarà la partida.
     * @param saver         Objecte encarregat de guardar la partida.
     * @param dataCollector Objecte encarregat de recopilar dades persistents.
     */
    private void createNewGame(GameProperties properties, JFrame window, GameSaver saver, DataCollector dataCollector) {
        Board board = getBoard(properties);
        Player[] playersData = createPlayersData(properties);
        Bag bag = generateBag(properties.language());
        resolveDependencies(true, window, saver, dataCollector, board, bag, properties.language(), playersData, 0, 0);
    }

    /**
     * Carrega una partida desada prèviament.
     *
     * @param window        Finestra on es mostrarà la partida.
     * @param dataRestorer  Objecte encarregat de restaurar les dades.
     * @param loader        Objecte encarregat de carregar la partida.
     * @param saver         Objecte encarregat de guardar la partida.
     * @param dataCollector Objecte encarregat de recopilar dades persistents.
     */
    private void loadGame(JFrame window, DataRestorer dataRestorer, GameLoader loader, GameSaver saver, DataCollector dataCollector) {
        Board board = new StandardBoard();
        Bag bag = new Bag();
        GameData gameData = new GameData();

        dataRestorer.addPersistableObjects(board, gameData, bag);
        loader.run();

        board = switch (gameData.getBoardType()) {
            case BoardType.Junior -> new JuniorBoard(board);
            case BoardType.Standard -> new StandardBoard(board);
            case BoardType.Super -> new SuperBoard(board);
        };

        Player[] playersData = gameData.getPlayers();

        resolveDependencies(false, window, saver, dataCollector, board, bag, gameData.getLanguage(), playersData, gameData.getTurnNumber(), gameData.getSkipCounter());
    }

    /**
     * Resol i configura totes les dependències necessàries per al funcionament del joc.
     *
     * @param isNewGame     Indica si aquesta partida és nova.
     * @param window        Finestra principal.
     * @param saver         Objecte encarregat de guardar la partida.
     * @param dataCollector Objecte encarregat de recopilar dades persistents.
     * @param board         Tauler del joc.
     * @param language      Idioma del joc.
     * @param playersData   Dades dels jugadors.
     * @param turnNumber    Número del torn actual.
     * @param skipCounter   Comptador de salts de torn.
     */
    private void resolveDependencies(boolean isNewGame, JFrame window, GameSaver saver, DataCollector dataCollector, Board board, Bag bag, Language language, Player[] playersData, int turnNumber, int skipCounter) {
        DAWG dawg = new DAWG();
        CrossChecks crossChecks = switch (language) {
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
        boardView.endPlace();

        PointCalculator pointCalculator = new PointCalculator(board);

        PiecesReader piecesReader = new PiecesReader();
        PiecesConverterFactory piecesConverterFactory = new PiecesConverterFactory(piecesReader);
        PiecesConverter piecesConverter = piecesConverterFactory.run(language);

        fillDAWG(dawg, language);

        Anchors anchors = new Anchors();
        AnchorUpdater anchorUpdater = new AnchorUpdater(anchors, board, piecesConverter);

        PlayerObject[] players = instantiatePlayers(playersData, language, piecesConverter, pointCalculator, dawg, board, anchors, crossChecks);
        Endgame endgame = new Endgame(playersData);
        Turn turnManager = new Turn(endgame, players);

        EndScreen endScreen = new EndScreen();
        GameStepper stepper = new GameStepper(turnManager, leaderboard, playersData, endScreen);

        if (isNewGame) {
            HandFiller handFiller = new HandFiller(bag, playersData, new Rand());
            handFiller.run();
        } else {
            placePieces(board, boardView);
        }

        SidePanel sidePanel = new SidePanel(playersData);
        PauseMenu pauseMenu = new PauseMenu(saver);

        DrawAction drawAction = new DrawAction(handView);
        PlaceAction placeAction = new PlaceAction(boardView);
        SkipAction skipAction = new SkipAction();
        ActionButtonPanel panel = new ActionButtonPanel(drawAction, placeAction, skipAction);

        configurePlayers(players, playersData, stepper, board, boardView, pointCalculator, bag, piecesConverter,
                anchorUpdater, dawg, crossChecks, handView, panel, sidePanel);

        generateWindow(window, boardView, sidePanel, handView, blankPieceSelector, pauseMenu, panel, endScreen);

        GameData gameData = new GameData();
        gameData.setLanguage(language);
        gameData.setPlayers(playersData);
        gameData.setBoardType(board.getType());
        gameData.setTurnNumber(turnNumber);
        gameData.setSkipCounter(skipCounter);

        dataCollector.addPersistableObjects(board, gameData, bag);

        players[0].startTurn();
    }

    private static void placePieces(Board board, BoardView boardView) {
        for (int i = 0; i < board.getSize(); i++) {
            for (int j = 0; j < board.getSize(); j++) {
                Piece p = board.getCellPiece(i, j);
                if (p != null) {
                    boardView.updateCell(p.letter(), p.value(), i, j);
                }
            }
        }
    }

    /**
     * Genera i afegeix les diferents vistes i panells a la finestra del joc.
     *
     * @param window             Finestra principal.
     * @param boardView          Vista del tauler.
     * @param sidePanel          Panell lateral amb informació dels jugadors.
     * @param handView           Vista de la mà del jugador.
     * @param blankPieceSelector Selector de fitxes en blanc.
     * @param pauseMenu          Menú de pausa.
     * @param actionPanel        Panell d'accions.
     * @param endScreen          Pantalla del final de partida.
     */
    private void generateWindow(JFrame window, BoardView boardView, SidePanel sidePanel, HandView handView, BlankPieceSelector blankPieceSelector, PauseMenu pauseMenu, ActionButtonPanel actionPanel, EndScreen endScreen) {
        gameScreen.addBoard(boardView);
        gameScreen.addSidePanel(sidePanel);
        gameScreen.addHandView(handView);
        gameScreen.addBlankPieceSelector(blankPieceSelector);
        gameScreen.addPauseButton(pauseMenu);
        gameScreen.addActionPanel(actionPanel);
        gameScreen.addEndScreen(endScreen);

        window.add(gameScreen);
    }

    /**
     * Omple l'estructura DAWG amb paraules segons l'idioma del joc.
     *
     * @param dawg     Estructura DAWG on es guarden les paraules.
     * @param language Idioma del joc.
     */
    private static void fillDAWG(DAWG dawg, Language language) {
        WordAdder wordAdder = new WordAdder(dawg);
        DictionaryReader reader = new DictionaryReader();
        String dictionary = reader.run(language);
        dictionary.lines().forEach(wordAdder::run);
    }

    /**
     * Genera una bossa de fitxes segons l'idioma del joc.
     *
     * @param language Idioma del joc.
     * @return Bossa amb les fitxes configurades.
     */
    private static Bag generateBag(Language language) {
        PiecesReader reader = new PiecesReader();
        String piecesFile = reader.run(language);
        PieceGenerator pieceGenerator = new PieceGenerator();
        BagFiller bagFiller = new BagFiller(pieceGenerator);
        return bagFiller.run(piecesFile);
    }

    /**
     * Crea un array de jugadors (dades bàsiques) a partir de les propietats del joc.
     *
     * @param properties Propietats del joc.
     * @return Array de jugadors.
     */
    private Player[] createPlayersData(GameProperties properties) {
        List<Player> players = new ArrayList<>();
        for (String name : properties.players())
            players.add(new Player(name, false));
        for (String name : properties.Cpus())
            players.add(new Player(name, true));
        return players.toArray(Player[]::new);
    }

    /**
     * Instancia els objectes de jugadors, diferenciant entre jugadors humans i IA, segons l'idioma.
     *
     * @param players         Array de jugadors amb dades bàsiques.
     * @param language        Idioma del joc.
     * @param piecesConverter Convertidor de fitxes segons l'idioma.
     * @param pointCalculator Calculadora de punts.
     * @param dawg            Estructura DAWG amb paraules.
     * @param board           Tauler del joc.
     * @param anchors         Ancoratges per a jugades.
     * @param crossChecks     Objecte de validació de paraules creuades.
     * @return Array d'objectes PlayerObject instanciats.
     */
    private PlayerObject[] instantiatePlayers(Player[] players, Language language, PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Anchors anchors, CrossChecks crossChecks) {
        List<PlayerObject> playerObjects = new ArrayList<>();
        for (Player player : players) {
            if (player.getCPU()) {
                AIPlayerObject playerObject = instantiate(AIPlayerObject.class);
                playerObjects.add(playerObject);
                AI ai = switch (language) {
                    case Language.Catalan ->
                            new CatalanAI(piecesConverter, pointCalculator, dawg, board, player, anchors, crossChecks);
                    case Language.Spanish ->
                            new SpanishAI(piecesConverter, pointCalculator, dawg, board, player, anchors, crossChecks);
                    case Language.English ->
                            new EnglishAI(piecesConverter, pointCalculator, dawg, board, player, anchors, crossChecks);
                };
                playerObject.configureAI(ai);
            } else {
                HumanPlayerObject playerObject = instantiate(HumanPlayerObject.class);
                playerObjects.add(playerObject);
            }
        }
        return playerObjects.toArray(PlayerObject[]::new);
    }

    /**
     * Configura les propietats i dependències de cada jugador per al desenvolupament correcte del joc.
     *
     * @param playerObjects   Objectes PlayerObject.
     * @param players         Dades bàsiques dels jugadors.
     * @param stepper         Gestor de torns.
     * @param board           Tauler del joc.
     * @param boardView       Vista del tauler.
     * @param pointCalculator Calculadora de punts.
     * @param bag             Bossa de fitxes.
     * @param piecesConverter Convertidor de fitxes.
     * @param anchorUpdater   Actualitzador d'ancoratges.
     * @param dawg            Estructura DAWG.
     * @param crossChecks     Validació de paraules creuades.
     * @param handView        Vista de la mà del jugador.
     * @param actionPanel     Panell d'accions del jugador.
     */
    private void configurePlayers(PlayerObject[] playerObjects, Player[] players, GameStepper stepper, Board board,
                                  BoardView boardView, PointCalculator pointCalculator, Bag bag,
                                  PiecesConverter piecesConverter, AnchorUpdater anchorUpdater, DAWG dawg,
                                  CrossChecks crossChecks, IHandView handView, ActionButtonPanel actionPanel, SidePanel sidePanel) {
        for (int i = 0; i < players.length; i++) {
            configurePlayer(i, playerObjects[i], players[i], stepper, board, boardView, pointCalculator, bag,
                    piecesConverter, anchorUpdater, dawg, crossChecks, handView, actionPanel, sidePanel);
        }
    }

    /**
     * Configura un jugador amb totes les seves dependències i accions.
     *
     * @param playerObject    Objecte que representa el jugador (humà o IA).
     * @param player          Dades bàsiques del jugador.
     * @param stepper         Gestor del torn de joc.
     * @param board           Tauler del joc.
     * @param boardView       Vista del tauler.
     * @param pointCalculator Calculadora de punts per a la partida.
     * @param bag             Bossa de fitxes disponible.
     * @param piecesConverter Convertidor de fitxes segons idioma.
     * @param anchorUpdater   Actualitzador d'ancoratges per a jugades.
     * @param dawg            Estructura DAWG que conté el diccionari.
     * @param crossChecks     Objecte per a la validació de paraules creuades.
     * @param handView        Vista de la mà del jugador.
     * @param actionPanel     Panell d'accions del jugador.
     */
    private void configurePlayer(int playerIndex, PlayerObject playerObject, Player player, GameStepper stepper, Board board,
                                 BoardView boardView, PointCalculator pointCalculator, Bag bag,
                                 PiecesConverter piecesConverter, AnchorUpdater anchorUpdater, DAWG dawg,
                                 CrossChecks crossChecks, IHandView handView, ActionButtonPanel actionPanel, SidePanel sidePanel) {
        WordPlacer wordPlacer = new WordPlacer(player, board, boardView, pointCalculator);
        PlaceActionMaker placeActionMaker = new PlaceActionMaker(player, bag, wordPlacer, stepper, piecesConverter,
                board, anchorUpdater, crossChecks, dawg, new Rand());
        DrawActionMaker drawActionMaker = new DrawActionMaker(bag, player, new Rand(), handView, stepper, piecesConverter);
        SkipActionMaker skipActionMaker = new SkipActionMaker(stepper);
        playerObject.configure(playerIndex, placeActionMaker, player, drawActionMaker, skipActionMaker, handView, actionPanel, sidePanel);
    }

    /**
     * Retorna el tauler segons la configuració especificada a les propietats.
     *
     * @param properties Propietats del joc que defineixen el tipus de tauler.
     * @return Tauler configurat.
     */
    private Board getBoard(GameProperties properties) {
        return switch (properties.boardType()) {
            case BoardType.Junior -> new JuniorBoard();
            case BoardType.Standard -> new StandardBoard();
            case BoardType.Super -> new SuperBoard();
        };
    }
}
