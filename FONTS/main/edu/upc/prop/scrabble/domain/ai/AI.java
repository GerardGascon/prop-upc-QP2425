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

import java.util.*;

/**
 * AI class used to compute the move to make
 *
 * @author Albert Usero
 * @author Felipe Martínez Lassalle
 */
public abstract class AI {
    private final DAWG dawg;
    protected final Player bot;//para la mano
    private Board board;
    private Anchors anchors;
    protected CrossChecks crossChecks;
    private Vector2 currentAnchor;
    protected final PointCalculator pointCalculator;
    protected final PiecesConverter piecesConverter;
    protected Movement bestMove;
    protected int bestScore;
    protected Direction currentDirection;
    private int initialPieces;

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
     * Computes and returns the best possible move based on the highest point value.
     *
     * @return the move that yields the highest number of points. Null if no move possible.
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

    private void checkHorizontal() {
        currentDirection = Direction.Horizontal;
        innerRun();
    }

    private void resetGlobals() {
        bestMove = new Movement("", 0, 0, Direction.Horizontal);
        bestScore = -1;
        initialPieces = bot.getHand().length;
    }

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
     * Backtracking function that iterates over every left part of possible words.
     *
     * @param partialWord current word fragment.
     * @param node        dawg node equivalent to the partialWord.
     * @param limit       how far can we go.
     * @see DAWG
     * @see Node
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
     * Processes special cases (may do nothing if language doesn't have any)
     *
     * @param partialWord current word fragment.
     * @param limit       how far can we go.
     * @param entry       current successor entry.
     * @see DAWG
     * @see Node
     */
    protected abstract void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry);

    /**
     * Checks no illegal combination is done (may do nothing if language doesn't have any)
     *
     * @param partialWord current word fragment.
     * @param limit       how far can we go.
     * @param entry       current successor entry.
     * @param usedPiece   piece used in current iteration.
     * @see DAWG
     * @see Node
     */
    protected abstract void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece);

    /**
     * Backtracking handler
     *
     * @param partialWord current word fragment.
     * @param limit       how far can we go.
     * @param usedPiece   piece used in current iteration.
     * @see DAWG
     * @see Node
     */
    protected void goToNextLeftPiece(String partialWord, Node node, int limit, Piece usedPiece) {
        bot.removePiece(usedPiece);
        LeftPart(partialWord, node, limit - 1);
        bot.addPiece(usedPiece);
    }

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

                    if (usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, String.valueOf(c)))
                        if((board.isCellValid(cell.x, cell.y + 1) && !board.isCellEmpty(cell.x, cell.y + 1)) ||
                           (board.isCellValid(cell.x, cell.y - 1) && !board.isCellEmpty(cell.x, cell.y - 1))) {
                            String adjWord = String.valueOf(c);
                            int i = 1;
                            while(board.isCellValid(cell.x, cell.y - i) && !board.isCellEmpty(cell.x, cell.y - i)) {
                                adjWord = board.getCellPiece(cell.x, cell.y - i) + adjWord;
                                ++i;
                            }
                            i = 0;
                            while(board.isCellValid(cell.x, cell.y + i) && !board.isCellEmpty(cell.x, cell.y - i)) {
                                adjWord = adjWord + board.getCellPiece(cell.x, cell.y + i);
                                ++i;
                            }
                            Node aux = getFinalNode(adjWord);
                            if(aux != null && aux.isEndOfWord()) extendToNextNewPieceRight(partialWord, cell, entry, usedPiece);
                        }
                        else extendToNextNewPieceRight(partialWord, cell, entry, usedPiece);
                }
            } else {
                extendToNextExistingPieceRight(partialWord, node, new Vector2(cell.x + 1, cell.y), board.getCellPiece(cell.x, cell.y));
            }
        } else if (node.isEndOfWord()) {
            checkWord(partialWord, new Vector2(cell.x - 1, cell.y));
        }
    }

    protected abstract void processRightPartSpecialPieces(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry);

    protected abstract void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece);

    protected abstract void extendToNextExistingPieceRight(String partialWord, Node node, Vector2 cell, Piece placedPiece);

    /**
     * Backtracking handler
     *
     * @param partialWord current word fragment.
     * @param nextNode    current successor entry.
     * @param cell        next iteration cell.
     * @param usedPiece   piece used in current iteration.
     * @see DAWG
     * @see Node
     */
    protected void goToNextRightPiece(String partialWord, Node nextNode, Vector2 cell, Piece usedPiece) {
        bot.removePiece(usedPiece);
        ExtendRight(partialWord, nextNode, new Vector2(cell.x + 1, cell.y));
        bot.addPiece(usedPiece);
    }

    private Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        int i = 0;
        while (i < word.length() && current != null) {
            current = current.getSuccessor(word.charAt(i));
            ++i;
        }
        return current;
    }

    protected void checkWord(String word, Vector2 cell) {
        if (bot.getHand().length == initialPieces)
            return;
        Piece[] pieceArray = piecesConverter.run(word);
        Vector2[] posVector = new Vector2[pieceArray.length];
        boolean anchorTraversed = false;
        for (int i = 0; i < pieceArray.length; ++i) {
            posVector[i] = new Vector2(cell.x - pieceArray.length + 1 + i, cell.y);
            if(posVector[i] == currentAnchor) anchorTraversed = true;
        }

        if(anchorTraversed) recalculateMaxScoringWord(word, posVector, pieceArray);
    }

    private void recalculateMaxScoringWord(String word, Vector2[] posVector, Piece[] pieceArray) {
        int points = pointCalculator.run(posVector, pieceArray);
        if (points > bestScore) {
            bestScore = points;
            bestMove = new Movement(word, posVector[0].x, posVector[0].y, currentDirection);
        }
    }
}