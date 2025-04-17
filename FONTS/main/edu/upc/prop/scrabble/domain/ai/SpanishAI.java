package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
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

public class SpanishAI extends AI {
    public SpanishAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, String language, DAWG dawg, Board board, Player bot, int diff, WordPlacer wordPlacer, WordGetter wordGetter, PiecesInHandVerifier piecesInHandVerifier, WordValidator wordValidator, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    @Override
    protected void processLeftPartSpecialPieces(String partialWord, Node node, int limit, Map.Entry<Character, Node> entry){
        if(entry.getKey() == 'R') { // RR
            Node nextNode = node.getSuccessor('R');
            Piece usedPiece = bot.hasPiece("RR");
            if(nextNode != null && usedPiece != null) {
                bot.removePiece(usedPiece);
                LeftPart(partialWord + "RR", nextNode, limit - 1);
                bot.addPiece(usedPiece);
            }
        }
        else if(entry.getKey() == 'L') { // LL
            Node nextNode = node.getSuccessor('L');
            Piece usedPiece = bot.hasPiece("LL");
            if(nextNode != null && usedPiece != null) {
                bot.removePiece(usedPiece);
                LeftPart(partialWord + "LL", nextNode, limit - 1);
                bot.addPiece(usedPiece);
            }
        }
        else if(entry.getKey() == 'C') { // CH
            Node nextNode = node.getSuccessor('H');
            Piece usedPiece = bot.hasPiece("CH");
            if(nextNode != null && usedPiece != null) {
                bot.removePiece(usedPiece);
                LeftPart(partialWord + "CH", nextNode, limit - 1);
                bot.addPiece(usedPiece);
            }
        }
    }

    @Override
    protected void processNextLeftPiece(char lastLetter, String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        if((lastLetter != 'R' || entry.getKey() != 'R') &&
                (lastLetter != 'L' || entry.getKey() != 'L') &&
                (lastLetter != 'C' || entry.getKey() != 'H')) {
            goToNextLeftPiece(partialWord, limit - 1, entry, usedPiece);
        }
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Map.Entry<Character, Node> entry, char lastLetter, Piece usedPiece, Vector2 nextCell) {
        if((lastLetter != 'R' || entry.getKey() != 'R') &&
                (lastLetter != 'L' || entry.getKey() != 'L') &&
                (lastLetter != 'C' || entry.getKey() != 'H')) {
            goToNextRightPiece(partialWord, entry, usedPiece, nextCell);
        }
    }

    @Override
    protected void extendToNextExistingPieceRight(String partialWord, char lastLetter, String placedLetter, Node nextNode, Vector2 nextCell) {
        if ((lastLetter != 'R' || !placedLetter.equals("R")) &&
                (lastLetter != 'L' || !placedLetter.equals("L")) &&
                (lastLetter != 'C' || !placedLetter.equals("H"))) {
            ExtendRight(partialWord + placedLetter, nextNode, nextCell);
        }
    }

    @Override
    protected void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry, Vector2 nextCell) {
        if(entry.getKey() == 'R') { // RR
            Node nextNode = node.getSuccessor('R');
            Piece usedPiece = bot.hasPiece("RR");
            if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "RR")) {
                bot.removePiece(usedPiece);
                ExtendRight(partialWord + "RR", nextNode, nextCell);
                bot.addPiece(usedPiece);
            }
        }
        else if(entry.getKey() == 'L') { // LL
            Node nextNode = node.getSuccessor('L');
            Piece usedPiece = bot.hasPiece("LL");
            if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "LL")) {
                bot.removePiece(usedPiece);
                ExtendRight(partialWord + "LL", nextNode, nextCell);
                bot.addPiece(usedPiece);
            }
        }
        else if(entry.getKey() == 'C') { // CH
            Node nextNode = node.getSuccessor('H');
            Piece usedPiece = bot.hasPiece("CH");
            if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "CH")) {
                bot.removePiece(usedPiece);
                ExtendRight(partialWord + "CH", nextNode, nextCell);
                bot.addPiece(usedPiece);
            }
        }
    }
}