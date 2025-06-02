package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.Map;

/**
 * Intel·ligència artificial que computa el moviment a realitzar
 * pel jugador controlat per la màquina
 * @see Player
 * @see Board
 * @see Anchors
 * @see CrossChecks
 * @see Movement
 * @see PointCalculator
 * @see PiecesConverter
 * @see DAWG
 * @author Albert Usero
 * @author Felipe Martínez
 */
public abstract class AI {
    /**
     * Estructura que guarda les paraules vàlides per a la llengua amb la qual es juga
     */
    private final DAWG dawg;
    /**
     * Jugador al qual la intel·ligència artificial computarà el moviment
     */
    protected final Player bot;
    /**
     * Board sobre el qual s'ha de buscar el moviment a realitzar
     */
    private Board board;
    /**
     * Anchors sobre els quals es pot iniciar un moviment
     */
    private Anchors anchors;
    /**
     * CrossChecks que senyalitzen les lletres vàlides a  determinades caselles
     */
    protected CrossChecks crossChecks;
    /**
     * Representació de l'anchor sobre el qual actualment es busca un moviment
     */
    private Vector2 currentAnchor;
    /**
     * Encarregada de calcular els punts donats pels moviments obtinguts
     */
    protected final PointCalculator pointCalculator;
    /**
     * Encarregada de transformar les lletres dels moviments obtinguts en peces
     */
    protected final PiecesConverter piecesConverter;
    /**
     * Millor moviment trobat
     */
    protected Movement bestMove;
    /**
     * Puntuació donada pel millor moviment trobat
     */
    protected int bestScore;
    /**
     * Direcció sobre la qual es busca el moviment (Horizontal/Vertical)
     */
    protected Direction currentDirection;
    /**
     * Numero de peces que posseeix el jugador controlat
     */
    private int initialPieces;

    /**
     * Crea una nova intel·ligència artificial amb els paràmetres especificats. Per tenir
     * un correcte funcionament cal que si els seus paràmetres poden adoptar diferents
     * idiomes tots estiguin al mateix.
     * @param piecesConverter Convertidor de peces
     * @param pointCalculator Calculador de punts
     * @param dawg DAWG que guarda els nodes que representen les possibles paraules
     * @param board Tauler sobre el qual es calcularan els moviments
     * @param bot Jugador al qual l'algorisme calcularà els moviments
     * @param anchors Estructura de dades que emmagatzema caselles des de les quals es pot efectuar un moviment
     * @param crossChecks Estructura de dades que senyalitza les lletres vàlides a determinades caselles
     * @see Player
     * @see Board
     * @see Anchors
     * @see CrossChecks
     * @see DAWG
     * @see PointCalculator
     * @see PiecesConverter
     */
    public AI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        this.dawg = dawg;
        this.bot = bot;
        this.board = board;
        this.anchors = anchors;
        this.crossChecks = crossChecks;
        this.pointCalculator = pointCalculator;
        this.piecesConverter = piecesConverter;
    }

    /**
     * Computa i retorna el millor possible moviment que resulti en la quantitat més gran de punts.
     * @return El moviment que efectua la quantitat més gran de punts. Null si no hi ha cap possible.
     */
    public Movement run() {
        resetGlobals();

        checkHorizontal();
        checkVertical();

        if (bestMove.direction() == Direction.Vertical) { // Rotate if needed
            int newY = bestMove.x();
            bestMove = new Movement(bestMove.word(), board.getSize() - bestMove.y() - 1, newY, Direction.Vertical);
        }

        if(bestScore == -1) return null;
        return bestMove;
    }
    /**
     * Actualitza la direcció actual a vertical i comprova possibles
     * moviments en vertical. Això s'efectua comprovant moviments en
     * horitzontal amb el tauler (i estructures de dades relacionades)
     * amb una rotació en 90 del sentit horari. (després ja es desfarà
     * la rotació si el millor moviment resulta ser en vertical)
     * @see Board
     * @see CrossChecks
     * @see Anchors
     */
    private void checkVertical() {
        Board originalBoard = board;
        CrossChecks originalCrossChecks = crossChecks;
        Anchors originalAnchors = anchors;

        board = board.rotate();
        anchors = anchors.rotate(board.getSize());
        crossChecks = crossChecks.rotate();

        currentDirection = Direction.Vertical;
        innerRun();

        board = originalBoard;
        anchors = originalAnchors;
        crossChecks = originalCrossChecks;
    }

    /**
     * Actualitza la direcció actual a horitzontal i comprova possibles
     * moviments en horitzontal amb el tauler en la posició original.
     */
    private void checkHorizontal() {
        currentDirection = Direction.Horizontal;
        innerRun();
    }

    /**
     * Restableix els valors globals als valors inicials.
     * @see Movement
     * @see Player
     */
    private void resetGlobals() {
        bestMove = new Movement("", 0, 0, Direction.Horizontal);
        bestScore = -1;
        initialPieces = bot.getHand().length;
    }

    /**
     * Comprova tots els moviments possibles vàlids amb les peces actuals del jugador
     * en direcció horitzontal. Anirà actualitzant les dades globals bestMove i
     * bestScore i el seu resultat quedarà emmagatzemat allà.
     * @see Anchors
     * @see Board
     * @see Node
     * @see DAWG
     */
    private void innerRun() {
        for (int i = 0; i < anchors.getSize(); ++i) {
            currentAnchor = anchors.getAnchor(i);
            int limit = 0;

            while (board.isCellValid(currentAnchor.x - limit - 1, currentAnchor.y) &&
                    board.isCellEmpty(currentAnchor.x - limit - 1, currentAnchor.y) &&
                    !anchors.exists(currentAnchor.x - limit - 1, currentAnchor.y)) ++limit;

            // LeftPart is composed by bot's pieces or already placed letters, never both
            // we can track in which case we are thanks to the limit variable
            if (limit == 0) {
                // Already placed letters case
                StringBuilder partialWord = new StringBuilder();
                int j = 1;
                while (board.isCellValid(currentAnchor.x - j, currentAnchor.y) &&
                        !board.isCellEmpty(currentAnchor.x - j, currentAnchor.y)) { // Check existing string
                    partialWord.insert(0, board.getCellPiece(currentAnchor.x - j, currentAnchor.y).letter());
                    ++j;
                }
                Node node = getFinalNode(partialWord.toString());
                if (node != null) ExtendRight(partialWord.toString(), node, currentAnchor); // Straight to the right part
            } else LeftPart("", dawg.getRoot(), limit); // Bot's pieces case
        }
    }

    /**
     * Funció de backtracking que itera sobre cada part esquerra ("prefixos") de les possibles
     * paraules a formar a les condicions actuals (peces a la mà, peces en joc etc.)
     *
     * @param partialWord Tros de la paraula actual
     * @param node Node del DAWG al qual s'arriba travessant-lo amb el tros de paraula actual
     * @param limit Com de lluny podem anar
     * @see DAWG
     * @see Node
     * @see Piece
     */
    protected void LeftPart(String partialWord, Node node, int limit) {
        ExtendRight(partialWord, node, currentAnchor);

        if (limit > 0) {
            // Available space (no need to recheck if valid)
            Map<Character, Node> nextNodes = node.getSuccessors();
            for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {
                processLeftPartSpecialPieces(partialWord, limit, entry);
                Piece usedPiece = bot.hasPiece(String.valueOf(entry.getKey()));
                if (usedPiece != null)
                    processNextLeftPiece(partialWord, limit, entry, usedPiece);
            }
        }
    }

    /**
     * Tracta casos especials cap a l'esquerra (potser no fa res si no existeixen al llenguatge)
     * @param partialWord Tros de la paraula actual
     * @param limit Com de lluny podem anar
     * @param entry Caràcter/node següent que estem comprovant
     * @see DAWG
     * @see Node
     */
    protected abstract void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry);

    /**
     * Comprova que no hi ha cap combinació il·legal (potser no fa res si no existeixen al llenguatge)
     * @param partialWord Tros de la paraula actual
     * @param limit Com de lluny podem anar
     * @param entry Caràcter/node següent que estem comprovant
     * @param usedPiece Peça utilitzada a la iteració actual
     * @see DAWG
     * @see Node
     */
    protected abstract void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece);

    /**
     * Gestiona el procés de backtracking avançant a la següent peça a l'esquerra.
     *
     * @param partialWord Tros de la paraula construïda fins ara.
     * @param node Node actual dins de l'estructura DAWG.
     * @param limit Límit màxim de posicions que es poden explorar cap enrere.
     * @param usedPiece Peça utilitzada en la iteració actual que s'elimina temporalment per provar altres opcions.
     * @see DAWG
     * @see Node
     * @see Piece
     */
    protected void goToNextLeftPiece(String partialWord, Node node, int limit, Piece usedPiece) {
        bot.removePiece(usedPiece);
        LeftPart(partialWord, node, limit - 1);
        bot.addPiece(usedPiece);
    }

    /**
     * Crida a estendre cap a la dreta si és possible. Té en compte possibles
     * els possibles casos els quals ens podem trobar en realitzar la crida.
     * Comprova el tros de paraula que portem per veure si és vàlid.
     * @param partialWord Tros de paraula actual
     * @param node Node que representa el caràcter final del tros de paraula actual
     * @param cell Casella actual sobre la qual estem estenent
     */
    protected void ExtendRight(String partialWord, Node node, Vector2 cell) {
        if (board.isCellValid(cell.x, cell.y)) {
            if (board.isCellEmpty(cell.x, cell.y)) {
                if (node.isEndOfWord())
                    checkWord(partialWord, new Vector2(cell.x - 1, cell.y));
                Map<Character, Node> nextNodes = node.getSuccessors();
                for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {
                    processRightPartSpecialPieces(partialWord, cell, entry);
                    char c = entry.getKey();
                    Piece usedPiece = bot.hasPiece(String.valueOf(c));

                    if (usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, String.valueOf(c))) {
                        if ((board.isCellValid(cell.x, cell.y + 1) && !board.isCellEmpty(cell.x, cell.y + 1)) ||
                            (board.isCellValid(cell.x, cell.y - 1) && !board.isCellEmpty(cell.x, cell.y - 1))) {
                            if (validExistingWord(cell, board, c)) {
                                extendToNextNewPieceRight(partialWord, cell, entry, usedPiece);
                            }
                        } else {
                            extendToNextNewPieceRight(partialWord, cell, entry, usedPiece);
                        }
                    }
                }
            } else {
                extendToNextExistingPieceRight(partialWord, node, new Vector2(cell.x + 1, cell.y), board.getCellPiece(cell.x, cell.y));
            }
        } else if (node.isEndOfWord()) {
            checkWord(partialWord, new Vector2(cell.x - 1, cell.y));
        }
    }

    /**
     * Tracta casos especials cap a la dreta (potser no fa res si no existeixen al llenguatge)
     * @param partialWord Tros de la paraula actual
     * @param cell Posició a la qual ens trobem en expansió
     * @param entry Caràcter/node següent que estem comprovant
     * @see DAWG
     * @see Node
     */
    protected abstract void processRightPartSpecialPieces(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry);

    /**
     * Estén cap a la dreta fent servir una peça de la mà i comprova casos especials
     * @param partialWord Tros de paraula actual
     * @param cell Casella sobre la qual estem estenent
     * @param entry Caràcter/Node següent que estem comprovant
     * @param usedPiece Peça utilitzada
     */
    protected abstract void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece);

    /**
     * Estén cap a la dreta amb una peça ja col·locada al tauler i comprova casos especials
     * @param partialWord Tros de paraula actual
     * @param node Node que referencia a la peça ja col·locada
     * @param cell Casella sobre la qual estem estenent
     * @param placedPiece Peça ja al tauler
     */
    protected abstract void extendToNextExistingPieceRight(String partialWord, Node node, Vector2 cell, Piece placedPiece);

    /**
     * Gestor de backtracking
     * @param partialWord Tros de paraula actual
     * @param nextNode Caràcter/node següent que estem comprovant
     * @param cell Casella de la següent iteració
     * @param usedPiece Peça utilitzada a la iteració actual
     * @see DAWG
     * @see Node
     * @see Piece
     */
    protected void goToNextRightPiece(String partialWord, Node nextNode, Vector2 cell, Piece usedPiece) {
        bot.removePiece(usedPiece);
        ExtendRight(partialWord, nextNode, new Vector2(cell.x + 1, cell.y));
        bot.addPiece(usedPiece);
    }

    /**
     * Retorna el node final que representa certa paraula
     * @param word Paraula sobre la qual volem obtenir el node final
     * @return Node que representa el caràcter final de la paraula donada.
     * @see Node
     */
    protected Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        int i = 0;
        while (i < word.length() && current != null) {
            current = current.getSuccessor(word.charAt(i));
            ++i;
        }
        return current;
    }

    /**
     * Validem si quan estenem la paraula actual no fem invàlides
     * les paraules presents al board
     * @param cell Casella on comença la paraula
     * @param board Tauler actual
     * @param c Caràcter a afegir
     * @return Cert si la paraula és vàlida
     */
    protected abstract boolean validExistingWord(Vector2 cell, Board board, char c);

    /**
     * Comprovem si hem fet servir com a mínim una peça de la mà i si passem
     * per algun anchor abans de confirmar que hem trobat una possible jugada.
     * Tractem d'actualitzar la millor jugada i puntuació en cas que sigui correcte.
     * @param word Paraula que estem comprovant
     * @param cell Casella d'inici del moviment (considerem direcció horitzontal)
     */
    protected void checkWord(String word, Vector2 cell) {
        if (bot.getHand().length == initialPieces)
            return;
        Piece[] pieceArray = piecesConverter.run(word);
        Vector2[] posVector = new Vector2[pieceArray.length];
        boolean anchorTraversed = false;
        for (int i = 0; i < pieceArray.length; ++i) {
            posVector[i] = new Vector2(cell.x - pieceArray.length + 1 + i, cell.y);
            if(anchors.exists(posVector[i].x, posVector[i].y))
                anchorTraversed = true;
        }

        if(anchorTraversed) recalculateMaxScoringWord(word, posVector, pieceArray);
    }

    /**
     * Calcula la puntuació del nou moviment i en cas que sigui millor que l'actual
     * actualitza les variables globals BestMove i BestScore.
     * @param word Nova paraula trobada
     * @param posVector Posició ón s'efectua el nou moviment
     * @param pieceArray Peces que fem servir per realitzar el nou moviment
     */
    private void recalculateMaxScoringWord(String word, Vector2[] posVector, Piece[] pieceArray) {
        int points = pointCalculator.run(posVector, pieceArray);
        if (points > bestScore) {
            bestScore = points;
            bestMove = new Movement(word, posVector[0].x, posVector[0].y, currentDirection);
        }
    }
}