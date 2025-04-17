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
import edu.upc.prop.scrabble.domain.board.WordGetter;
import edu.upc.prop.scrabble.domain.board.WordPlacer;
import edu.upc.prop.scrabble.domain.dawg.WordValidator;
import edu.upc.prop.scrabble.domain.pieces.PiecesConverter;
import edu.upc.prop.scrabble.domain.pieces.PiecesInHandVerifier;
import edu.upc.prop.scrabble.utils.Pair;
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.Map;

public class CatalanAI extends AI {
    public CatalanAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    @Override
    protected void processLeftPartSpecialPieces(String partialWord, Node node, int limit, Map.Entry<Character, Node> entry) {
        if(entry.getKey() == 'N') { // NY
            Node nextNode = entry.getValue().getSuccessor('Y');
            Piece usedPiece = bot.hasPiece("NY");
            if(nextNode != null && usedPiece != null) {
                bot.removePiece(usedPiece);
                LeftPart(partialWord + "NY", nextNode, limit - 1);
                bot.addPiece(usedPiece);
            }
        }
        else if(entry.getKey() == 'L') { // L·L
            Node nextNode = entry.getValue().getSuccessor('·');
            Piece usedPiece = bot.hasPiece("L·L");
            if(nextNode != null && usedPiece != null) {
                bot.removePiece(usedPiece);
                LeftPart(partialWord + "L·L", nextNode.getSuccessor('L'), limit - 1);
                bot.addPiece(usedPiece);
            }
        }
    }

    @Override
    protected void processNextLeftPiece(char lastLetter, String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
                (lastLetter != 'L' || entry.getKey() != '·')) {
            goToNextLeftPiece(partialWord, limit - 1, entry, usedPiece);
        }
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Map.Entry<Character, Node> entry, char lastLetter, Piece usedPiece, Vector2 nextCell) {
        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
                (lastLetter != 'L' || entry.getKey() != '·')) {
            goToNextRightPiece(partialWord, entry, usedPiece, nextCell);
        }
    }

    @Override
    protected void extendToNextExistingPieceRight(String partialWord, char lastLetter, String placedLetter, Node nextNode, Vector2 nextCell) {
        if ((lastLetter != 'N' || !placedLetter.equals("Y")) && (lastLetter != 'L' || !placedLetter.equals("·"))) {
            ExtendRight(partialWord + placedLetter, nextNode, nextCell);
        }
    }

    @Override
    protected void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry, Vector2 nextCell) {
        if(entry.getKey() == 'N') { // NY
            Node nextNode = entry.getValue().getSuccessor('Y');
            Piece usedPiece = bot.hasPiece("NY");
            if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "NY")) {
                if(nextNode.isEndOfWord()) {
                    partialWord = partialWord + "NY";
                    Piece[] pieceArray = piecesConverter.run(partialWord);
                    Vector2[] posVector = new Vector2[pieceArray.length];
                    for (int i = 0; i < pieceArray.length; ++i) {
                        posVector[i] = new Vector2(cell.x - pieceArray.length + 1 + i, cell.y);
                    }
                    int points = pointCalculator.run(posVector, pieceArray);
                    if (points > bestScore) {
                        bot.removePiece(usedPiece);
                        bestScore = points;
                        bestMove = new Movement(partialWord, posVector[0].x, posVector[0].y, getWordDirection(posVector));
                        bot.addPiece(usedPiece);
                    }
                }
                bot.removePiece(usedPiece);
                ExtendRight(partialWord + "NY", nextNode, nextCell);
                bot.addPiece(usedPiece);
            }
        }
        else if(entry.getKey() == 'L') { // L·L
            Node nextNode = entry.getValue().getSuccessor('·');
            if(nextNode != null) nextNode = nextNode.getSuccessor('L');
            Piece usedPiece = bot.hasPiece("L·L");
            if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "L·L")) {
                if(nextNode.isEndOfWord()) {
                    partialWord = partialWord + "L·L";
                    Piece[] pieceArray = piecesConverter.run(partialWord);
                    Vector2[] posVector = new Vector2[pieceArray.length];
                    for (int i = 0; i < pieceArray.length; ++i) {
                        posVector[i] = new Vector2(cell.x - pieceArray.length + 1 + i, cell.y);
                    }
                    int points = pointCalculator.run(posVector, pieceArray);
                    if (points > bestScore) {
                        bot.removePiece(usedPiece);
                        bestScore = points;
                        bestMove = new Movement(partialWord, posVector[0].x, posVector[0].y, getWordDirection(posVector));
                        bot.addPiece(usedPiece);
                    }
                }
                bot.removePiece(usedPiece);
                ExtendRight(partialWord + "L·L", nextNode, nextCell);
                bot.addPiece(usedPiece);
            }
        }
    }
}