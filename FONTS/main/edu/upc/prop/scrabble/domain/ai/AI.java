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
import edu.upc.prop.scrabble.domain.pieces.PieceDrawer;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.*;

/**
 * AI class used to compute the move to make
 * @author Albert Usero & Felipe Martínez Lassalle
 */
public abstract class AI {
    private final DAWG dawg;
    protected Player bot;//para la mano
    private final Board board;
    private final Anchors anchors;
    protected CrossChecks crossChecks;
    private Vector2 currentAnchor;
    protected final PointCalculator pointCalculator;
    protected final PiecesConverter piecesConverter;
    protected Movement bestMove;
    protected int bestScore;

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
     * @return the move that yields the highest number of points. Null if no move possible.
     */
    public Movement run() {

        // Reset global variables
        bestMove = new Movement("", 0, 0, Direction.Horizontal);
        bestScore = -1;

        // HORIZONTAL CHECKS
        // Iterate over every available anchor
        for (int i = 0; i < anchors.getSize(); ++i) {
            // Set current anchor
            currentAnchor = anchors.getAnchor(i); // Global variable
            int currentAnchorX = currentAnchor.x; // Variables to make code cleaner
            int currentAnchorY = currentAnchor.y;
            int limit = 0; // How far to the left can we go
            while (board.isCellValid(currentAnchorX - limit - 1, currentAnchorY) &&
                    board.isCellEmpty(currentAnchorX - limit - 1, currentAnchorY) &&
                    !anchors.exists(currentAnchorX - limit - 1, currentAnchorY)) ++limit;

            // LeftPart is composed by bot's pieces or already placed letters, never both
            // we can track in which case we are thanks to the limit variable
            if (limit == 0) { // Already placed letters case
                String partialWord = "";
                int j = 1;
                while (board.isCellValid(currentAnchorX - j, currentAnchorY) &&
                        !board.isCellEmpty(currentAnchorX - j, currentAnchorY)){ // Check existing string
                    partialWord = board.getCellPiece(currentAnchorX - j , currentAnchorY).letter() + partialWord;
                    ++j;
                }
                ExtendRight(partialWord, getFinalNode(partialWord), currentAnchor); // Straight to the right part
            }
            else LeftPart("", dawg.getRoot(), limit); // Bot's pieces case
        }
        return bestMove;
    }

    /**
     * Backtracking function that iterates over every left part of possible words.
     * @param partialWord current word fragment.
     * @param node dawg node equivalent to the partialWord.
     * @param limit how far can we go.
     * @see DAWG
     * @see Node
     */
    protected void LeftPart(String partialWord, Node node, int limit) {
        // Straight to right part check
        ExtendRight(partialWord, node, currentAnchor);

        if (limit > 0) { // Available space
            // Get all possible successors and iterate over them
            Map<Character, Node> nextNodes = node.getSuccessors();
            for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {
                // Special cases check (need to enter even if not in possession of current char)
                processLeftPartSpecialPieces(partialWord, limit, entry);
                // Standard check (if in possession of usable piece)
                Piece usedPiece = bot.hasPiece(String.valueOf(entry.getKey()));
                if (usedPiece != null) processNextLeftPiece(partialWord, limit, entry, usedPiece);
            }
        }
    }

    /**
     * Processes special cases (may do nothing if language doesn't have any)
     * @param partialWord current word fragment.
     * @param limit how far can we go.
     * @param entry current successor entry.
     * @see DAWG
     * @see Node
     */
    protected abstract void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry);

    /**
     * Checks no illegal combination is done (may do nothing if language doesn't have any)
     * @param partialWord current word fragment.
     * @param limit how far can we go.
     * @param entry current successor entry.
     * @param usedPiece piece used in current iteration.
     * @see DAWG
     * @see Node
     */
    protected abstract void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece);

    /**
     * Backtracking handler
     * @param partialWord current word fragment.
     * @param limit how far can we go.
     * @param entry current successor entry.
     * @param usedPiece piece used in current iteration.
     * @see DAWG
     * @see Node
     */
    protected void goToNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        bot.removePiece(usedPiece);
        LeftPart(partialWord + entry.getKey(), entry.getValue(), limit - 1);
        bot.addPiece(usedPiece);
    }

    protected void ExtendRight(String partialWord, Node node, Vector2 cell) {

        // Prepare next cell
        Vector2 nextCell = new Vector2(cell.x + 1, cell.y);

        // No placed piece in current cell and available node
        if (board.isCellValid(cell.x, cell.y) && board.isCellEmpty(cell.x, cell.y) && node != null) {
            // Get all possible successors and iterate over them
            Map<Character, Node> nextNodes = node.getSuccessors();
            for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {

                // Special cases check (need to check even if not in possession of current char)
                processRightPartSpecialPieces(partialWord, node, cell, entry, nextCell);

                // Get current char and matching piece (null if non-existent)
                char c = entry.getKey();
                Piece usedPiece = bot.hasPiece(String.valueOf(c));
                // In possession of matching and usable piece
                if (usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, String.valueOf(c))) {

                    // Word already valid
                    if (entry.getValue().isEndOfWord()) {
                        partialWord = partialWord + entry.getKey(); // update partial word
                        // Create needed variables
                        Piece[] pieceArray = piecesConverter.run(partialWord);
                        Vector2[] posVector = new Vector2[pieceArray.length];
                        for (int i = 0; i < pieceArray.length; ++i) {
                            posVector[i] = new Vector2(cell.x - pieceArray.length + 1 + i, cell.y);
                        }
                        // Update best move and score if needed
                        int points = pointCalculator.run(posVector, pieceArray);
                        if (points > bestScore) {
                            bestScore = points;
                            bestMove = new Movement(partialWord, posVector[0].x, posVector[0].y, getWordDirection(posVector));
                        }
                    }
                    char lastLetter = ' ';
                    if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
                    extendToNextNewPieceRight(partialWord, entry, lastLetter, usedPiece, nextCell);
                }
            }
        } else if (board.isCellValid(cell.x, cell.y ) && !board.isCellEmpty(cell.x, cell.y)) { // NO PONER COMBINATIONS ILEGALES!!!!! NY y tal
            Piece placedPiece = board.getCellPiece(cell.x, cell.y);
            char lastLetter = ' ';
            if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
            String placedLetter = placedPiece.letter();
            Node nextNode = node.getSuccessor(placedPiece.letter().charAt(0));
            if (placedLetter.length() == 1 && nextNode != null)
                extendToNextExistingPieceRight(partialWord, lastLetter, placedLetter, nextNode, nextCell);
            else {
                for (int i = 1; i < partialWord.length() && nextNode != null; i++)
                    nextNode = node.getSuccessor(placedPiece.letter().charAt(i));
                if (nextNode != null)
                    ExtendRight(partialWord + placedLetter, nextNode, nextCell);
            }
        }
    }

    protected abstract void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry, Vector2 nextCell);

    protected abstract void extendToNextNewPieceRight(String partialWord, Map.Entry<Character, Node> entry, char lastLetter, Piece usedPiece, Vector2 nextCell);

    protected abstract void extendToNextExistingPieceRight(String partialWord, char lastLetter, String placedLetter, Node nextNode, Vector2 nextCell);

    /**
     * Backtracking handler
     * @param partialWord current word fragment.

     * @param entry current successor entry.
     * @param usedPiece piece used in current iteration.
     * @see DAWG
     * @see Node
     */
    protected void goToNextRightPiece(String partialWord, Map.Entry<Character, Node> entry, Piece usedPiece, Vector2 nextCell) {
        bot.removePiece(usedPiece);
        ExtendRight(partialWord + entry.getKey(), entry.getValue(), nextCell);
        bot.addPiece(usedPiece);
    }

    private Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        for (int i = 0; i < word.length(); i++) {
            current = current.getSuccessor(word.charAt(i));
        }

        return current;
    }

    protected Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }
}