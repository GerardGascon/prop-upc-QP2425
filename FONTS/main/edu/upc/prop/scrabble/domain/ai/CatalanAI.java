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
import edu.upc.prop.scrabble.utils.Vector2;

import java.util.Map;

public class CatalanAI extends AI {
    public CatalanAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    @Override
    protected void processLeftPartSpecialPieces(String partialWord, Map.Entry<Character, Node> entry, int limit) {
        char c = entry.getKey(); // Current char
        Node nextNode = null; // Initialize
        Piece usedPiece = null;
        switch (c) {
            case 'N':
                nextNode = entry.getValue().getSuccessor('Y');
                usedPiece = bot.hasPiece("NY");
                if(nextNode != null && usedPiece != null) {
                    goToNextLeftPiece(partialWord + "NY", nextNode, limit, usedPiece);
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('·');
                usedPiece = bot.hasPiece("L·L");
                if(nextNode != null && usedPiece != null) {
                    goToNextLeftPiece(partialWord + "L·L", nextNode.getSuccessor('L'), limit, usedPiece);
                }
                break;
        }
    }

    @Override
    protected void processNextLeftPiece(String partialWord, Map.Entry<Character, Node> entry, int limit, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
           (lastLetter != 'L' || entry.getKey() != '·')) {
            goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
        }
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Map.Entry<Character, Node> entry, Piece usedPiece, Vector2 nextCell) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
                (lastLetter != 'L' || entry.getKey() != '·')) {
            goToNextRightPiece(partialWord, entry, usedPiece, nextCell);
        }
    }

    @Override
    protected void extendToNextExistingPieceRight(String partialWord, Piece placedPiece, Node node, Vector2 nextCell) {
        //System.out.println("");
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        String placedLetter = placedPiece.letter();
        Node nextNode = node.getSuccessor(placedPiece.letter().charAt(0));
        if (placedLetter.length() == 1 && nextNode != null &&
            (lastLetter != 'N' || !placedLetter.equals("Y")) &&
            (lastLetter != 'L' || !placedLetter.equals("·"))) {
           // System.out.println("LENGTH UNO");
            ExtendRight(partialWord + placedLetter, nextNode, nextCell);
        }
        else if (placedLetter.length() > 1 && nextNode != null) {
           // System.out.println("LENGTH ESPECIAL");
            //for (int i = 1; i < partialWord.length() && nextNode != null; i++)
            for (int i = 1; i < placedLetter.length() && nextNode != null; i++)
                //nextNode = node.getSuccessor(placedPiece.letter().charAt(i));
                nextNode = nextNode.getSuccessor(placedPiece.letter().charAt(i));
                if (nextNode != null) {
                   // System.out.println(partialWord + " + " + placedLetter);
                    ExtendRight(partialWord + placedLetter, nextNode, nextCell);
                }
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