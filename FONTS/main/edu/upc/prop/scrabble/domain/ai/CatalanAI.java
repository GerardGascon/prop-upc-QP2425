package edu.upc.prop.scrabble.domain.ai;

import edu.upc.prop.scrabble.data.Anchors;
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
    protected void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry) {
        char c = entry.getKey(); // Current char
        Node nextNode; // Initialize
        Piece usedPiece;
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
    protected void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
           (lastLetter != 'L' || entry.getKey() != '·')) {
            goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
        }
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((lastLetter != 'N' || entry.getKey() != 'Y') &&
           (lastLetter != 'L' || entry.getKey() != '·')) {
            goToNextRightPiece(partialWord + entry.getKey(), entry.getValue(), cell, usedPiece);
        }
    }

    @Override
    protected void extendToNextExistingPieceRight(String partialWord, Node node, Vector2 cell, Piece placedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        String placedLetter = placedPiece.letter();
        Node nextNode = node.getSuccessor(placedPiece.letter().charAt(0));
        if(nextNode != null) { // Valid node
            if (placedLetter.length() == 1) { // Regular piece
                if ((lastLetter != 'N' || !placedLetter.equals("Y")) && // Illegal combinations check
                    (lastLetter != 'L' || !placedLetter.equals("·"))) {
                    ExtendRight(partialWord + placedLetter, nextNode, cell);
                }
            }
            else { //Special piece
                for (int i = 1; i < partialWord.length() && nextNode != null; i++) nextNode = node.getSuccessor(placedPiece.letter().charAt(i));
                if (nextNode != null) ExtendRight(partialWord + placedLetter, nextNode, cell);
            }
        }
    }

    @Override
    protected void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry) {
        char c = entry.getKey(); // Current char
        Node nextNode; // Initialize
        Piece usedPiece ;
        switch (c) {
            case 'N':
                nextNode = entry.getValue().getSuccessor('Y');
                usedPiece = bot.hasPiece("NY");
                if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "NY")) {
                    if(nextNode.isEndOfWord()) checkWord(partialWord + "NY", cell);
                    goToNextRightPiece(partialWord + "NY", nextNode, cell, usedPiece);
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('·');
                if(nextNode != null) nextNode = nextNode.getSuccessor('L');
                usedPiece = bot.hasPiece("L·L");
                if(nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "L·L")) {
                    if(nextNode.isEndOfWord()) checkWord(partialWord + "L·L", cell);
                    goToNextRightPiece(partialWord + "L·L", nextNode, cell, usedPiece);
                }
                break;
        }
    }
}