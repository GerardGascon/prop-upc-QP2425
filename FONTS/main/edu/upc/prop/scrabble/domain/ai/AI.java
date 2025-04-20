package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
import edu.upc.prop.scrabble.data.Movement;
import edu.upc.prop.scrabble.data.Player;
import edu.upc.prop.scrabble.data.board.Board;
import edu.upc.prop.scrabble.data.crosschecks.CrossChecks;
import edu.upc.prop.scrabble.data.dawg.DAWG;
import edu.upc.prop.scrabble.data.dawg.Node;
import edu.upc.prop.scrabble.data.pieces.Piece;
import edu.upc.prop.scrabble.domain.AnchorUpdater;
import edu.upc.prop.scrabble.domain.board.PointCalculator;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.utils.Direction;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.*;

/**
 * AI class used to compute the move to make
 * @author Albert Usero & Felipe Martínez Lassalle
 */
public abstract class AI {
    private final DAWG dawg;
    protected Player bot;//para la mano
    private Board board;
    private Anchors anchors;
    protected CrossChecks crossChecks;
    private Vector2 currentAnchor;
    protected final PointCalculator pointCalculator;
    protected final PiecesConverter piecesConverter;
    protected Movement bestMove;
    protected int bestScore;
    protected Direction currentDirection;

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
        currentDirection = Direction.Horizontal;

        // HORIZONTAL CHECKS
        innerRun();

        // VERTICAL CHECKS
        // Rotation of needed components
        /*currentDirection = Direction.Vertical;
        Board storedBoard = board;
        board = board.rotate();
        Anchors storedAnchors = anchors;
        anchors = anchors.rotate(board);
        CrossChecks storedCrossChecks = crossChecks;
        crossChecks = crossChecks.rotate();
        innerRun();
        // Restore rotations
        board = storedBoard;
        anchors = storedAnchors;
        crossChecks = storedCrossChecks;*/

        return bestMove;
    }

    private void innerRun() {

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
                Node node = getFinalNode(partialWord);
                if(node != null) ExtendRight(partialWord, node, currentAnchor); // Straight to the right part
            }
            else LeftPart("", dawg.getRoot(), limit); // Bot's pieces case
        }
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
        //System.out.println(partialWord+"\n");
        //System.out.println(currentAnchor.x + " " +currentAnchor.y);
        // Straight to right part check
        ExtendRight(partialWord, node, currentAnchor);

        if (limit > 0) { // Available space (no need to recheck if valid)
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
     * @param partialWord current word fragment.
     * @param limit how far can we go.
     * @param usedPiece piece used in current iteration.
     * @see DAWG
     * @see Node
     */
    protected void goToNextLeftPiece(String partialWord, Node node, int limit, Piece usedPiece) {
        bot.removePiece(usedPiece);
        LeftPart(partialWord, node, limit - 1);
        bot.addPiece(usedPiece);
    }

    protected void ExtendRight(String partialWord, Node node, Vector2 cell) {

        // Valid cell
        if (board.isCellValid(cell.x, cell.y)) {
            // No placed piece in current cell
            if (board.isCellEmpty(cell.x, cell.y)) {
                // Already valid word and no adjacent piece
                if (node.isEndOfWord()) checkWord(partialWord, new Vector2(cell.x - 1, cell.y));
                // Get all possible successors and iterate over them
                Map<Character, Node> nextNodes = node.getSuccessors();
                for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {
                    // Special cases check (need to check even if not in possession of current char)
                    processRightPartSpecialPieces(partialWord, node, cell, entry);
                    // In possession of matching and usable piece
                    char c = entry.getKey(); // Current char
                    Piece usedPiece = bot.hasPiece(String.valueOf(c)); // Matching piece (null if non-existent)

                    if (usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, String.valueOf(c))) {
                        // Standard check
                        extendToNextNewPieceRight(partialWord, cell, entry, usedPiece);
                    }
                }
            }
            // Already placed piece
            else extendToNextExistingPieceRight(partialWord, node, new Vector2(cell.x + 1, cell.y), board.getCellPiece(cell.x, cell.y));
        }
        // Already valid word and end of board
        else if (node.isEndOfWord()) checkWord(partialWord, new Vector2(cell.x - 1, cell.y));
    }

    protected abstract void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry);

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
        while(i < word.length() && current != null) {
            current = current.getSuccessor(word.charAt(i));
            ++i;
        }
        return current;
    }

    protected void checkWord(String word, Vector2 cell) {
        // Create needed variables
        Piece[] pieceArray = piecesConverter.run(word);
        Vector2[] posVector = new Vector2[pieceArray.length];
        for (int i = 0; i < pieceArray.length; ++i) {
            posVector[i] = new Vector2(cell.x - pieceArray.length + 1 + i, cell.y);
        }
        // Update best move and score if needed
        int points = pointCalculator.run(posVector, pieceArray);
        if (points > bestScore) {
            bestScore = points;
            if(currentDirection == Direction.Vertical) { // Rotate if needed
                int newY = posVector[0].x;
                posVector[0].x = board.getSize() - posVector[0].y - 1;
                posVector[0].y = newY;
            }
            bestMove = new Movement(word, posVector[0].x, posVector[0].y, currentDirection);
        }
    }
}