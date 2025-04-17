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

public abstract class AI {
    private final DAWG dawg;
    protected Player bot;//para la mano
    private final Board board;
    private final Anchors anchors;
    protected CrossChecks crossChecks;
    private Vector2 currentAnchor;
    private final PointCalculator pointCalculator;
    private final PiecesConverter piecesConverter;
    private Movement bestMove;
    private int bestScore;

    //los world placers y demás simlares hacer que se declaren en action maker y usarlos
    //tanto para persona como bot
    public AI(PiecesConverter piecesConverter, PointCalculator pointCalculator, String language, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        this.dawg = dawg;
        this.bot = bot;
        this.board = board;
        this.anchors = anchors;
        this.crossChecks = crossChecks;
        this.pointCalculator = pointCalculator;
        this.piecesConverter = piecesConverter;
    }

    //devuelve un vector con para cada pieza la posicionen la que va
    public Movement run() {
        // Move initialization
        bestMove = new Movement("", 0, 0, Direction.Horizontal);
        bestScore = -1;
        // Iterate over every available anchor
        for (int i = 0; i < anchors.getSize(); ++i) {
            currentAnchor = anchors.getAnchor(i); // Global variable
            int currentAnchorX = currentAnchor.x;
            int currentAnchorY = currentAnchor.y;
            //MOVIMIENTOS HORIZONTALES---------------------------
            int limit = 0; // How far to the left can we go
            while (board.isCellValid(currentAnchorX - limit - 1, currentAnchorY) &&
                    board.isCellEmpty(currentAnchorX - limit - 1, currentAnchorY) &&
                    !anchors.exists(currentAnchorX - limit - 1, currentAnchorY)) ++limit;

            // LeftPart is composed by bot's pieces or already placed letters, never both
            if (limit == 0) { // Already placed letters
                String partialWord = "";
                int j = 0;
                while (board.isCellValid(currentAnchorX - j - 1, currentAnchorY)) {
                    partialWord = board.getCellPiece(currentAnchorX - i - 1, currentAnchorY).letter() + partialWord;
                    ++j;
                }
                ExtendRight(partialWord, getFinalNode(partialWord), currentAnchor);
            } else {
                LeftPart("", dawg.getRoot(), limit);
            }
        }

        //tmb que best score sea -1 significa que no ha encontrado nada
        return bestMove; //si es null hacer luego que se cambien piezas o pase turno
    }

    protected void LeftPart(String partialWord, Node node, int limit) {
        ExtendRight(partialWord, node, currentAnchor);
        if (limit > 0) {
            Map<Character, Node> nextNodes = node.getSuccessors();
            for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {
                // Special cases check
                processLeftPartSpecialPieces(partialWord, node, limit, entry);
                Piece usedPiece = bot.hasPiece(String.valueOf(entry.getKey()));
                if (usedPiece != null) {
                    char lastLetter = partialWord.charAt(partialWord.length() - 1);
                    processNextPiece(lastLetter, partialWord, limit, entry, usedPiece);
                }
            }
        }
    }

    protected abstract void processLeftPartSpecialPieces(String partialWord, Node node, int limit, Map.Entry<Character, Node> entry);

    protected abstract void processNextPiece(char lastLetter, String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece);

    protected void goToNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        bot.removePiece(usedPiece);
        LeftPart(partialWord + entry.getKey(), entry.getValue(), limit);
        bot.addPiece(usedPiece);
    }

    protected void ExtendRight(String partialWord, Node node, Vector2 cell) {
        // FALTA CHECAR SI ES UNA PALABRA VALIDA CALCULAR PUNTOS MEJOR MOVIMIENTO Y TAL Y CUAL
        Vector2 nextCell = new Vector2(cell.x + 1, cell.y);
        if (board.isCellValid(cell.x, cell.y) && board.isCellEmpty(cell.x, cell.y)) {
            Map<Character, Node> nextNodes = node.getSuccessors();
            for (Map.Entry<Character, Node> entry : nextNodes.entrySet()) {
                if (entry.getValue().isEndOfWord()) {
                    Piece[] pieceArray = piecesConverter.run(partialWord);
                    Vector2[] posVector = new Vector2[pieceArray.length];
                    for (int i = 0; i < pieceArray.length; ++i) {
                        posVector[i] = new Vector2(cell.x - i, cell.y - i);
                    }
                    int points = pointCalculator.run(posVector, pieceArray);
                    if (points > bestScore) {
                        bestScore = points;
                        bestMove = new Movement(partialWord, posVector[0].x, posVector[0].y, getWordDirection(posVector));
                    }
                }

                // Special cases check
                processRightPartSpecialPieces(partialWord, node, cell, entry, nextCell);

                Piece usedPiece = bot.hasPiece(String.valueOf(entry.getKey()));
                if (usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, String.valueOf(entry.getKey()))) {
                    char lastLetter = partialWord.charAt(partialWord.length() - 1);
                    extendToNextNewPieceRight(partialWord, entry, lastLetter, usedPiece, nextCell);
                }
            }
        } else if (board.isCellValid(cell.x, cell.y)) { // NO PONER CONVINATIONS ILEGALES!!!!! NY y tal
            Piece placedPiece = board.getCellPiece(cell.x, cell.y);
            char lastLetter = partialWord.charAt(partialWord.length() - 1);
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

    protected abstract void extendToNextNewPieceRight(String partialWord, Map.Entry<Character, Node> entry, char lastLetter, Piece usedPiece, Vector2 nextCell);

    protected abstract void extendToNextExistingPieceRight(String partialWord, char lastLetter, String placedLetter, Node nextNode, Vector2 nextCell);

    protected void goToNextRightPiece(String partialWord, Map.Entry<Character, Node> entry, Piece usedPiece, Vector2 nextCell) {
        bot.removePiece(usedPiece);
        ExtendRight(partialWord + entry.getKey(), entry.getValue(), nextCell);
        bot.addPiece(usedPiece);
    }

    protected abstract void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry, Vector2 nextCell);

    private Node getFinalNode(String word) {
        Node current = dawg.getRoot();
        for (int i = 0; i < word.length(); i++) {
            current = current.getSuccessor(word.charAt(i));
        }

        return current;
    }

    private Direction getWordDirection(Vector2[] positions) {
        if (positions.length == 1)
            return null;

        if (positions[0].x == positions[1].x)
            return Direction.Vertical;
        return Direction.Horizontal;
    }
}