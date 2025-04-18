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

public class SpanishAI extends AI {
    public SpanishAI(PiecesConverter piecesConverter, PointCalculator pointCalculator, DAWG dawg, Board board, Player bot, Anchors anchors, CrossChecks crossChecks) {
        super(piecesConverter, pointCalculator, dawg, board, bot, anchors, crossChecks);
    }

    @Override
    protected void processLeftPartSpecialPieces(String partialWord, int limit, Map.Entry<Character, Node> entry) {
        char c = entry.getKey(); // Current char
        Node nextNode = null; // Initialize
        Piece usedPiece = null;
        switch (c) {
            case 'R':
                nextNode = entry.getValue().getSuccessor('R');
                usedPiece = bot.hasPiece("RR");
                if(nextNode != null && usedPiece != null) {
                    goToNextLeftPiece(partialWord + "RR", nextNode, limit, usedPiece);
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('L');
                usedPiece = bot.hasPiece("LL");
                if(nextNode != null && usedPiece != null) {
                    goToNextLeftPiece(partialWord + "LL", nextNode, limit, usedPiece);
                }
                break;

            case 'C':
                nextNode = entry.getValue().getSuccessor('C');
                usedPiece = bot.hasPiece("CH");
                if(nextNode != null && usedPiece != null) {
                    goToNextLeftPiece(partialWord + "CH", nextNode, limit, usedPiece);
                }
                break;
        }
    }

    @Override
    protected void processNextLeftPiece(String partialWord, int limit, Map.Entry<Character, Node> entry, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((lastLetter != 'R' || entry.getKey() != 'R') &&
           (lastLetter != 'L' || entry.getKey() != 'L') &&
           (lastLetter != 'C' || entry.getKey() != 'H')) {
            goToNextLeftPiece(partialWord + entry.getKey(), entry.getValue(), limit, usedPiece);
        }
    }

    @Override
    protected void extendToNextNewPieceRight(String partialWord, Vector2 cell, Map.Entry<Character, Node> entry, Piece usedPiece) {
        char lastLetter = ' ';
        if(!partialWord.isEmpty()) lastLetter = partialWord.charAt(partialWord.length() - 1);
        if((lastLetter != 'R' || entry.getKey() != 'R') && // Illegal combinations check
           (lastLetter != 'L' || entry.getKey() != 'L') &&
           (lastLetter != 'C' || entry.getKey() != 'H')) {
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
                if ((lastLetter != 'R' || !placedLetter.equals("R")) && // Illegal combinations check
                        (lastLetter != 'C' || !placedLetter.equals("H")) &&
                        (lastLetter != 'L' || !placedLetter.equals("L"))) {
                    ExtendRight(partialWord + placedLetter, nextNode, cell);
                }
            }
            else { //Special piece
                nextNode = nextNode.getSuccessor(placedPiece.letter().charAt(1));
                if (nextNode != null) ExtendRight(partialWord + placedLetter, nextNode, cell);
            }
        }
    }

    @Override
    protected void processRightPartSpecialPieces(String partialWord, Node node, Vector2 cell, Map.Entry<Character, Node> entry) {
        char c = entry.getKey(); // Current char
        Node nextNode; // Initialize
        Piece usedPiece;
        switch (c) {
            case 'R':
                nextNode = entry.getValue().getSuccessor('R');
                usedPiece = bot.hasPiece("RR");
                if (nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "RR")) {
                    if (nextNode.isEndOfWord()) checkWord(partialWord + "RR", cell);
                    goToNextRightPiece(partialWord + "RR", nextNode, cell, usedPiece);
                }
                break;

            case 'L':
                nextNode = entry.getValue().getSuccessor('L');
                usedPiece = bot.hasPiece("LL");
                if (nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "LL")) {
                    if (nextNode.isEndOfWord()) checkWord(partialWord + "LL", cell);
                    goToNextRightPiece(partialWord + "LL", nextNode, cell, usedPiece);
                }
                break;
            case 'C':
                nextNode = entry.getValue().getSuccessor('H');
                usedPiece = bot.hasPiece("CH");
                if (nextNode != null && usedPiece != null && crossChecks.ableToPlace(cell.x, cell.y, "CH")) {
                    if (nextNode.isEndOfWord()) checkWord(partialWord + "CH", cell);
                    goToNextRightPiece(partialWord + "CH", nextNode, cell, usedPiece);
                }
                break;
        }
    }
}