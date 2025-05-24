package edu.upc.prop.scrabble.domain.actionmaker;

import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.exceptions.PlayerDoesNotHavePieceException;
import edu.upc.prop.scrabble.data.pieces.Bag;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.ai.AnchorUpdater;
import edu.upc.prop.scrabble.domain.board.PresentPiecesWordCompleter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.ai.CrossCheckUpdater;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.exceptions.InitialMoveNotInCenterException;
import edu.upc.prop.scrabble.domain.exceptions.MovementOutsideOfBoardException;
import edu.upc.prop.scrabble.domain.exceptions.WordDoesNotExistException;
import edu.upc.prop.scrabble.domain.exceptions.WordNotConnectedToOtherWordsException;
import edu.upc.prop.scrabble.domain.game.GameStepper;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandGetter;
import edu.upc.prop.scrabble.domain.movement.MovementCleaner;
import edu.upc.prop.scrabble.domain.movement.MovementBoundsChecker;
import edu.upc.prop.scrabble.domain.turns.TurnResult;
import edu.upc.prop.scrabble.utils.IRand;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.Arrays;

/**
 * Gestiona els passos necessaris per realitzar una acció de col·locació de paraula
 * en el joc de Scrabble.
 * Això inclou validar el moviment, comprovar que la paraula existeix i col·locar
 * la paraula al taulell amb les peces necessàries.
 *
 * @author Gerard Gascón
 */
public class PlaceActionMaker {
    /**
     * Component que comprova que el moviment es mantingui dins dels límits del taulell.
     */
    private final MovementBoundsChecker movementBoundsChecker;

    /**
     * Component que valida que una paraula existeixi al diccionari.
     */
    private final WordValidator wordValidator;

    /**
     * Component que obté les peces necessàries que té el jugador a la mà.
     */
    private final PiecesInHandGetter piecesInHandGetter;

    /**
     * Component que neteja el moviment, obtenint les peces i posicions necessàries.
     */
    private final MovementCleaner movementCleaner;

    /**
     * Component que col·loca les peces al taulell.
     */
    private final WordPlacer wordPlacer;

    /**
     * Component que completa les paraules que es formen amb les peces presents al taulell.
     */
    private final PresentPiecesWordCompleter presentPiecesWordCompleter;

    /**
     * Component que actualitza les comprovacions creuades després d’un moviment.
     */
    private final CrossCheckUpdater crossCheckUpdater;

    /**
     * Component que gestiona el pas del torn i actualitza l'estat del joc.
     */
    private final GameStepper stepper;

    /**
     * Component que converteix paraules en conjunts de peces.
     */
    private final PiecesConverter piecesConverter;

    /**
     * El taulell actual del joc.
     */
    private final Board board;

    /**
     * Component que actualitza els punts d’ancoratge del taulell.
     */
    private final AnchorUpdater anchorUpdater;

    /**
     * Crea una instància de PlaceActionMaker que gestiona accions de col·locació de paraules.
     *
     * @param player             El jugador que fa l'acció.
     * @param bag                La bossa de peces del joc.
     * @param wordPlacer         Component que col·loca la paraula al taulell.
     * @param stepper            Controlador per avançar la lògica del joc després del moviment.
     * @param piecesConverter    Utilitat per convertir paraules en conjunts de peces.
     * @param board              El taulell actual del joc.
     * @param anchorUpdater      Component que actualitza els punts d’ancoratge.
     * @param crossChecks        Gestor de les comprovacions creuades.
     * @param dawg               Diccionari DAWG per validar paraules.
     * @param rand               Generador aleatori utilitzat per seleccionar peces.
     */
    public PlaceActionMaker(Player player, Bag bag,
                            WordPlacer wordPlacer,
                            GameStepper stepper, PiecesConverter piecesConverter,
                            Board board, AnchorUpdater anchorUpdater, CrossChecks crossChecks, DAWG dawg, IRand rand) {
        this.movementBoundsChecker = new MovementBoundsChecker(board, piecesConverter);
        this.movementCleaner = new MovementCleaner(board, piecesConverter);
        this.wordValidator = new WordValidator(dawg);
        this.piecesInHandGetter = new PiecesInHandGetter(bag, player, rand);
        this.wordPlacer = wordPlacer;
        this.presentPiecesWordCompleter = new PresentPiecesWordCompleter(board);
        this.crossCheckUpdater = new CrossCheckUpdater(piecesConverter, crossChecks, board, dawg);
        this.stepper = stepper;
        this.piecesConverter = piecesConverter;
        this.board = board;
        this.anchorUpdater = anchorUpdater;
    }

    /**
     * Executa una acció de col·locació de paraula al taulell.
     * Aquest mètode fa totes les validacions necessàries i actualitza el taulell,
     * les peces del jugador i l'estat del joc.
     *
     * @param movement El moviment que conté la paraula i la seva posició.
     * @throws WordDoesNotExistException Si la paraula no existeix al diccionari.
     * @throws MovementOutsideOfBoardException Si el moviment està fora dels límits del taulell.
     * @throws PlayerDoesNotHavePieceException Si el jugador no té les peces necessàries.
     * @see Movement
     */
    public void run(Movement movement) {
        assertInsideOfBounds(movement);
        Pair<Piece, Vector2>[] necessaryPiecesPositions = movementCleaner.run(movement);
        assertInitialMoveInCenter(necessaryPiecesPositions);
        assertWordIsConnected(movement, necessaryPiecesPositions);
        Piece[] necessaryPieces = extractNecessaryPieces(necessaryPiecesPositions);
        Vector2[] necessaryPositions = extractNecessaryPositions(necessaryPiecesPositions);

        assertNewWordsExist(necessaryPieces, necessaryPositions);
        Piece[] piecesInHand = piecesInHandGetter.run(necessaryPieces);
        wordPlacer.run(piecesInHand, movement.x(), movement.y(), movement.direction());
        crossCheckUpdater.run(movement);
        anchorUpdater.run(movement);
        stepper.run(TurnResult.Place);
    }

    /**
     * Verifica que la paraula estigui connectada a altres paraules ja presents al taulell.
     * Si no és així i el taulell no està buit, llança una excepció.
     *
     * @param movement El moviment realitzat.
     * @param necessaryPiecesPositions Les peces i posicions necessàries per al moviment.
     */
    private void assertWordIsConnected(Movement movement, Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        if (board.isEmpty())
            return;

        Piece[] necessaryPieces = piecesConverter.run(movement.word());
        if (necessaryPieces.length == necessaryPiecesPositions.length)
            throw new WordNotConnectedToOtherWordsException("The word is not connected to any other words on the board.");
    }

    /**
     * Comprova que el primer moviment passi pel centre del taulell.
     * Si no és així, llança una excepció.
     *
     * @param necessaryPiecesPositions Les peces i posicions necessàries per al moviment.
     */
    private void assertInitialMoveInCenter(Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        if (!board.isEmpty())
            return;
        boolean inCenter = false;
        for (Pair<Piece, Vector2> piece: necessaryPiecesPositions) {
            if (board.isCenter(piece.second().x, piece.second().y)){
                inCenter = true;
                break;
            }
        }

        if (!inCenter)
            throw new InitialMoveNotInCenterException("The first move must go through the center of the board.");
    }

    /**
     * Extreu només les peces necessàries de la llista de parells (peca, posició).
     *
     * @param necessaryPiecesPositions Llista de parells (peca, posició).
     * @return Array amb les peces necessàries.
     */
    private Piece[] extractNecessaryPieces(Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        return Arrays.stream(necessaryPiecesPositions).map(Pair::first).toArray(Piece[]::new);
    }

    /**
     * Extreu només les posicions necessàries de la llista de parells (peca, posició).
     *
     * @param necessaryPiecesPositions Llista de parells (peca, posició).
     * @return Array amb les posicions necessàries.
     */
    private Vector2[] extractNecessaryPositions(Pair<Piece, Vector2>[] necessaryPiecesPositions) {
        return Arrays.stream(necessaryPiecesPositions).map(Pair::second).toArray(Vector2[]::new);
    }

    /**
     * Comprova que totes les noves paraules formades amb les peces noves existeixin al diccionari.
     *
     * @param pieces Les peces que s’han col·locat.
     * @param positions Les posicions on s’han col·locat les peces.
     */
    private void assertNewWordsExist(Piece[] pieces, Vector2[] positions) {
        String[] newWords = presentPiecesWordCompleter.run(positions, pieces);
        for (String word : newWords)
            assertWordExists(word);
    }

    /**
     * Comprova si una paraula existeix al diccionari, i si no, llança una excepció.
     *
     * @param word La paraula a validar.
     */
    private void assertWordExists(String word) {
        if (!wordValidator.run(word))
            throw new WordDoesNotExistException("The word \"" + word + "\" does not exist");
    }

    /**
     * Comprova que el moviment estigui dins dels límits del taulell.
     * Si està fora, llança una excepció.
     *
     * @param movement El moviment a validar.
     */
    private void assertInsideOfBounds(Movement movement) {
        if (!movementBoundsChecker.run(movement))
            throw new MovementOutsideOfBoardException("The movement \"" + movement + "\" is outside of the board bounds.");
    }
}
